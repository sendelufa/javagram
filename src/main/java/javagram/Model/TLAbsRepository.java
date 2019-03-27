/**
 * Project Javagram Created by Shibkov Konstantin on 20.03.2019.
 */
package javagram.Model;

import java.awt.Image;

abstract class TLAbsRepository {

  static volatile TLRepositoryProd instance;
  boolean isPhoneRegistered = false;
  String userPhone;
  String userFullName;
  String userFirstName;
  String userLastName;

  Image userPhotoSmall = null;
  Image getUserPhotoBig = null;

  int userId;
}
