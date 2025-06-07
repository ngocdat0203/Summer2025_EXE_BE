package com.example.lovenhavestopsystem.socket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatSocketHandler extends TextWebSocketHandler {

    // Lưu phiên kết nối của userId
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Lấy userId từ query param (vd: /ws/chat?userId=123)
        String userId = session.getUri().getQuery().split("=")[1];
        sessions.put(userId, session);
        System.out.println("Connected user: " + userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Format tin nhắn: senderId|receiverId|nội dung
        String[] parts = message.getPayload().split("\\|", 3);
        String senderId = parts[0];
        String receiverId = parts[1];
        String content = parts[2];

        System.out.println("[" + senderId + " -> " + receiverId + "] " + content);

        // Gửi lại cho người nhận nếu online
        WebSocketSession receiverSession = sessions.get(receiverId);
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(new TextMessage(senderId + ": " + content));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
    }
}
