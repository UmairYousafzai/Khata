package com.example.khataapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static DateUtil instance;

    public static synchronized DateUtil getInstance()
    {
        if (instance==null)
        {
            instance= new DateUtil();
        }

        return instance;
    }

    public String getDate()
    {
        //date
        Date date= Calendar.getInstance().getTime();

        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

        return dateFormat.format(date);

    }
}
