package com.example.khataapp.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

    public static String StringToFormatDate(String date)
    {
        String formatDate="";

        if (date!=null)
        {
            Date date1 = null;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");

            if (date1 != null) {
                formatDate= dateFormat.format(date1);
            }
        }

        return formatDate;

    }

    public static String fullStringToFirstLetter(String string)
    {
        String letter="";
        if (string!=null&&!string.isEmpty())
        {
             letter= string.substring(0,1);

        }
        return letter;
    }
}
