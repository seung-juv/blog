package com.seungju.blog.jwt.service;

import com.seungju.blog.exception.InvalidAccessTokenException;
import com.seungju.blog.exception.NotFoundException;
import com.seungju.blog.jwt.entity.RefreshToken;
import com.seungju.blog.jwt.repository.RefreshTokenRedisRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {

  private final SecretKey secretKey;

  @Value("${jwt.access-token-expires}")
  private int ACCESS_TOKEN_EXPIRES;

  private final RefreshTokenRedisRepository refreshTokenRedisRepository;

  public RefreshToken generateRefreshToken(final UUID id) {
    RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), id);
    refreshTokenRedisRepository.save(refreshToken);

    return refreshToken;
  }

  public String generateAccessToken(final String id) {
    RefreshToken refreshToken = refreshTokenRedisRepository.findById(id)
        .orElseThrow(NotFoundException::new);

    UUID userId = refreshToken.getUserId();

    Date now = new Date();
    Date expiration = new Date(now.getTime() + ACCESS_TOKEN_EXPIRES);

    String accessToken = Jwts.builder().signWith(secretKey).setIssuedAt(now)
        .setExpiration(expiration).setSubject(userId.toString()).compact();

    return accessToken;
  }

  public UUID extractId(final String accessToken) {
    try {
      String subject = Jwts.parserBuilder().setSigningKey(secretKey).build()
          .parseClaimsJws(accessToken).getBody().getSubject();

      UUID id = UUID.fromString(subject);

      return id;
    } catch (final JwtException e) {
      throw new InvalidAccessTokenException();
    }
  }

}
