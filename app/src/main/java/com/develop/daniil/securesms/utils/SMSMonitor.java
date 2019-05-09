package com.develop.daniil.securesms.utils;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.develop.daniil.securesms.sql.DBHelper;

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

                // Retrieve the sms message received
                Object[] pdus = (Object[]) bundle.get("pdus");
                messages = new SmsMessage[pdus.length];
                for (int i = 0; i < messages.length; i++) { //собираем СМС из кусочков
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    fromAddress = messages[i].getOriginatingAddress();
                    bodyMsg = messages[i].getMessageBody();
                }
                sendBundle(context);

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


        char[] CharBodyMsg = bodyMsg.toCharArray(); //сообщение в массив символов
        if(bodyMsg != null && CharBodyMsg[0] == '#' && CharBodyMsg[CharBodyMsg.length-1] == '#'){ //Если первый и последний символ это метки ##
            /*
            Create mySQL
         */
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            ContentValues contentValues1 = new ContentValues();
            ContentValues contentValues = new ContentValues();

            //ToDo: Расшифровка
            // --- [#, символТекст, символКлюч, символТекст, ...., #]

            String key = "";
            String crypto_text = "";

            for(int i = 1; i <= CharBodyMsg.length - 3;i+=2){ //в обход решёток-меток
                crypto_text += CharBodyMsg[i];
                key += CharBodyMsg[i+1];
            }

            char[] char_key = key.toCharArray();
            char[] char_crypto_text = crypto_text.toCharArray();

            String original_text = "";
            for(int i = 0; i <= char_key.length - 1;i++){
                int symb = (int)char_crypto_text[i] ^ (int)char_key[i]; //XOR

                original_text += (char)symb;
            }
        /*
             Add msg to Messages
            */
            contentValues.put(DBHelper.MESSAGE_NUMBER, fromAddress);
            contentValues.put(DBHelper.MESSAGE_TEXT, original_text);
            contentValues.put(DBHelper.MESSAGE_TIME, time);
            contentValues.put(DBHelper.MESSAGE_TYPE, "receive");

            database.insert(DBHelper.TABLE_MESSAGES, null, contentValues);

        /*
                delete msg from Contacts if exists
             */
            database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_NUMBER + " = ?", new String[]{fromAddress});

        /*
             Add msg to CONTACTS
            */
            contentValues1.put(DBHelper.KEY_NUMBER, fromAddress);
            contentValues1.put(DBHelper.KEY_TEXT, original_text);
            contentValues1.put(DBHelper.KEY_TIME, time);
            contentValues1.put(DBHelper.KEY_TYPE, "receive");

            database.insert(DBHelper.TABLE_CONTACTS, null, contentValues1);

        /*
            send broadcast
         */

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("toUser","Message to User");
            context.sendBroadcast(broadcastIntent);
        }

    }
}
