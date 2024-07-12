//package com.example.woori_base.cofig;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//@Slf4j
//public class JWTConfig {
//
//    @Value("${JWT_SECRET}")
//    private String JWT_SECRET;
//
//    @Value("${JWT_TIME_ACCESS_TOKEN}")
//    private String JWT_TIME_ACCESS_TOKEN;
//
//    public String generateToken(CustomUserDetailConfig userDetails) {
//        // Lấy thông tin user
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + JWT_TIME_ACCESS_TOKEN);
//        // Tạo chuỗi json web token từ id của user.
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
//                .compact();
//    }
//
//    public Claims getUserIdFromJWT(String token) {
//        JwtParser parser = Jwts.parser()
//                .setSigningKey(JWT_SECRET)
//                .build();
//        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
//        Claims claims = claimsJws.getBody();
//        return claims;
//    }
//
//    public boolean validateToken(String authToken) {
//        try {
//            Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
//            Jwts.parser()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(authToken);
//            return true;
//        } catch (MalformedJwtException ex) {
//            log.error("Invalid JWT token");
//        } catch (ExpiredJwtException ex) {
//            log.error("Expired JWT token");
//        } catch (UnsupportedJwtException ex) {
//            log.error("Unsupported JWT token");
//        } catch (IllegalArgumentException ex) {
//            log.error("JWT claims string is empty.");
//        }
//        return false;
//    }
//}
