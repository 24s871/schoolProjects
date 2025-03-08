import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

class WebSocketClient {
    constructor(url, onMessageCallback) {
        this.client = new Client({
            webSocketFactory: () => new SockJS(url),
            reconnectDelay: 5000,
            onConnect: () => {
                console.log("WebSocket connected");
                this.isConnected = true; // Setăm flag-ul când conexiunea este stabilită
                this.pendingSubscriptions.forEach(({ topic, callback }) => {
                    this.subscribe(topic, callback);
                });
                this.pendingSubscriptions = []; // Curățăm subscripțiile în așteptare
            },
            onDisconnect: () => {
                console.log("WebSocket disconnected");
                this.isConnected = false;
            },
            onStompError: (error) => console.error("WebSocket error", error),
        });

        this.onMessageCallback = onMessageCallback;
        this.isConnected = false; // Flag care indică starea conexiunii
        this.pendingSubscriptions = []; // Subscripții care așteaptă conectarea
    }

    connect() {
        this.client.activate();
    }

    subscribe(topic, callback) {
        if (this.isConnected) {
            this.client.subscribe(topic, (message) => {
                if (callback) callback(JSON.parse(message.body));
            });
        } else {
            // Dacă conexiunea nu este încă activă, adaugă subscripția în așteptare
            this.pendingSubscriptions.push({ topic, callback });
        }
    }

    send(destination, message) {
        if (this.isConnected) {
            this.client.publish({ destination, body: JSON.stringify(message) });
        } else {
            console.warn("WebSocket not connected. Message not sent.");
        }
    }

    disconnect() {
        this.client.deactivate();
    }
}

export default WebSocketClient;