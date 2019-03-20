/**
 * Project Javagram Created by Shibkov Konstantin on 20.03.2019.
 */
package javagram.Model;

abstract class TLAbsRepository {
  String userPhone;
  boolean isPhoneRegistered = false;

  static volatile TLRepositoryProd instance;

  String userFullName;
  String userFirstName;
  String userLastName;

  int userId;
}
