/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import static java.lang.Thread.sleep;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
import javagram.CommonInterfaces.IHumanableDate;
import javagram.Log;
import javagram.MainContract;
import javagram.MainContract.IContact;
import javagram.MainContract.IMessage;
import javagram.MainContract.Repository;
import javagram.Model.TelegramProdFactory;
import javagram.Presenter.objects.TgMessage;
import javagram.WindowGUI.WindowHandler;
import javax.swing.DefaultListModel;
import org.javagram.response.object.Message;

public class PrChat implements MainContract.IPresenter {

  //TelegramApiBridge
  private volatile HashMap<Integer, IContact> contactList = new HashMap<>();
  //HashMap<idMessage, idContact>
  private volatile TreeMap<Integer, IContact> lastMessagesList = new TreeMap<>();
  private Repository repository = new TelegramProdFactory().getModel();
  private MainContract.IViewChat view;
  private volatile DefaultListModel<IContact> contactsListModel = new DefaultListModel<>();
  private volatile DefaultListModel<IMessage> messagesListModel;

  public PrChat(MainContract.IViewChat view) {
    this.view = view;
    //set view to frame
    showUserData();

    getContactList();
  }

  void showUserData() {
    this.view.setUserFullNameLabelTop(repository.getUserFullName());
    this.view.setUserPhotoTop(repository.getUserPhoto(),
        repository.getUserFirstName(), repository.getUserLastName());
  }

  public synchronized void getContactList() {

    Thread th = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          //get from Telegram Api
          ArrayList<IContact> contactListArray = repository.getContactList();

          for (int i = 0; i < contactListArray.size(); i++) {
            if (contactList.containsKey(contactListArray.get(i).getId())) {
              //TODO обновить контакт
              continue;
            } else {
              contactList.put(contactListArray.get(i).getId(), contactListArray.get(i));
            }
          }
          //clear
          contactsListModel.clear();

          for (IContact contact : contactList.values()) {
            Log.info("add IContact contact " + contact.getId() + ":" + contact.getFullName());
            contactsListModel.addElement(contact);
          }
          view.showContactList(contactsListModel);

          //pause before get lastmessages to prevent FLOOD_WAIT
          view.showInfo(
              "<html>Обновляем последние сообщения и<br>сортируем список контактов</html> ");
          sleep(1000);
          getLastMessages();
          view.showInfo("Загружаем фотографии контактов");
          sleep(1000);

        } catch (
            Exception e) {
          e.printStackTrace();
          view.showError("Ошибка при получении списка контактов! IOException getContactList()");
        }

        refreshUserPhotos();
        view.showInfo("<html><b>Все готово!</b><br>Можете написать сообщения</html>");
      }

    });

    th.start();
  }

  public void getLastMessages() {
    try {
      ArrayList<Message> lastMessages = repository.messagesGetDialogs(0, 0, 0);
      for (Message m : lastMessages) {
        IMessage lastMessage = new TgMessage(m.getId(), m.getFromId(), m.getToId(), m.getMessage(),
            m.getDate(),
            m.isOut(), m.isUnread());

        //set last messages to contact list
        try {
          if (m.isOut()) {
            contactList.get(m.getToId()).setLastMessage(lastMessage);
            lastMessagesList.put(m.getId(), contactList.get(m.getToId()));
          } else {
            contactList.get(m.getFromId()).setLastMessage(lastMessage);
            lastMessagesList.put(m.getId(), contactList.get(m.getFromId()));
          }
        } catch (NullPointerException e) {
          Log.info("NPE when get last Messages " + m.getToId());
        }
      }
      //contactsListModel.clear();

      for (Integer i : lastMessagesList.keySet()) {
        IContact currentContact = lastMessagesList.get(i);
        if (contactsListModel.contains(currentContact)) {
          int indexToRemove = contactsListModel.indexOf(currentContact);
          contactsListModel.remove(indexToRemove);
          contactsListModel.insertElementAt(currentContact, 0);
        }
      }
      WindowHandler.repaintFrame();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public synchronized void refreshUserPhotos() {
    Thread threadGetPhotos = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < contactsListModel.getSize(); i++) {
          IContact c = contactsListModel.get(i);
          Log.info("start set small photo to contact " + c.getFullName() + "(" + c.getId() + ")");
          BufferedImage photoSmall = repository.getContactPhotoSmall(c);
          if (photoSmall != null) {
            c.setPhotoSmall(photoSmall);
            Log.info("smallphoto have setted for " + c.getFullName());
          }
          if (i % 5 == 0) {
            view.repaintContactList();
          }
        }
      }
    }
    );

    threadGetPhotos.start();

  }

  public void clearContactListModel() {
    contactsListModel.clear();
  }

  public void getDialogMessages(int userId) {
    messagesListModel = new DefaultListModel<>();
    ArrayList<Message> messages = null;
    try {
      messages = repository.getMessagesHistoryByUserId(userId);
      importMessagesToContact(userId, messages);

    } catch (IOException e) {
      e.printStackTrace();
      view.showError("Сообщения для чата " + userId + " не получены!");
    }

    //method add to model

    Collections.reverse(messages);
    for (Message message : messages) {
      messagesListModel
          .addElement(new TgMessage(message.getId(), message.getFromId(), message.getToId(),
              message.getMessage(), message.getDate(), message.isOut(), message.isUnread()));
    }
    view.showDialogMessages(messagesListModel);
  }

  private void importMessagesToContact(int userId, ArrayList<Message> messages) {

  }

  public void logOut() {
    repository.logOut();
  }

  public void sendMessage(int contactId, String text) {
    try {
      repository.sendMessage(contactId, text, (int) (Math.random() * 1000));
      //add message to Dialog

      IMessage newMessage = new TgMessage(0, repository.getUserId(), contactId, text,
          (int) (System.currentTimeMillis() / 1000), true, false);
      messagesListModel.addElement(newMessage);

      IContact contactAddLastMessage = contactList.get(contactId);

      if (contactsListModel.contains(contactAddLastMessage)) {
        int index = contactsListModel.indexOf(contactAddLastMessage);
        contactsListModel.get(index).setLastMessage(newMessage);
      }

      view.refreshDialogsView();
    } catch (IOException e) {
      e.printStackTrace();
      view.showError("error sending message");
    }
  }

}
