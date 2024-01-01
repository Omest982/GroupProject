package org.example.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.entity.User;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtService {
    private final static String SECRET = "CRClPakWbPafJH12umy4hdV4MNNeutKv62UjIsg278Uh4nfj7";
    private final static long EXPIRATION_TIME = 1000 * 60; //1 hour

    public static String generateToken(User user){
        return generateToken(new HashMap<>(), user);
    }

    public static String generateToken(
            Map<String, Object> extraClaims,
            User user){


        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean isTokenValid(String token, User user){
        String email = extractEmail(token);

        return (user.getEmail().equals(email) && !isTokenExpired(token));
    }

    public static boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public static Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public static String extractEmail(String token){
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
