package com.example.bisneslogic.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class JwtService {

    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



    public String GenerateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }



    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
//@Service
//public class JwtService {
//    public String extractUsername;
//
//    @Value("${userBucket.path}")
//    private static final String SECRET_KEY = "984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf";
//
//        public String  generateToken(UserDetails userDetails){
//            return generateToken(new HashMap<>(), userDetails, "user");
//        }
//
//    public String generateToken(
//            Map<String, Object> extraClaims,
//            UserDetails userDetails
//    ){
//        return Jwts
//                .builder()
//                .setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//                .signWith(getSigInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, String role) {
//        Map<String, Object> claims = new HashMap<>(extraClaims);
//        claims.put("role", role);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//                .signWith(getSigInKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//
//
//
//    public boolean isTokenValid(String jwtToken, UserDetails userDetails){
//            final String username = extractUsername(jwtToken);
//            return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
//    }
//
//    private boolean isTokenExpired(String jwtToken) {
//            return extractExpiration(jwtToken).before(new Date());
//    }
//
//    private Date extractExpiration(String jwtToken) {
//            return extractClaims(jwtToken, Claims::getExpiration);
//    }
//
//    public  String extractUsername(String jwtToken){
//        return extractClaims(jwtToken, Claims::getSubject);
//    }
//
////    public  String extractRole(String jwtToken){
////        return extractClaims(jwtToken, Claims::);
////    }
//    public <T> T  extractClaims(String jwtToken, Function<Claims, T> claimResolver) {
//
//        final Claims claims = extractAllClaims(jwtToken);
//        return claimResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String jwtToken){
//        return Jwts
//                .parserBuilder()
//                .setSigningKey(getSigInKey())
//                .build()
//                .parseClaimsJws(jwtToken)
//                .getBody();
//    }
//
//    private Key getSigInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//}
