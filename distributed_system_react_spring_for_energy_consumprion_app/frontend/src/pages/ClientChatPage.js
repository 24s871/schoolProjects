import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import axios from "axios";

export default function ClientChatPage() {
    const { id } = useParams();
    const userId = Number(id);

    const [stompClient, setStompClient] = useState(null);
    const [messages, setMessages] = useState([]);
    const [inputMessage, setInputMessage] = useState("");
    const [user, setUser] = useState({
        firstName: "",
        lastName: "",
        age: "",
        username: "",
        role: ""
    });

    useEffect(() => {
        loadUser();
        setupWebSocket();

        return () => {
            if (stompClient) stompClient.deactivate();
        };
    }, []);

    const loadUser = async () => {
        try {
            const result = await axios.get(`http://localhost:8080/api/user/${userId}`);
            setUser(result.data);
        } catch (error) {
            console.error("Error loading user:", error);
        }
    };

    const setupWebSocket = () => {
        const socket = new SockJS("http://localhost:8080/ws");
        const client = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                console.log("Connected to WebSocket");
                client.subscribe(`/user/${userId}/queue/messages`, onMessageReceived);
            },
            onStompError: (error) => {
                console.error("WebSocket error:", error);
            },
        });

        client.activate();
        setStompClient(client);
    };

    const onMessageReceived = (message) => {
        const messageData = JSON.parse(message.body);
        setMessages(prev => [...prev, messageData]);
    };

    const sendMessage = () => {
        if (inputMessage.trim() === "" || !stompClient) return;

        const chatMessage = {
            senderId: userId,
            receiverId: 1, // Assuming admin ID is 1
            content: inputMessage,
        };

        stompClient.publish({
            destination: '/app/sendMessage',
            body: JSON.stringify(chatMessage)
        });

        setMessages(prev => [...prev, chatMessage]);
        setInputMessage("");
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>Chat with Admin</h2>
            <div style={{
                border: "1px solid #ccc",
                padding: "10px",
                height: "300px",
                overflowY: "scroll",
            }}>
                {messages.map((msg, index) => (
                    <div key={index}>
                        <strong>{msg.senderId === userId ? 'You' : 'Admin'}:</strong> {msg.content}
                    </div>
                ))}
            </div>
            <input
                type="text"
                value={inputMessage}
                onChange={(e) => setInputMessage(e.target.value)}
                style={{ width: "80%" }}
            />
            <button onClick={sendMessage} style={{ marginLeft: "10px" }}>
                Send
            </button>
        </div>
    );
}
