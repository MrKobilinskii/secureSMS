package com.develop.daniil.securesms.utils;

public class User {
    String number, lastMsg_text, lastMsg_time; Long id;

    public User(Long id, String number, String lastMsg_text, String lastMsg_time) {
        this.id = id;
        this.number = number;
        this.lastMsg_text = lastMsg_text;
        this.lastMsg_time = lastMsg_time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setLastMsg_text(String lastMsg_text) {
        this.lastMsg_text = lastMsg_text;
    }

    public void setLastMsg_time(String lastMsg_time) {
        this.lastMsg_time = lastMsg_time;
    }

    public Long getId() {
        return id;
    }

    public String getLastMsg_text() {
        return lastMsg_text;
    }

    public String getLastMsg_time() {
        return lastMsg_time;
    }

    public String getNumber() {
        return number;
    }
}
