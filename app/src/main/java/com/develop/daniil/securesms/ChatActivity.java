package com.develop.daniil.securesms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.develop.daniil.securesms.utils.ChatAdapter;
import com.develop.daniil.securesms.utils.messageRight;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    ImageView logo; ImageButton newMsg; TextView appName; String number, text, time;
    EditText write_msg; RecyclerView chat_RecyclerView; ImageButton send_Button;
    ChatAdapter chatAdapter; ArrayList<messageRight> messages = new ArrayList<>();
    DateFormat dateFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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

        write_msg = findViewById(R.id.write_msg);
        chat_RecyclerView = findViewById(R.id.chat_RecyclerView);
        send_Button = findViewById(R.id.send_Button);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        write_msg.requestFocus(); //set focus by default

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this); //create recyclerView
        chat_RecyclerView.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter(messages);
        chat_RecyclerView.setAdapter(chatAdapter);

        send_Button.setOnClickListener(new View.OnClickListener() { //Нажимю отправить смс
            @Override
            public void onClick(View v) {
                text = write_msg.getText().toString();
                Date date = new Date();
                time = dateFormat.format(date); //текущее Время

                messageRight messageRight = new messageRight(text, time);
                messages.add(messageRight);
                chatAdapter.notifyDataSetChanged(); //New message!!!

                chat_RecyclerView.scrollToPosition(messages.size() - 1); //Показать последний месседж в Ресайкл Вью
            }
        });
    }
}
