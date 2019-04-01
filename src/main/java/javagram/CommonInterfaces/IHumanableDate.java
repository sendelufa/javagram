/**
 * Project Javagram Created by Shibkov Konstantin on 18.03.2019.
 */
package javagram.CommonInterfaces;

import java.util.Calendar;

public interface IHumanableDate {

  static String convertDate(int dateMessageInSeconds) {
    StringBuilder dateString = new StringBuilder();
    //time in seconds (unixstamp)
    Calendar todayDate = Calendar.getInstance();
    todayDate.set(todayDate.get(Calendar.YEAR), todayDate.get(Calendar.MONTH),
        todayDate.get(Calendar.DATE), 0, 0, 0);
    long startTodayUnixStamp = todayDate.getTimeInMillis() / 1000;

    Calendar calDate = Calendar.getInstance();
    long sec = ((long) dateMessageInSeconds) * 1000;
    calDate.setTimeInMillis(sec);
    int hour = calDate.get(Calendar.HOUR_OF_DAY);
    String hourString = hour < 10 ? "0" + hour : "" + hour;

    int min = calDate.get(Calendar.MINUTE);
    String minString = min < 10 ? "0" + min : "" + min;

    if (dateMessageInSeconds < startTodayUnixStamp) {
      String m = String.valueOf(calDate.get(Calendar.MONTH) + 1);
      String month = m.length() == 1 ? "0" + m : m;
      dateString
          .append(calDate.get(Calendar.DAY_OF_MONTH))
          .append(".")
          .append(month)
          .append(" ");
    }

    dateString.append(hourString)
        .append(":")
        .append(minString);

    return dateString.toString();
  }
}
