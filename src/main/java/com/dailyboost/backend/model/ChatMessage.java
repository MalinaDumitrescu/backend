package com.dailyboost.backend.model;

public class ChatMessage {
    private String fromUserId;
    private String toUserId;
    private String message;

    public ChatMessage() {}

    public ChatMessage(String fromUserId, String toUserId, String message) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
    }

    public String getFromUserId() { return fromUserId; }
    public void setFromUserId(String fromUserId) { this.fromUserId = fromUserId; }

    public String getToUserId() { return toUserId; }
    public void setToUserId(String toUserId) { this.toUserId = toUserId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
