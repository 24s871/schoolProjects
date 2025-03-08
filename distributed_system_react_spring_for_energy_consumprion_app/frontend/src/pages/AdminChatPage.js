import React, { useState, useEffect, useCallback, useRef } from 'react';
import axios from "axios";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { debounce } from 'lodash';

const AdminChatPage = () => {
    const adminId = 1; // Replace with actual admin ID

    const [clients, setClients] = useState([]);
    const [selectedClient, setSelectedClient] = useState(null);
    const [messages, setMessages] = useState({});
    const [newMessage, setNewMessage] = useState("");
    const [notifications, setNotifications] = useState({});
    const [stompClient, setStompClient] = useState(null);
    const [typingStatus, setTypingStatus] = useState({});
    const [isConnected, setIsConnected] = useState(false);

    const messagesEndRef = useRef(null);

    const loadClients = useCallback(async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/users');
            setClients(response.data.filter(user => user.role !== 'ADMIN'));
        } catch (error) {
            console.error("Error loading clients:", error);
        }
    }, []);

    const onMessageReceived = useCallback((message) => {
        const receivedMessage = JSON.parse(message.body);
        setMessages(prevMessages => ({
            ...prevMessages,
            [receivedMessage.senderId]: [
                ...(prevMessages[receivedMessage.senderId] || []),
                receivedMessage
            ]
        }));
        setNotifications(prevNotifications => ({
            ...prevNotifications,
            [receivedMessage.senderId]: (prevNotifications[receivedMessage.senderId] || 0) + 1
        }));
        if (receivedMessage.senderId === selectedClient) {
            sendReadReceipt(receivedMessage.messageId, receivedMessage.senderId);
        }
    }, [selectedClient]);

    const onTypingReceived = useCallback((typingNotification) => {
        const { senderId, isTyping } = JSON.parse(typingNotification.body);
        setTypingStatus(prevStatus => ({
            ...prevStatus,
            [senderId]: isTyping
        }));
        if (isTyping) {
            setTimeout(() => {
                setTypingStatus(prevStatus => ({
                    ...prevStatus,
                    [senderId]: false
                }));
            }, 3000);
        }
    }, []);

    const onReadReceived = useCallback((readNotification) => {
        const { senderId, messageId } = JSON.parse(readNotification.body);
        setMessages(prevMessages => ({
            ...prevMessages,
            [senderId]: Array.isArray(prevMessages[senderId])
                ? prevMessages[senderId].map(msg =>
                    msg.messageId <= messageId ? { ...msg, status: 'read' } : msg
                )
                : []
        }));
    }, []);

    const setupWebSocket = useCallback(() => {
        if (stompClient) {
            stompClient.deactivate();
        }

        const socket = new SockJS("http://localhost:8080/ws");
        const client = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                console.log("Connected to WebSocket");
                setIsConnected(true);
                client.subscribe(`/user/${adminId}/queue/messages`, onMessageReceived);
                client.subscribe(`/user/${adminId}/queue/typing`, onTypingReceived);
                client.subscribe(`/user/${adminId}/queue/read-receipts`, onReadReceived);
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
    }, [adminId, onMessageReceived, onTypingReceived, onReadReceived, stompClient]);

    useEffect(() => {
        loadClients();
        setupWebSocket();

        return () => {
            if (stompClient) {
                stompClient.deactivate();
            }
        };
    }, [loadClients, setupWebSocket, stompClient]);

    useEffect(() => {
        if (selectedClient && messagesEndRef.current) {
            messagesEndRef.current.scrollIntoView({ behavior: "smooth" });
        }
    }, [messages, selectedClient]);

    const handleClientSelect = useCallback((clientId) => {
        setSelectedClient(clientId);
        if (!messages[clientId]) {
            fetchMessages(clientId);
        }
        clearNotifications(clientId);
    }, [messages]);

    const fetchMessages = useCallback(async (clientId) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/chat/chatmessage/${adminId}/${clientId}`);
            setMessages(prevMessages => ({
                ...prevMessages,
                [clientId]: response.data
            }));
            response.data.forEach(msg => {
                if (msg.senderId === clientId) {
                    sendReadReceipt(msg.messageId, clientId);
                }
            });
        } catch (error) {
            console.error("Error fetching messages:", error);
        }
    }, [adminId]);

    const handleSendMessage = useCallback(async () => {
        if (newMessage.trim() === "" || !selectedClient || !isConnected) return;

        const chatMessage = {
            senderId: adminId,
            receiverId: selectedClient,
            content: newMessage,
            messageId: Date.now(),
            status: 'sent'
        };

        try {
            stompClient.publish({
                destination: "/app/chat.sendMessage",
                body: JSON.stringify(chatMessage),
            });

            setMessages(prevMessages => ({
                ...prevMessages,
                [selectedClient]: [...(prevMessages[selectedClient] || []), chatMessage]
            }));
            setNewMessage("");
        } catch (error) {
            console.error("Error sending message:", error);
        }
    }, [newMessage, selectedClient, isConnected, adminId, stompClient]);

    const handleTyping = useCallback(debounce(() => {
        if (stompClient && selectedClient && isConnected) {
            stompClient.publish({
                destination: '/app/typing',
                body: JSON.stringify({ senderId: adminId, receiverId: selectedClient, isTyping: true })
            });
        }
    }, 300), [stompClient, selectedClient, isConnected, adminId]);

    const sendReadReceipt = useCallback((messageId, senderId) => {
        if (stompClient && isConnected) {
            stompClient.publish({
                destination: '/app/read',
                body: JSON.stringify({ messageId: messageId, senderId: adminId, receiverId: senderId })
            });
        }
    }, [stompClient, isConnected, adminId]);

    const clearNotifications = useCallback((clientId) => {
        setNotifications(prevNotifications => ({
            ...prevNotifications,
            [clientId]: 0
        }));
    }, []);

    return (
        <div style={{ display: 'flex', height: '100vh' }}>
            <div style={{ width: '30%', borderRight: '1px solid #ccc', padding: '20px', overflowY: 'auto' }}>
                <h2>Clients</h2>
                <div style={{ color: isConnected ? 'green' : 'red', marginBottom: '10px' }}>
                    {isConnected ? 'Connected' : 'Disconnected'}
                </div>
                {clients.map(client => (
                    <div
                        key={client.userId}
                        onClick={() => handleClientSelect(client.userId)}
                        style={{
                            padding: '10px',
                            cursor: 'pointer',
                            backgroundColor: selectedClient === client.userId ? '#e0e0e0' : 'transparent',
                            position: 'relative'
                        }}
                    >
                        {client.username}
                        {notifications[client.userId] > 0 && (
                            <span style={{
                                position: 'absolute',
                                right: '10px',
                                backgroundColor: 'red',
                                color: 'white',
                                borderRadius: '50%',
                                padding: '2px 6px',
                                fontSize: '12px'
                            }}>
                                {notifications[client.userId]}
                            </span>
                        )}
                    </div>
                ))}
            </div>
            <div style={{ width: '70%', padding: '20px' }}>
                {selectedClient ? (
                    <>
                        <h2>Chat with {clients.find(c => c.userId === selectedClient)?.username}</h2>
                        <div style={{ height: 'calc(100vh - 200px)', overflowY: 'auto', border: '1px solid #ccc', padding: '10px', marginBottom: '10px' }}>
                            {messages[selectedClient]?.map((msg, index) => (
                                <div key={index} style={{
                                    padding: '10px',
                                    margin: '5px',
                                    backgroundColor: msg.senderId === adminId ? '#e3f2fd' : '#f5f5f5',
                                    borderRadius: '5px',
                                    maxWidth: '80%',
                                    marginLeft: msg.senderId === adminId ? 'auto' : '10px'
                                }}>
                                    <b>{msg.senderId === adminId ? 'You' : clients.find(c => c.userId === selectedClient)?.username}:</b> {msg.content}
                                    {msg.senderId === adminId && (
                                        <span style={{ color: msg.status === 'read' ? 'green' : 'gray', marginLeft: '5px', fontSize: '12px' }}>
                            {msg.status === 'read' ? 'Read' : 'Sent'}
                        </span>
                                    )}
                                </div>
                            ))}
                            <div ref={messagesEndRef} />
                        </div>
                        {typingStatus[selectedClient] && <div style={{ color: 'gray' }}>Client is typing...</div>}
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
                                onClick={handleSendMessage}
                                style={{ padding: '8px 20px' }}
                                disabled={!isConnected}
                            >
                                Send
                            </button>
                        </div>
                    </>
                ) : (
                    <h2>Select a client to start chatting</h2>
                )}
            </div>
        </div>
    );
};

export default AdminChatPage;
