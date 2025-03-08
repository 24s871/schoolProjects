import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import axios from "axios";

export default function ChatRoom() {
    const [currentUser, setCurrentUser] = useState(null);
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);
    const [stompClient, setStompClient] = useState(null);
    const [messages, setMessages] = useState([]);
    const [inputMessage, setInputMessage] = useState("");

    useEffect(() => {
        if (currentUser) {
            fetchUsers();
            setupWebSocket();
        }
    }, [currentUser]);

    const setupWebSocket = () => {
        const socket = new SockJS('http://localhost:8080/ws');
        const client = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                console.log("Connected to WebSocket");
                client.subscribe(`/user/${currentUser.userId}/queue/messages`, (message) => {
                    const receivedMessage = JSON.parse(message.body);
                    setMessages((prevMessages) => [...prevMessages, receivedMessage]);
                });
            },
        });
        client.activate();
        setStompClient(client);

        return () => {
            if (client) {
                client.deactivate();
            }
        };
    };

    const fetchUsers = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/chat/users/${currentUser.userId}`);
            setUsers(response.data);
        } catch (err) {
            console.error("Error fetching users:", err);
        }
    };

    const handleUserChange = (event) => {
        const selectedUserId = parseInt(event.target.value);
        const user = users.find(u => u.userId === selectedUserId);
        setSelectedUser(user);
        fetchMessages(currentUser.userId, selectedUserId);
    };

    const fetchMessages = async (senderId, receiverId) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/chat/messages/${senderId}/${receiverId}`);
            setMessages(response.data);
        } catch (err) {
            console.error("Error fetching messages:", err);
        }
    };

    const sendMessage = () => {
        if (stompClient && inputMessage.trim() !== '' && selectedUser) {
            const chatMessage = {
                senderId: currentUser.userId,
                receiverId: selectedUser.userId,
                content: inputMessage,
            };
            stompClient.publish({
                destination: '/app/sendMessage',
                body: JSON.stringify(chatMessage)
            });
            setMessages((prevMessages) => [...prevMessages, chatMessage]);
            setInputMessage('');
        }
    };

    return (
        <div className="app">
            <h2 className="text-center">ChatRoom</h2>
            <div className="container">
                <div className="row">
                    <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
                        <h2 className="text-center m-4">Enter Chat</h2>
                        <div className="mb-3">
                            <label htmlFor="userId" className="form-label">User ID:</label>
                            <input type="number" className="form-control" placeholder="Enter your user ID"
                                   onChange={(e) => setCurrentUser({userId: parseInt(e.target.value)})}/>
                        </div>
                        <button className="btn btn-primary" onClick={setupWebSocket}>Join Chat</button>
                    </div>
                </div>
            </div>
            {currentUser && (
                <div>
                    <label htmlFor="user-select">Select user to chat:</label>
                    <select id="user-select" onChange={handleUserChange}>
                        <option value="">-- Choose a user --</option>
                        {users.map((user) => (
                            <option key={user.userId} value={user.userId}>
                                {user.username}
                            </option>
                        ))}
                    </select>

                    {selectedUser && (
                        <div>
                            <h3>Chat with {selectedUser.username}</h3>
                            <div className="chat-window">
                                {messages.map((msg, index) => (
                                    <p key={index}>
                                        <strong>{msg.senderId === currentUser.userId ? 'You' : selectedUser.username}:</strong> {msg.content}
                                    </p>
                                ))}
                            </div>
                            <input
                                type="text"
                                value={inputMessage}
                                onChange={(e) => setInputMessage(e.target.value)}
                                placeholder="Write a message..."
                            />
                            <button className="btn btn-primary" onClick={sendMessage}>Send</button>
                        </div>
                    )}
                </div>
            )}
        </div>
    );
}
