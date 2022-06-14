package com.example.finalstep2.utils;

public class DateUtils {

    public static String GetDate(int years, int month, int day) {
        String date = String.valueOf(years) + String.valueOf(month) + String.valueOf(day);
        return date;
    }

    public static boolean CheckEqul(String from, String to)
    {
        int fromInt = Integer.parseInt(from);
        int toInt = Integer.parseInt(to);
        if (fromInt < toInt)
            return true;
        else return false;
    }

}
