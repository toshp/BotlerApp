package com.heybotler.botlerapp.models;

/**
 * Created by toshitpanigrahi on 3/6/17.
 */

public class Message {
    private String text;
    private String time;
    private String sender;
    private String picSrc;

    public Message(String text, String time, String sender, String picSrc) {
        this.text = text;
        this.time = time;
        this.sender = sender;
        this.picSrc = picSrc;
    }

    // Getters
    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public String getSender() {
        return sender;
    }

    public String getPicSrc() {
        return picSrc;
    }
}
