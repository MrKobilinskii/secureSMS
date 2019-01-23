package com.develop.daniil.securesms.utils;

public class message{
    String fromAddress, toAddress, text, time;

    public message(String fromAddress, String toAddress, String text, String time){
        this.fromAddress = fromAddress;
        this.toAddress= toAddress;
        this.text = text;
        this.time = time;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }
}
