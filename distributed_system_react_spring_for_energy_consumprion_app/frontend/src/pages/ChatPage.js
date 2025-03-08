import React, { useState, useEffect, useCallback, useRef } from 'react';
import { useParams } from "react-router-dom";
import axios from "axios";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

const ChatPage = () => {
    const { userId, sendId } = useParams();
    const currentUserId = Number(userId);
    const receiverId = Number(sendId);

    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState("");
    const [notifications, setNotifications] = useState(0);
    const [showNotification, setShowNotification] = useState(false);
    const [user, setUser] = useState({
        userId: "",
        firstName: "",
        lastName: "",
        age: "",
        username: "",
        role: ""
    });
    const [receiver, setReceiver] = useState({
        userId: "",
        firstName: "",
        lastName: "",
        username: "",
        role: ""
    });
    const [stompClient, setStompClient] = useState(null);
    const [isTyping, setIsTyping] = useState(false);
    const [lastReadMessageId, setLastReadMessageId] = useState(null);
    const [isConnected, setIsConnected] = useState(false);

    const messagesEndRef = useRef(null);

    const loadConversation = useCallback(async () => {
        try {
            //http://localhost:8084
            const result = await axios.get(`http://localhost/chatapp/api/chat/chatmessage/${currentUserId}/${receiverId}`);
            setMessages(result.data.map(msg => ({...msg, status: msg.readAt ? 'read' : 'sent'})));

            // Send read receipts for unread messages from the other user
            const unreadMessages = result.data.filter(msg => msg.senderId === receiverId && !msg.readAt);
            unreadMessages.forEach(msg => {
                sendReadReceipt(msg.messageId);
            });
        } catch (error) {
            console.error("Error loading conversation:", error);
        }
    }, [currentUserId, receiverId]);

    const loadUser = async () => {
        try {
            const result = await axios.get(`http://localhost/utilizatorapp/user/${currentUserId}`);
            setUser(result.data);
        } catch (error) {
            console.error("Error loading user:", error);
        }
    };

    const loadReceiver = async () => {
        try {
            //http://localhost:8083
            const result = await axios.get(`http://localhost/utilizatorapp/user/${receiverId}`);
            setReceiver(result.data);
        } catch (error) {
            console.error("Error loading receiver:", error);
        }
    };

    useEffect(() => {
        loadUser();
        loadReceiver();
        loadConversation();
        setupWebSocket();

        return () => {
            if (stompClient) {
                stompClient.deactivate();
            }
        };
    }, [loadConversation]);

    useEffect(() => {
        let reconnectTimeout;

        const attemptReconnect = () => {
            if (!stompClient || !stompClient.connected) {
                console.log("Attempting to reconnect...");
                setupWebSocket();
            }
        };

        if (!isConnected) {
            reconnectTimeout = setTimeout(attemptReconnect, 5000);
        }

        return () => {
            if (reconnectTimeout) clearTimeout(reconnectTimeout);
        };
    }, [isConnected, stompClient]);

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    const setupWebSocket = () => {
        if (stompClient) {
            stompClient.deactivate();
        }
        //http://localhost:8084
        const socket = new SockJS("http://localhost/chatapp/ws");
        const client = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                console.log("Connected to WebSocket");
                setIsConnected(true);
                client.subscribe(`/user/${currentUserId}/queue/messages`, onMessageReceived);
                client.subscribe(`/user/${currentUserId}/queue/typing`, onTypingReceived);
                client.subscribe(`/user/${currentUserId}/queue/read-receipts`, onReadReceived);
            },
            onDisconnect: () => {
                console.log("Disconnected from WebSocket");
                setIsConnected(false);
            },
            onStompError: (error) => {
                console.error("WebSocket error:", error);
                setIsConnected(false);
            },
        });

        client.activate();
        setStompClient(client);
    };

    const onMessageReceived = (message) => {
        try {
            const receivedMessage = JSON.parse(message.body);
            setMessages(prevMessages => {
                if (!prevMessages.some(msg => msg.messageId === receivedMessage.messageId)) {
                    setNotifications(prev => prev + 1);
                    setShowNotification(true);
                    setTimeout(() => setShowNotification(false), 5000);
                    sendReadReceipt(receivedMessage.messageId);
                    return [...prevMessages, {...receivedMessage, status: 'read'}];
                }
                return prevMessages;
            });
        } catch (error) {
            console.error("Error processing received message:", error);
        }
    };

    const onTypingReceived = () => {
        setIsTyping(true);
        setTimeout(() => setIsTyping(false), 3000);
    };

    const onReadReceived = (readNotification) => {
        const { messageId } = JSON.parse(readNotification.body);
        setLastReadMessageId(messageId);
        setMessages(prevMessages => prevMessages.map(msg =>
            msg.senderId === currentUserId && msg.messageId <= messageId
                ? { ...msg, status: 'read' }
                : msg
        ));
    };

    const handleSendMessage = () => {
        if (newMessage.trim() !== "" && stompClient && isConnected) {
            try {
                const chatMessage = {
                    senderId: currentUserId,
                    receiverId: receiverId,
                    content: newMessage,
                };

                stompClient.publish({
                    destination: "/app/chat.sendMessage",
                    body: JSON.stringify(chatMessage),
                });

                setMessages(prevMessages => [...prevMessages, {...chatMessage, messageId: Date.now(), status: 'sent'}]);
                setNewMessage("");
            } catch (error) {
                console.error("Error sending message:", error);
            }
        } else if (!isConnected) {
            console.error("Cannot send message: WebSocket not connected");
        }
    };

    const handleTyping = () => {
        if (stompClient && isConnected) {
            stompClient.publish({
                destination: '/app/typing',
                body: JSON.stringify({ senderId: currentUserId, receiverId: receiverId, isTyping: true })
            });
        }
    };

    const sendReadReceipt = (messageId) => {
        if (stompClient && isConnected) {
            stompClient.publish({
                destination: '/app/read',
                body: JSON.stringify({ messageId: messageId, senderId: currentUserId, receiverId: receiverId })
            });
        }
    };

    const clearNotifications = () => {
        setNotifications(0);
        setShowNotification(false);
    };

    return (
        <div className="app">
            <div style={{ color: isConnected ? 'green' : 'red', marginBottom: '10px' }}>
                {isConnected ? 'Connected' : 'Disconnected'}
            </div>
            {showNotification && notifications > 0 && (
                <div
                    style={{
                        position: 'fixed',
                        top: '20px',
                        right: '20px',
                        backgroundColor: '#4CAF50',
                        color: 'white',
                        padding: '12px 24px',
                        borderRadius: '8px',
                        boxShadow: '0 4px 6px rgba(0,0,0,0.1)',
                        zIndex: 1000,
                        cursor: 'pointer'
                    }}
                    onClick={clearNotifications}
                >
                    {notifications} new message{notifications !== 1 ? 's' : ''} from {receiver.username}
                </div>
            )}
            <h1>Chat with {receiver.username}</h1>
            <div
                style={{
                    height: '600px',
                    overflowY: 'scroll',
                    border: '2px solid black',
                    backgroundColor: 'white',
                    padding: '20px'
                }}
                onClick={clearNotifications}
            >
                {messages.map((msg, index) => (
                    <div key={index} style={{
                        padding: '10px',
                        margin: '5px',
                        backgroundColor: msg.senderId === currentUserId ? '#e3f2fd' : '#f5f5f5',
                        borderRadius: '5px',
                        maxWidth: '80%',
                        marginLeft: msg.senderId === currentUserId ? 'auto' : '10px'
                    }}>
                        <b>{msg.senderId === currentUserId ? user.username : receiver.username}:</b> {msg.content}
                        {msg.senderId === currentUserId && (
                            <span style={{ color: msg.status === 'read' ? 'green' : 'gray', marginLeft: '5px', fontSize: '12px' }}>
                                {msg.status === 'read' ? 'Read' : 'Sent'}
                            </span>
                        )}
                    </div>
                ))}
                <div ref={messagesEndRef} />
            </div>
            {isTyping && <div style={{ color: 'gray' }}>{receiver.username} is typing...</div>}
            <div style={{ display: 'flex', gap: '10px', marginTop: '10px' }}>
                <input
                    type="text"
                    value={newMessage}
                    onChange={(e) => {
                        setNewMessage(e.target.value);
                        handleTyping();
                    }}
                    placeholder="Type a message..."
                    style={{ flex: 1, padding: '8px', borderRadius: '4px', border: '1px solid #ccc' }}
                    onKeyPress={(e) => e.key === 'Enter' && handleSendMessage()}
                />
                <button
                    className="btn btn-primary"
                    onClick={handleSendMessage}
                    style={{ padding: '8px 20px' }}
                >
                    Send
                </button>
            </div>
        </div>
    );
};

export default ChatPage;
