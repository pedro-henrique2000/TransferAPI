package com.project.transferapi.infra.security.config;

import com.project.transferapi.domain.ports.GenerateAccessTokenPort;
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

@Service
public class JWTService implements GenerateAccessTokenPort {

   @Value("${application.jwt.key}")
   private String secret;

   public String generateToken(UserDetails userDetails, Long id, String role) {
      Map<String, Object> extraClaims = new HashMap<>();
      extraClaims.put("role", role);

      return this.generateToken(extraClaims, userDetails, id);
   }

   private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, Long id) {
      return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setId(id.toString())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(this.getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
   }

   public boolean isTokenValid(final String jwt, UserDetails userDetails) {
      final String username = this.extractUsername(jwt);
      return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
   }

   public String extractUsername(final String jwt) {
      return this.extractClaim(jwt, Claims::getSubject);
   }

   private boolean isTokenExpired(String jwt) {
      Date expiration = this.extractExpiration(jwt);
      return expiration.before(new Date());
   }

   private Date extractExpiration(final String jwt) {
      return this.extractClaim(jwt, Claims::getExpiration);
   }

   private <T> T extractClaim(final String jwt, Function<Claims, T> claimsResolver) {
      final Claims claims = this.extractAllClaims(jwt);
      return claimsResolver.apply(claims);
   }

   private Claims extractAllClaims(String jwt) {
      return Jwts
            .parserBuilder()
            .setSigningKey(this.getSigningKey())
            .build()
            .parseClaimsJws(jwt)
            .getBody();
   }

   private Key getSigningKey() {
      byte[] keyBytes = Decoders.BASE64.decode(this.secret);
      return Keys.hmacShaKeyFor(keyBytes);
   }

}
