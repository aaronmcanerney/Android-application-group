package com.example.aaron.fragmenttest;

import java.util.Calendar;

public class Utilities {

    private static String[] months = new String[]{"January" , "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    public static String formatDate(int year, int month, int day) {
        String monthString = months[month];
        return String.format("%s %d, %d", monthString, day, year);
    }

    public static String formatDate(Long year, Long month, Long day) {
        return formatDate(year.intValue(), month.intValue(), day.intValue());
    }

    public static String formatDate(Event event) {
        return formatDate(event.getYear(), event.getMonth(), event.getDay());
    }

    public static String formatTime(int hour, int minute) {
        String suffix = "AM";
        if (hour >= 12) {
            if (hour > 12) hour -= 12;
            suffix = "PM";
        }
        if (hour == 0) hour = 12;
        return String.format("%d:%02d%s", hour, minute, suffix);
    }

    public static String formatTime(Long hour, Long minute) {
        return formatTime(hour.intValue(), minute.intValue());
    }

    public static String formatTime(Event event) {
        return formatTime(event.getHour(), event.getMinute());
    }

    public static String formatDateAndTime(Event event) {
        return formatDate(event) + " @ " + formatTime(event);
    }

    public static String formatSystemDateAndTime(Event event) {
        int year = event.getYear();
        int month = event.getMonth();
        int day = event.getDay();
        int hour = event.getHour();
        int minute = event.getMinute();

        return String.format("%04d-%02d-%02d %02d:%02d", year, month, day, hour, minute);
    }

    public static String formatSystemDateAndTimeAtCurrentMoment() {
        Calendar initialTime = Calendar.getInstance();
        int day = initialTime.get(Calendar.DAY_OF_MONTH);
        int month = initialTime.get(Calendar.MONTH);
        int year = initialTime.get(Calendar.YEAR);
        int hour = initialTime.get(Calendar.HOUR_OF_DAY);
        int minute = initialTime.get(Calendar.MINUTE);

        Event event = new Event();
        event.setYear(year);
        event.setMonth(month);
        event.setDay(day);
        event.setHour(hour);
        event.setMinute(minute);

        return formatSystemDateAndTime(event);
    }
}
