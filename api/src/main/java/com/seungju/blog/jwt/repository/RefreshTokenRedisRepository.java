package com.seungju.blog.jwt.repository;

import com.seungju.blog.jwt.entity.RefreshToken;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRedisRepository {

  @Value("${jwt.refresh-token-expires}")
  private int refreshTokenExpires;

  private final RedisTemplate redisTemplate;

  public void save(final RefreshToken refreshToken) {
    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(refreshToken.getRefreshToken(), refreshToken.getUserId().toString());
    redisTemplate.expire(refreshToken.getRefreshToken(), refreshTokenExpires, TimeUnit.MILLISECONDS);
  }

  public Optional<RefreshToken> findById(final String refreshToken) {
    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    String userId = valueOperations.get(refreshToken);

    if (userId == null) {
      return Optional.empty();
    }

    return Optional.of(new RefreshToken(refreshToken, UUID.fromString(userId)));
  }

}
