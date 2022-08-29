package com.example.khataapp.utils;

import android.content.res.Resources;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.InverseMethod;

import com.example.khataapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kotlin.jvm.JvmStatic;

public class Converter {

    private static String oldNumberString;
    private static double oldNumber;

    @InverseMethod("stringToDouble")
    public static String doubleToString(double num)
    {

        if (num==oldNumber)
        {
            return oldNumberString;
        }
        else
        {
            oldNumberString= String.valueOf(num);
            return oldNumberString;

        }
    }

    public static double stringToDouble(String num)
    {
        double doubleNum=0;
        if (!num.equals(oldNumberString))
        {
            try {
                doubleNum= Double.parseDouble(num);
                oldNumber=doubleNum;

            }
            catch (Exception e)
            {
                Log.e("Conversion Error:",e.getMessage());
            }

        }

       return oldNumber;

    }
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

            DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

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

    @InverseMethod("toDouble")
    public static String toString(EditText view, double oldValue,
                                  double value) {
        NumberFormat numberFormat = getNumberFormat(view);
        try {
            // Don't return a different value if the parsed value
            // doesn't change
            String inView = view.getText().toString();
            double parsed =
                    numberFormat.parse(inView).doubleValue();
            if (parsed == value) {
                return view.getText().toString();
            }
        } catch (ParseException e) {
            // Old number was broken
        }
        return numberFormat.format(value);
    }

    public static double toDouble(EditText view, double oldValue,
                                  String value) {
        NumberFormat numberFormat = getNumberFormat(view);
        try {
            return numberFormat.parse(value).doubleValue();
        } catch (ParseException e) {
            Resources resources = view.getResources();
            String errStr = resources.getString(R.string.badNumber);
            view.setError(errStr);
            return oldValue;
        }
    }

    private static NumberFormat getNumberFormat(EditText view) {
        Resources resources= view.getResources();
        Locale locale = resources.getConfiguration().locale;
        NumberFormat format =
                NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            DecimalFormat decimalFormat = (DecimalFormat) format;
            decimalFormat.setGroupingUsed(false);
        }
        return format;
    }

    public  static String FormatDoubleNumbers(double num)
    {
       return "Rs "+new DecimalFormat("##.##").format(num);
    }
    public  static String FormatDoubleNumbers(String num)
    {
        String[] numberArray= num.split("\\.");
        if (numberArray.length>1)
        {
            if (numberArray[1].length()>2)
            {
                num=numberArray[0]+"."+numberArray[1].substring(0,2);
            }
        }
       return num;
    }
}
