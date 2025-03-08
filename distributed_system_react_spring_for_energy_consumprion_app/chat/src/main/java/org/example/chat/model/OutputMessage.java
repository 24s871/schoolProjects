package org.example.chat.model;

import java.time.LocalDateTime;

public class OutputMessage {
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private LocalDateTime timestamp;
    private String senderUsername;

    // Constructors
    public OutputMessage() {}

    public OutputMessage(ChatMessage chatMessage, String senderUsername) {
        this.senderId = chatMessage.getSenderId();
        this.receiverId = chatMessage.getReceiverId();
        this.content = chatMessage.getContent();
        this.timestamp = chatMessage.getTimestamp();
        this.senderUsername = senderUsername;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
}

