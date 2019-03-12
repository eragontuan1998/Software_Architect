package com.example.software_architect.Model;

public class Chat {
    private String sender,receiver,message;
    private boolean isseen;

    public Chat(String mSender, String mReceiver, String mMessage,boolean isseen) {
        this.sender = mSender;
        this.receiver = mReceiver;
        this.message = mMessage;
        this.isseen= isseen;
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

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean mIsseen) {
        isseen = mIsseen;
    }
}
