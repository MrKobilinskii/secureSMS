package com.develop.daniil.securesms.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSMonitor extends BroadcastReceiver {

    SmsMessage[] messages;
    String fromAddress = "";
    String bodyMsg = "";
    private static final String TAG = "Принято новое сообщение!";

    @Override
    public void onReceive(Context context, Intent intent) { // вызывается системой каждый раз при получении сообщения

        Bundle bundle = intent.getExtras();

//        Todo: Work with SDK!
        if (bundle != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                final String myPackageName = context.getPackageName();
                if (Telephony.Sms.getDefaultSmsPackage(context).equals(
                        myPackageName)) {
                    // you are default

                    // Retrieve the sms message received
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    messages = new SmsMessage[pdus.length];

                    for (int i = 0; i < messages.length; i++) { //собираем СМС из кусочков
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        fromAddress = messages[i].getOriginatingAddress();
                        bodyMsg = messages[i].getMessageBody();
                    }

                    sendBundle(context);

                }

            } else {
                // for below KitKat do like normal
                Object[] pdus = (Object[]) bundle.get("pdus");
                messages = new SmsMessage[pdus.length];

                for (int i = 0; i < messages.length; i++) { //собираем СМС из кусочков
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    fromAddress = messages[i].getOriginatingAddress();
                    bodyMsg = messages[i].getMessageBody();
                }

                sendBundle(context);
            }

        }
    }

    private void sendBundle(Context context){
        Toast.makeText(context, TAG, Toast.LENGTH_SHORT).show();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //Time format
        String time = dateFormat.format(new Date()); //Put current time

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("SMS_RECEIVED_ACTION");
        broadcastIntent.putExtra("bodyMsg", bodyMsg);
        broadcastIntent.putExtra("fromAddress", fromAddress);
        broadcastIntent.putExtra("time", time);
        context.sendBroadcast(broadcastIntent);
    }

}
