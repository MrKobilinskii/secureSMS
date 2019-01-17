package com.develop.daniil.securesms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NumberActivity extends AppCompatActivity {

    ImageView logo; ImageButton newMsg; TextView appName; Button next; EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        /*
            Work with my_toolbar
        */
        logo = findViewById(R.id.logo);
        newMsg = findViewById(R.id.newMsg);
        appName = findViewById(R.id.appName);
        number = findViewById(R.id.number_EditText);

        logo.setImageResource(R.drawable.ic_arrow_back_black_24dp); //Кнопка назад вместо Лого
        appName.setText("Новое сообщение");
        newMsg.setVisibility(View.GONE); //Убрать нью мсдж

        logo.setOnClickListener(new View.OnClickListener() { // =Arrow-back
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
            Work with Activity
         */

        next = findViewById(R.id.next);
        number = findViewById(R.id.number_EditText);

        number.requestFocus(); //set focus by default

        next.setOnClickListener(new View.OnClickListener() { //Кнопка Далее
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NumberActivity.this, ChatActivity.class);

                intent.putExtra("number", number.getText().toString()); // send Username to Chat

                if((number.getText().length() > 0) &&(number.getText().length() <= 20)) //Todo: Проверка номера
                {
                    finish();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(NumberActivity.this, "Укажите номер в поле ввода", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
