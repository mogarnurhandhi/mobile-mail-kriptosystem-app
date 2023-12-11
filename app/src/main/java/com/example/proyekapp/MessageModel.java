package com.example.proyekapp;

public class MessageModel {
    private String messageSubject;
    private String messageText;
    private String messageUser;
    private String  messageTime;

    public MessageModel(){

    }

    public MessageModel(String messageSubject, String messageText, String messageUser, String messageTime) {
        this.messageSubject = messageSubject;
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = messageTime;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
