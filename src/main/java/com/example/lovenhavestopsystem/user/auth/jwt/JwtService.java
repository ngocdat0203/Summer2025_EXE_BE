package com.example.lovenhavestopsystem.user.auth.jwt;

import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import com.google.api.client.util.Value;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


        Map<String, Object> claims = new HashMap<>();

        claims.put("id", id);
        claims.put("email", email);
        claims.put("roles", roles.stream().map(RoleName::name).toList());
        claims.put("fullName", new String (fullName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        claims.put("address", new String (address.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        if (consultantId > 0) {
            claims.put("consultantId", consultantId);
        } else {
            claims.put("consultantId", null);
        }

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        logger.info("Generated token: {}", token);


        validateToken(token, new org.springframework.security.core.userdetails.User(email, "", new ArrayList<>()));

        return token;


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

}
