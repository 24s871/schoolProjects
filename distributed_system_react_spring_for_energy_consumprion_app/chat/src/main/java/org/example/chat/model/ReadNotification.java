package org.example.chat.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadNotification {
    private Long senderId;
    private Long receiverId;
    private String message;


}

