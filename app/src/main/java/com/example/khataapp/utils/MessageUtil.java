package com.example.khataapp.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MessageUtil {

    private static MessageUtil messageUtil=null;

    public static synchronized MessageUtil getInstance()
    {
        if (messageUtil==null)
        {
            messageUtil=new MessageUtil();

        }
        return messageUtil;
    }

    private void sendMessage(String message, Context context)
    {

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");

        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(context.getPackageManager())
                == null) {
            Toast.makeText(context, "Please install whatsapp first.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Starting Whatsapp
        context.startActivity(intent);
    }

}
