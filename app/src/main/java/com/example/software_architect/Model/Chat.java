package com.example.software_architect.Model;

public class Chat {
    private String sender,receiver,message;

    public Chat(String mSender, String mReceiver, String mMessage) {
        sender = mSender;
        receiver = mReceiver;
        message = mMessage;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String mSender) {
        sender = mSender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String mReceiver) {
        receiver = mReceiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String mMessage) {
        message = mMessage;
    }
}
