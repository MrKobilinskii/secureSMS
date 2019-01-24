package com.develop.daniil.securesms;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.daniil.securesms.sql.DBHelper;
import com.develop.daniil.securesms.utils.MainAdapter;
import com.develop.daniil.securesms.utils.message;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    IntentFilter intentFilter;
    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;
    long startTime; int temp = 0; //для Стрелки

    RecyclerView recyclerView; TextView no_mail_textView;
    ArrayList<message> messages = new ArrayList<>();
    MainAdapter mainAdapter;
    ImageButton newMsg;
    DBHelper dbHelper; SQLiteDatabase database; ContentValues contentValues;

    private BroadcastReceiver intentReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String toAddress = intent.getExtras().getString("toUser"); //message to Me!

            messages.clear();
            readContactsFromSQL();

            no_mail_textView.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
            Create mySQL
         */
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();


//        database.delete("contacts",null,null);
        /*
            for Receive sms
         */
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS},
                MY_PERMISSIONS_REQUEST_SMS_RECEIVE); //add permissions to receive

        recyclerView = findViewById(R.id.ReceiveSms_RecyclerView);

        /*
            Work with activity
         */
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mainAdapter = new MainAdapter(messages, R.layout.main_message_model);
        recyclerView.setAdapter(mainAdapter);

        /*
                Read from SQL
         */
        readContactsFromSQL();

        no_mail_textView = findViewById(R.id.no_mail_textView);
        if(messages.size() == 0){
            no_mail_textView.setVisibility(View.VISIBLE);
        }
        else {
             // buttons space will be available for another widgets
            no_mail_textView.setVisibility(View.GONE);
        }

        newMsg = findViewById(R.id.newMsg);
        newMsg.setOnClickListener(new View.OnClickListener() { //новая СМС
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NumberActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        registerReceiver(intentReciever, intentFilter);
        messages.clear();
        readContactsFromSQL();
        no_mail_textView.setVisibility(View.GONE);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(intentReciever);
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);  //for Receive SMS
        if (requestCode == MY_PERMISSIONS_REQUEST_SMS_RECEIVE) {
            // YES!!
            Log.i("TAG", "MY_PERMISSIONS_REQUEST_SMS_RECEIVE --> YES");
        }
    }



    private void readContactsFromSQL(){
        /*
                Read from SQL
         */
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int numberIndex = cursor.getColumnIndex(DBHelper.KEY_NUMBER);
            int textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);
            int timeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
            int typeIndex = cursor.getColumnIndex(DBHelper.KEY_TYPE);
            do {
                messages.add(0, new message(cursor.getString(numberIndex), cursor.getString(textIndex), cursor.getString(timeIndex), cursor.getString(typeIndex))); //сообщение вверх стека
            } while (cursor.moveToNext());
        } else
            Log.d("mLog","0 rows");

        mainAdapter.notifyDataSetChanged();
        cursor.close();
    }

    @Override
    public void onBackPressed() { //Стрелка
        if(temp == 0){ //если это первое нажатие
            Toast.makeText(this,"Нажмите кнопку выхода ещё раз", Toast.LENGTH_SHORT).show();
            startTime = System.currentTimeMillis(); //начал замер
            temp = 1;
        }
        else{
            long estimatedTime = System.currentTimeMillis() - startTime;
            if(estimatedTime < 2000){ //2 seconds
                super.onBackPressed(); //выход
            }
            else{
                temp = 0;
            }
        }
    }
}
