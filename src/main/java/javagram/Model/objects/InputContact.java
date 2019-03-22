/**
 * Project Javagram Created by Shibkov Konstantin on 22.03.2019.
 */
package javagram.Model.objects;

import org.telegram.api.TLInputContact;

public class InputContact {
  private long clientId;
  private String phone;
  private String firstName;
  private String lastName;

  public InputContact(long clientId, String phone, String firstName, String lastName) {
    this.clientId = clientId;
    this.phone = phone;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public long getClientId() {
    return this.clientId;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getPhone() {
    return this.phone;
  }

  public TLInputContact createTLInputContact() {
    return new TLInputContact(this.clientId, this.phone, this.firstName, this.lastName);
  }
}
