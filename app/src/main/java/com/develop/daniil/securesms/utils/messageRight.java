package com.develop.daniil.securesms.utils;

public class messageRight {

    String text, time;
    public messageRight(String text, String time){
        this.text = text;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }
}
