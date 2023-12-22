package org.example.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private final static String SECRET = "hHu3a8d9jHFIUh3f2";
    private final static long EXPIRATION_TIME = 1000 * 60; //1 hour

    public static String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public static String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);

        return (userDetails.getUsername().equals(username) && !isTokenExpired(token));
    }

    public static boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public static Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public static String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    private static Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
