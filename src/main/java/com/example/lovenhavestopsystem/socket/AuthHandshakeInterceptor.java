package com.example.lovenhavestopsystem.socket;

import com.example.lovenhavestopsystem.user.auth.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            String token = httpRequest.getParameter("token");

            if (token != null && !token.isBlank()) {
                try {
                    String username = jwtService.extractUsername(token);

                    var dummyUserDetails = new User(username, "", Collections.emptyList());
                    if (jwtService.validateToken(token, dummyUserDetails)) {
                        var authentication = new UsernamePasswordAuthenticationToken(
                                dummyUserDetails, null, dummyUserDetails.getAuthorities()
                        );

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        attributes.put("username", username); // 👈 Cần để định tuyến người dùng
                        return true;
                    }
                } catch (Exception e) {
                    log.error("❌ Token validation failed: {}", e.getMessage());
                }
            }
        }

        return false;
    }



    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // Không cần xử lý gì ở đây
    }
}
