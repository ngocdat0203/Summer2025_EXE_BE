/*
package com.example.lovenhavestopsystem.socket;

import com.example.lovenhavestopsystem.user.auth.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;


import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ChatSocketHandler extends TextWebSocketHandler {

    @Autowired
    private JwtService jwtService; // 👈 Bạn cần thêm JwtService để xác thực token

    // Map<conversationId, Map<userEmail, session>>
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketSession>> conversationSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        var queryParams = session.getUri().getQuery(); // token=...&conversationId=...
        String[] params = queryParams.split("&");

        String token = null;
        String conversationId = null;

        for (String param : params) {
            if (param.startsWith("token=")) {
                token = param.substring("token=".length());
            } else if (param.startsWith("conversationId=")) {
                conversationId = param.substring("conversationId=".length());
            }
        }

        // Validate token
        if (token == null || conversationId == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        String userId = jwtService.extractUserId(token); // 👈 Bạn cần thêm hàm này

        conversationSessions
                .computeIfAbsent(conversationId, k -> new ConcurrentHashMap<>())
                .put(userId, session);

        System.out.println("✅ Connected user: " + userId + " to conversation: " + conversationId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); // format: conversationId|messageContent
        String[] parts = payload.split("\\|", 2);

        if (parts.length < 2) {
            log.warn("⚠️ Invalid message format: {}", payload);
            return;
        }

        String conversationId = parts[0];
        String content = parts[1];

        String senderEmail = (String) session.getAttributes().get("userEmail");

        Map<String, WebSocketSession> participants = conversationSessions.get(conversationId);
        if (participants != null) {
            for (Map.Entry<String, WebSocketSession> entry : participants.entrySet()) {
                WebSocketSession receiverSession = entry.getValue();
                if (receiverSession != null && receiverSession.isOpen()) {
                    receiverSession.sendMessage(new TextMessage(senderEmail + ": " + content));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        for (Map<String, WebSocketSession> participants : conversationSessions.values()) {
            participants.values().removeIf(s -> s.equals(session));
        }
        log.info("🔌 WebSocket connection closed");
    }
}
*/
