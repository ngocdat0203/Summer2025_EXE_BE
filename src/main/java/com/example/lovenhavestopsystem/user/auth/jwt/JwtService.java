package com.example.lovenhavestopsystem.user.auth.jwt;

import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Value;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import io.jsonwebtoken.*;


@Service
public class JwtService {

/*
    private final static String  SECRET = "TmV3U2VjcmV0S2V5Rm9ySLdUU2lnbmluZ1B1cnBvc2VzMTIzNdu2Nzg=\r\n";

    private final static String  TOKEN_PREFIX = "Bearer ";*/


    private final String secretKey = "TmV3U2VjcmV0S2V5Rm9ySLdUU2lnbmluZ1B1cnBvc2VzMTIzNdu2Nzg="; // This should be securely stored and retrieved

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
/*    private final String secretKey;

    public JwtService() {
        this.secretKey = generateServiceKey();
    }

    public String generateServiceKey() {
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());

        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Error generating secret key", e);
        }
    }*/


    public String generateToken(int id, String email, List<RoleName> roles, String fullName, String address, int consultantId) {
        try {
            Map<String, Object> claims = new LinkedHashMap<>();
            claims.put("id", id);
            claims.put("email", email);
            claims.put("roles", roles.stream().map(RoleName::name).toList());
            claims.put("fullName", fullName);
            claims.put("address", address);
            /*
            claims.put("fullName", "Kỷ Ôić");
            claims.put("address", "Hồ Chí Minh");*/
            claims.put("consultantId", consultantId > 0 ? consultantId : null);

            // Thêm subject, issuedAt, expiration như các trường chuẩn
            claims.put("sub", email);
            claims.put("iat", System.currentTimeMillis() / 1000); // giây
            claims.put("exp", (System.currentTimeMillis() + 1000 * 60 * 60 * 10) / 1000); // 10h sau

            // Convert claims sang JSON UTF-8
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(claims);

            // Build token với payload JSON tự set
            String token = Jwts.builder()
                    .setPayload(jsonPayload)
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact();

            logger.info("Generated token: {}", token);
            System.out.println("✅ Full name: " + claims.get("fullName"));
            System.out.println("✅ Address: " + claims.get("address"));



            validateToken(token, new org.springframework.security.core.userdetails.User(email, "", new ArrayList<>()));

            return token;

        } catch (Exception e) {
            logger.error("Lỗi khi tạo token: {}", e.getMessage(), e);
            throw new RuntimeException("Không thể tạo JWT", e);
        }
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String usernameFromToken = claims.getSubject(); // hoặc claims.get("preferred_username")
            return usernameFromToken.equals(userDetails.getUsername());

        } catch (ExpiredJwtException e) {
            System.err.println("❌ Token hết hạn: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("❌ Token không hỗ trợ: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("❌ Token sai cấu trúc: " + e.getMessage());
        } catch (SignatureException e) {
            System.err.println("❌ Token sai chữ ký (signature invalid): " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Token rỗng hoặc sai: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Lỗi không xác định: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        return false;
    }


    private Key getKey() {

      byte[] keyBytes = Decoders.BASE64.decode(secretKey);
      System.out.println(keyBytes.length);
      return Keys.hmacShaKeyFor(keyBytes);

  }

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Authentication getAuthentication(String token) {
        try {
            Claims claims = extractAllClaims(token);
            String email = claims.getSubject(); // hoặc claims.get("email", String.class)

            // ⚠️ Dùng user cứng không cần DB
            UserDetails userDetails = new User(email, "", Collections.emptyList());

            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } catch (Exception e) {
            logger.error("❌ Lỗi khi trích xuất authentication từ token: {}", e.getMessage());
            return null;
        }
    }

    public String extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return String.valueOf(claims.get("id")); // hoặc lấy email nếu bạn dùng sub/email
    }

}
