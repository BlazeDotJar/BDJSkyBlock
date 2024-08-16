package de.bdj.sb.utlility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeStamp {

    public static String current_time = "";
    public static String current_date = "";

    /**
     * Returns the current time as a string
     * Format: HH:mm:ss
     */
    public static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        current_time = dtf.format(now);
        return dtf.format(now);
    }
    public static String getCurrentSeconds() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    public static String getCurrentMinutes() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    public static String getCurrentHours() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * Returns the current date as a string
     * Format: dd.MM.yyyy
     */
    public static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        current_date = dtf.format(now);
        return dtf.format(now);
    }

}