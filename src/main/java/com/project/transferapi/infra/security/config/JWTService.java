package com.project.transferapi.infra.security.config;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.TokenHandlerPort;
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
public class JWTService implements TokenHandlerPort {

   @Value("${application.jwt.key}")
   private String secret;

   @Value("${application.jwt.expiration}")
   private long tokenExpiration;

   @Value("${application.jwt.refresh-token.expiration}")
   private long refreshTokenExpiration;

   public String generateToken(UserDetails userDetails) {
      User user = (User) userDetails;
      Map<String, Object> extraClaims = new HashMap<>();
      extraClaims.put("role", user.getRole().name());

      return generateToken(extraClaims, userDetails, user.getId());
   }

   private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, Long id) {
      return buildToken(extraClaims, userDetails, id, tokenExpiration);
   }

   public String generateRefreshToken(UserDetails userDetails) {
      User user = (User) userDetails;
      return buildToken(new HashMap<>(), userDetails, user.getId(), refreshTokenExpiration);
   }

   private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, Long id, long expiration) {
      return Jwts.builder()
              .setClaims(extraClaims)
              .setSubject(userDetails.getUsername())
              .setId(id.toString())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + expiration))
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
