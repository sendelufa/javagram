/**
 * Project Javagram Created by Shibkov Konstantin on 18.03.2019.
 */
package javagram.CommonInterfaces;

import java.util.Calendar;

public interface HumanableDate {

  static String convertDate(int dateMessageInSeconds) {
    StringBuilder dateString = new StringBuilder();
    //time in seconds (unixstamp)
    Calendar todayDate = Calendar.getInstance();
    todayDate.set(todayDate.get(Calendar.YEAR), todayDate.get(Calendar.MONTH),
        todayDate.get(Calendar.DATE), 0, 0, 0);
    long startTodayUnixStamp = todayDate.getTimeInMillis() / 1000;

    Calendar calDate = Calendar.getInstance();
    long sec = Long.valueOf(dateMessageInSeconds) * 1000;
    calDate.setTimeInMillis(sec);

    dateString.append(calDate.get(Calendar.HOUR_OF_DAY))
        .append(":")
        .append(calDate.get(Calendar.MINUTE));

    if (dateMessageInSeconds < startTodayUnixStamp) {
      String m = String.valueOf(calDate.get(Calendar.MONTH)+1);
      String month = m.length() == 1 ? "0" + m : m;
      dateString.append(" ")
          .append(calDate.get(Calendar.DAY_OF_MONTH))
          .append(".")
          .append(month);
    }
    return dateString.toString();
  }
}
