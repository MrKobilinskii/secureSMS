package com.develop.daniil.securesms.utils;

public class message{
    String number, text, time, type;

    public message(String number, String text, String time, String type){
        this.number = number;
        this.text = text;
        this.time = time;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }
}
