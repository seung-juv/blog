package com.seungju.blog.jwt.entity;

import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;


@Getter
@RedisHash(value = "refresh-token")
public class RefreshToken {

  @Id
  private String refreshToken;

  private UUID userId;

  public RefreshToken(final String refreshToken, final UUID userId) {
    this.refreshToken = refreshToken;
    this.userId = userId;
  }

}
