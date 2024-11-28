package com.example.chattingavapp;

public class msggModel {
    String message;
    String senderid;
    long timeStamp;

    public msggModel() {
    }

    public msggModel(long timeStamp, String senderid, String message) {
        this.timeStamp = timeStamp;
        this.senderid = senderid;
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
