package org.example.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadReceipt {
    private Long messageId;
    private Integer readerId;
    private Integer senderId;
    private LocalDateTime readAt;

    public ReadReceipt(Long messageId, Integer senderId) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.readAt = LocalDateTime.now();
    }
}
