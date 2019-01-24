package com.develop.daniil.securesms;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.daniil.securesms.sql.DBHelper;
import com.develop.daniil.securesms.utils.ChatAdapter;
import com.develop.daniil.securesms.utils.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    String SENT_SMS = "SENT_SMS";
    String DELIVER_SMS = "RECEIVE_SMS";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    Intent sent_intent = new Intent(SENT_SMS);
    Intent deliver_intent = new Intent(DELIVER_SMS);
    PendingIntent sent_pi, deliver_pi;

    ImageView logo; ImageButton newMsg; TextView appName; String number, text, time, type;
    EditText write_msg; RecyclerView chat_RecyclerView; ImageButton send_Button;
    ChatAdapter chatAdapter; ArrayList<message> messages = new ArrayList<>();
    DateFormat dateFormat; DBHelper dbHelper; SQLiteDatabase database;

    private BroadcastReceiver intentReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String toAddress = intent.getExtras().getString("toUser"); //message to Me!

            messages.clear();
            readMessagesFromSQL();
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sent_pi = PendingIntent.getBroadcast(ChatActivity.this,0,sent_intent,0);
        deliver_pi = PendingIntent.getBroadcast(ChatActivity.this,0,deliver_intent,0);

        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter("SMS_RECEIVED_ACTION");
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(intentReciever, intFilt);

        /*
            Work with my_toolbar
        */
        logo = findViewById(R.id.logo);
        newMsg = findViewById(R.id.newMsg);
        appName = findViewById(R.id.appName);

        logo.setImageResource(R.drawable.ic_arrow_back_black_24dp); //Кнопка назад вместо Лого
        newMsg.setVisibility(View.GONE); //Убрать нью мсдж

        logo.setOnClickListener(new View.OnClickListener() { // =Arrow-back
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        number = getIntent().getStringExtra("number"); //Приём номера аддрессата
        appName.setText(number);

        /*
            Work with Activity
         */

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this); //create recyclerView
        chat_RecyclerView = findViewById(R.id.chat_RecyclerView);
        chat_RecyclerView.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter(messages);
        chat_RecyclerView.setAdapter(chatAdapter);

        // Todo: take msgs from sql
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        readMessagesFromSQL();

        write_msg = findViewById(R.id.write_msg);
        chat_RecyclerView = findViewById(R.id.chat_RecyclerView);
        send_Button = findViewById(R.id.send_Button);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

//        write_msg.requestFocus(); //set focus by default

        send_Button.setOnClickListener(new View.OnClickListener() { //Нажимю отправить смс
            @Override
            public void onClick(View v) {
                text = write_msg.getText().toString();
                write_msg.setText(""); //EditText null
                Date date = new Date();
                time = dateFormat.format(date); //текущее Время
                type = "send"; //Message from ME!

                message message = new message(number, text, time, type);
                ContentValues contentValues = new ContentValues();

                contentValues.put("number", number);
                contentValues.put("text", text);
                contentValues.put("time", time);
                contentValues.put("type", type);
                database.insert(DBHelper.TABLE_MESSAGES, null, contentValues);

                messages.add(message);
                sendMessage();

                chatAdapter.notifyDataSetChanged(); //New message!!!

                chat_RecyclerView.scrollToPosition(messages.size() - 1); //Показать последний месседж в Ресайкл Вью
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // дерегистрируем (выключаем) BroadcastReceiver
        unregisterReceiver(intentReciever);
    }

    private void readMessagesFromSQL(){
        /*
                Read from SQL
         */
        String selection = "number = ?";
        String[] selectionArgs = new String[] { number }; //Выбор записей по текущему номеру
        Cursor cursor = database.query(DBHelper.TABLE_MESSAGES, null, selection, selectionArgs, null, null,
                null);

        if (cursor.moveToLast()) {
            int idIndex = cursor.getColumnIndex(DBHelper.MESSAGE_ID);
            int numberIndex = cursor.getColumnIndex(DBHelper.MESSAGE_NUMBER);
            int textIndex = cursor.getColumnIndex(DBHelper.MESSAGE_TEXT);
            int timeIndex = cursor.getColumnIndex(DBHelper.MESSAGE_TIME);
            int typeIndex = cursor.getColumnIndex(DBHelper.MESSAGE_TYPE);
            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", number = " + cursor.getString(numberIndex) +
                        ", text = " + cursor.getString(textIndex) +
                        ", time = " + cursor.getString(timeIndex));
                messages.add(0, new message(cursor.getString(numberIndex),  cursor.getString(textIndex), cursor.getString(timeIndex), cursor.getString(typeIndex))); //сообщение вверх стека
            } while (cursor.moveToPrevious());
        } else
            Log.d("mLog","0 rows");

        chatAdapter.notifyDataSetChanged();
        cursor.close();
        chat_RecyclerView.scrollToPosition(messages.size() - 1); //Показать последний месседж в Ресайкл Вью

        /*
            Check number in Contacts_table
         */
        Cursor cursor1 = database.query(DBHelper.TABLE_CONTACTS, null, selection, selectionArgs, null, null,
                null);
        if(!cursor1.moveToFirst()){
            ContentValues contentValues = new ContentValues();
            contentValues.put("number", number);
            contentValues.put("text", "");
            contentValues.put("time", "");
            contentValues.put("type", "receive");

            database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
        }
    }

    private void sendMessage(){
//        Random random = new Random(System.currentTimeMillis());
//        int mkey = random.nextInt(1000);
//        int key = mkey % 26;  //ключ от 0 до 25
//        String decryptMsg = "";
//
//
//        SecureMessage secureMessage = new SecureMessage();
//        text = secureMessage.encryptMessage(text, key); //шифр

//        SecureMessage secureMessage = new SecureMessage();
//        if(message.length() > 0)
//            message = secureMessage.encryptMessage(message, key); //шифр
//
//        phone_editText.setText(message);
//
//        if(message.charAt(0) == '[' && message.indexOf(']') != -1) {  //дешифр
//            decryptMsg = message.substring(1, message.indexOf(']'));             //достаю смс
//            key = Integer.parseInt(message.substring( message.indexOf(']') + 1, message.length())); //достаю ключ
//
//            decryptMsg = secureMessage.decryptMessage(decryptMsg, key);
//            msg_editText.setText(decryptMsg);
//        }

        //СЛОЖНАЯ ПРОВЕРКА СИСТЕМЫ, К БОЛЕЕ УСТАРЕВШЕЙ ДРУГОЙ ПОДХОД!!!
        if (ContextCompat.checkSelfPermission(ChatActivity.this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ChatActivity.this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(ChatActivity.this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(number,null, text,null,null);
            Toast.makeText(ChatActivity.this,"Сообщение отправлено", Toast.LENGTH_SHORT).show();
        }
        catch (Throwable ignored) //суперкласс всех ошибок и исключений
        {
            Toast.makeText(ChatActivity.this,"Ошибка, повторите попытку", Toast.LENGTH_SHORT).show();
        }
    }
}


