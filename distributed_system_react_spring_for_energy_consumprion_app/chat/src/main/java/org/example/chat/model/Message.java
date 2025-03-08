package org.example.chat.model;

import lombok.*;
@Getter
@Setter
@Data
public class Message {
    private String sender;
    private String recipient;
    private String content;
    private Integer senderId;
    private Integer receiverId;
    private Boolean read = false;
}
