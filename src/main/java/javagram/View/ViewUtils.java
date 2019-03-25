package javagram.View;

public class ViewUtils {

  public static String getFullNameInitiates(String userFirstName, String userLastName) {
    if (userFirstName == null || userLastName == null || userFirstName.equals("")) {
      return "n/a error";
    }
    boolean hasLastName = (userLastName != null && !userLastName.equals(""));
    return hasLastName ? userFirstName.substring(0, 1) + userLastName.substring(0, 1)
        : userFirstName.substring(0, 1);
  }

}
