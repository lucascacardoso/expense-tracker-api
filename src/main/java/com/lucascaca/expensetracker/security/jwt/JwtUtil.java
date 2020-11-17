package com.lucascaca.expensetracker.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.lucascaca.expensetracker.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	public static final String API_SECRET_KEY = "expensetrackerapikey";

    public static final long TOKEN_VALIDITY = 2 * 60 * 60 * 1000;

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
        return Jwts.parser().setSigningKey(API_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

	public Map<String, String> generateJWTToken(User user){
		
		long timestamp = System.currentTimeMillis();
		
		Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("authorities", user.getAuthorities());
        claims.put("userId", user.getUserId());
		
		String token = Jwts.builder()
						   .setClaims(claims)
						   .signWith(SignatureAlgorithm.HS256, API_SECRET_KEY)
						   .setIssuedAt(new Date(timestamp))
						   .setExpiration(new Date(timestamp + TOKEN_VALIDITY))						   
						   .compact();
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", token);
		return map;
	}

    public Boolean validateToken(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

}
