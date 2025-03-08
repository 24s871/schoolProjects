package org.example.chat.Controller;
import org.example.chat.model.*;
import org.example.chat.repository.MessageRepository;
import org.example.chat.service.UserServiceClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(value = "http://localhost:3000",allowCredentials = "true")

public class ChatController {

    //jdbc:mysql://localhost:3306/Chat

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;
    RestTemplate restTemplate = new RestTemplate();
    private final UserServiceClient userServiceClient;
    private final Map<String, List<Message>> conversationMap = new HashMap<>();

    public ChatController(SimpMessagingTemplate messagingTemplate, MessageRepository messageRepository, UserServiceClient userServiceClient) {
        this.messagingTemplate = messagingTemplate;
        this.messageRepository = messageRepository;
        this.userServiceClient = userServiceClient;
    }

    /*
    private String generateConversationKey(int senderId, int receiverId) {
        return senderId < receiverId ? senderId + "_" + receiverId : receiverId + "_" + senderId;
    }
    @GetMapping("/conversations/{senderId}/{receiverId}")
    public List<Message> getConversation(@PathVariable int senderId, @PathVariable int receiverId) {
        String conversationKey = generateConversationKey(senderId, receiverId);
        return conversationMap.getOrDefault(conversationKey, new ArrayList<>());
    }

    @GetMapping("/conversations/{senderId}/{receiverId}/typing")
    public List<Message> getConversationTyping(@PathVariable int senderId, @PathVariable int receiverId) {
        String conversationKey = generateConversationKey(senderId, receiverId);
        List<Message> messages = conversationMap.getOrDefault(conversationKey, new ArrayList<>());

        messages.removeIf(message ->  "is typing".equals(message.getContent()));
//message.getSenderId() == senderId
        return messages;
    }

    @PostMapping("/conversations/{senderId}/{receiverId}/typing")
    public void addTypingMessage(@PathVariable int senderId, @PathVariable int receiverId, @RequestBody String sender) {
        String conversationKey = generateConversationKey(senderId, receiverId);
        List<Message> messages = conversationMap.computeIfAbsent(conversationKey, k -> new ArrayList<>());

        Message typingMessage = new Message();
        typingMessage.setSender(sender);
        typingMessage.setSenderId(senderId);
        typingMessage.setReceiverId(receiverId);
        typingMessage.setContent("is typing");

        messages.add(typingMessage);
    }


    @MessageMapping("/chat")
    public void broadcastMessage(@Payload Message message) {
        String conversationKey = generateConversationKey(message.getSenderId(), message.getReceiverId());
        conversationMap.computeIfAbsent(conversationKey, k -> new ArrayList<>()).add(message);
        messagingTemplate.convertAndSend("/topic/messages", message);
    }

    @MessageMapping("/chat/read")
    public void handleReadNotification(ReadNotification notification) {
        // Trimitere notificare către destinatar folosind /queue
        messagingTemplate.convertAndSendToUser(
                String.valueOf(notification.getReceiverId()),
                "/queue/notifications",
                notification
        );
    }

    @PostMapping("/conversations/{senderId}/{receiverId}/read")
    public ResponseEntity<List<Message>> markMessagesAsRead(
            @PathVariable int senderId,
            @PathVariable int receiverId) {

        Integer readerId = receiverId;

        // Găsește cheia conversației
        String conversationKey = generateConversationKey(senderId, receiverId);

        // Găsește lista de mesaje
        List<Message> messages = conversationMap.getOrDefault(conversationKey, new ArrayList<>());

        // Verificăm dacă există mesaje necitite
        boolean unreadMessagesExist = false;
        for (Message message : messages) {
            if (!message.getRead())
            {
                unreadMessagesExist = true;
            }
        }

        // Dacă nu există mesaje necitite, nu facem nimic
        if (!unreadMessagesExist) {
            return ResponseEntity.ok(messages);  // Returnează lista de mesaje fără modificări
        }

        // Marchează mesajele necitite ca citite
        for (Message message : messages) {
            if (message.getReceiverId() == readerId && !message.getRead()) {
                message.setRead(true);
            }
        }

        // Returnează lista actualizată de mesaje cu statusul de citire
        return ResponseEntity.ok(messages);
    }*/

    @GetMapping("/api/chat/chatmessage/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessage>> getMessages(
            @PathVariable Integer senderId,
            @PathVariable Integer receiverId) {
        List<ChatMessage> messages = messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
                senderId, receiverId, receiverId, senderId);

        // Mark messages as read
        messages.stream()
                .filter(msg -> msg.getReceiverId().equals(senderId) && msg.getReadAt() == null)
                .forEach(msg -> {
                    msg.setReadAt(LocalDateTime.now());
                    messageRepository.save(msg);

                    // Send read receipt to the sender
                    ReadReceipt readReceipt = new ReadReceipt(msg.getMessageId().longValue(), msg.getSenderId());
                    messagingTemplate.convertAndSendToUser(
                            String.valueOf(msg.getSenderId()),
                            "/queue/read-receipts",
                            readReceipt
                    );
                });

        return ResponseEntity.ok(messages);
    }
/*
    @GetMapping("/api/chat/users/{currentUserId}")
    public ResponseEntity<List<User>> getChatUsers(@RequestParam List<Integer> currentUserIds) {
        //List<Integer> userIds = messageRepository.findAllChatUsers(currentUserId);
        //List<User> users = userRepository.findAllById(userIds);
        //return ResponseEntity.ok(users);
        List<User> users=userServiceClient.getUsersByIds(currentUserIds);
        return ResponseEntity.ok(users);
    }
*/

    @MessageMapping("/typing")
    public void handleTyping(@Payload TypingNotification notification) {
        messagingTemplate.convertAndSendToUser(
                String.valueOf(notification.getReceiverId()),
                "/queue/typing",
                notification
        );
    }

    @MessageMapping("/chat.sendMessage")
    @SendToUser("/queue/messages")
    public OutputMessage sendMessage(@Payload ChatMessage chatMessage, Principal principal) {
        chatMessage.setTimestamp(LocalDateTime.now());
        ChatMessage savedMessage = messageRepository.save(chatMessage);

        //User sender = userRepository.findById(chatMessage.getSenderId())
        //        .orElseThrow(() -> new RuntimeException("Sender not found"));

        //8083
        String url="http://localhost/utilizatorapp/chat/"+chatMessage.getSenderId();
        String response=restTemplate.exchange(url, HttpMethod.GET, null, String.class).getBody();
        System.out.println(response);

        OutputMessage outputMessage = new OutputMessage(savedMessage, response);

        // Send to receiver
        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getReceiverId()),
                "/queue/messages",
                outputMessage
        );

        // Return to sender
        return outputMessage;
    }



    @MessageMapping("/read-receipt")
    public void handleReadReceipt(@Payload ReadReceipt readReceipt) {
        ChatMessage message = messageRepository.findById(Math.toIntExact(readReceipt.getMessageId()))
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setReadAt(LocalDateTime.now());
        messageRepository.save(message);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(readReceipt.getSenderId()),
                "/queue/read-receipts",
                readReceipt
        );
    }


}

