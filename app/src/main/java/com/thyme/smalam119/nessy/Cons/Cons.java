package com.thyme.smalam119.nessy.Cons;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by smalam119 on 1/11/18.
 */

public class Cons {
    public static final String BASE_URL = "https://quotes.rest/";
    public static final int QUOTES_LOAD_INTERVAL = 10000;
    public static final int QUOTES_LOAD_INTERVAL_SIX_HOUR = 21600000;
    public static final int QUOTES_LOAD_INTERVAL_TWELVE_HOUR = 43200000;
    public static final int QUOTES_LOAD_INTERVAL_TWENTY_FOUR_HOUR = 86400000;
    public static final int QUOTES_LOAD_INTERVAL_TEST = 300000;
    public static final String QUOTES_CATEGORY_FUNNY = "funny";
    public static final String QUOTES_CATEGORY_INSPIRE = "inspire";
    public static final String SHARED_PREF_NAME = "NESSY_SHARED_PREF";

    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum - 1;
    }

    public static String getCurrentDate(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        String currentDate = dateFormat.format(cal.getTime());
        return currentDate;
    }
}
