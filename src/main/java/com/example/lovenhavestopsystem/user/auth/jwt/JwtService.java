package com.example.lovenhavestopsystem.user.auth.jwt;

import com.example.lovenhavestopsystem.user.crud.entity.ConsultantProfiles;
import com.example.lovenhavestopsystem.user.crud.enums.RoleName;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;


@Service
public class JwtService {

/*
    private final static String  SECRET = "TmV3U2VjcmV0S2V5Rm9ySLdUU2lnbmluZ1B1cnBvc2VzMTIzNdu2Nzg=\r\n";

    private final static String  TOKEN_PREFIX = "Bearer ";*/

    private String secretKey;

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
    }


    public String generateToken(int id, String email, List<RoleName> roles, String fullName, String address, ConsultantProfiles consultantProfiles) {


        Map<String, Object> claims = new HashMap<>();



        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .claim("accountId", id)
                .claim("roles", roles)
                .claim("fullName", fullName)
                .claim("adrress", address)
                .claim("consultantProfiles", consultantProfiles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }

    private SecretKey getKey() {

        byte[] keyBytes = Base64.getDecoder().decode(secretKey);

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
