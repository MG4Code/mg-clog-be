package com.mg.clog.security;

import com.mg.clog.ClogProperties;
import com.mg.clog.user.data.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

  private final String secret;
  private final Long expirationTime;

  public JwtUtil(ClogProperties clogProperties) {
    this.secret = clogProperties.getJwtSecret();
    this.expirationTime = clogProperties.getJwtExpirationMs();
  }

  public Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes())).parseClaimsJws(token).getBody();
  }

  public String getUsernameFromToken(String token) {
    return getAllClaimsFromToken(token).getSubject();
  }

  public Date getExpirationDateFromToken(String token) {
    return getAllClaimsFromToken(token).getExpiration();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", user.getRoles());
    claims.put("userId", user.getId());
    return doGenerateToken(claims, user.getUsername());
  }

  private String doGenerateToken(Map<String, Object> claims, String username) {
    final Date createdDate = new Date();
    return Jwts.builder()
      .setClaims(claims)
      .setSubject(username)
      .setIssuedAt(createdDate)
      .setExpiration(new Date(createdDate.getTime() + expirationTime))
      .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secret.getBytes()))
      .compact();
  }

  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

}
