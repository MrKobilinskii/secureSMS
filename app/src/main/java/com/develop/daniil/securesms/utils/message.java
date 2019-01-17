package com.develop.daniil.securesms.utils;

public class message{
    String fromAddress, text, time;

    public message(String fromAddress, String text, String time){
        this.fromAddress = fromAddress;
        this.text = text;
        this.time = time;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }
}
