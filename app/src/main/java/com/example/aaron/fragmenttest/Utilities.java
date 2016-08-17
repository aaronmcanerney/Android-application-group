package com.example.aaron.fragmenttest;

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
        if (hour > 12) {
            hour -= 12;
            suffix = "PM";
        }
        return String.format("%d:%02d%s",hour, minute, suffix);
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
}
