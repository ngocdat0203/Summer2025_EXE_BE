package com.example.lovenhavestopsystem.socket;

import com.example.lovenhavestopsystem.user.auth.jwt.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {

    private final JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if ("CONNECT".equals(accessor.getCommand().name())) {
            String token = accessor.getFirstNativeHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                try {
                    String username = jwtService.extractUsername(token);
                    var dummyUserDetails = new User(username, "", Collections.emptyList());

                    if (jwtService.validateToken(token, dummyUserDetails)) {
                        var authentication = new UsernamePasswordAuthenticationToken(
                                dummyUserDetails, null, dummyUserDetails.getAuthorities()
                        );

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        accessor.setUser(authentication); // 👈 QUAN TRỌNG để gửi về /user/queue/*
                    }
                } catch (Exception e) {
                    log.error("❌ Token validation in STOMP failed: {}", e.getMessage());
                }
            }
        }

        return message;
    }
}
