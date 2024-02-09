package com.tibagni.logviewer.util;

import com.tibagni.logviewer.log.LogTimestamp;

public class JTimestampUtils {
  public static LogTimestamp stringToLogTimeStamp(String timestamp) {
    String[] timestampParts = timestamp.split(" ");
    String[] date = timestampParts[0].split("-");
    String[] time = timestampParts.length > 1 ?
    // Allow the user to use ':' or '.' for time portion. In case it is a copy of
    // what is in the logcat
        timestampParts[1].replaceAll("\\.", ":").split(":") : new String[] {};

    if (date.length != 2)
      throw new IllegalArgumentException("Invalid date!");

    int month = Integer.parseInt(date[0]);
    int day = Integer.parseInt(date[1]);

    // It does not matter if the timestamp is not complete as it should work with
    // approximate values. So, don't enforce it.
    int hour = (time.length > 0) ? Integer.parseInt(time[0]) : 0;
    int min = (time.length > 1) ? Integer.parseInt(time[1]) : 0;
    int sec = (time.length > 2) ? Integer.parseInt(time[2]) : 0;
    int hund = (time.length > 3) ? Integer.parseInt(time[3]) : 0;

    return new LogTimestamp(month, day, hour, min, sec, hund);
  }
}
