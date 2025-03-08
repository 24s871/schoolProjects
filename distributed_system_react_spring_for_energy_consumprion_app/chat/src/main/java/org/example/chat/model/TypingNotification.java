package org.example.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypingNotification {
    private Integer senderId;
    private Integer receiverId;
    private boolean isTyping;
}
