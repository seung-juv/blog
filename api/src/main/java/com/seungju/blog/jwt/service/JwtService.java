package com.seungju.blog.jwt.service;

import com.seungju.blog.exception.InvalidAccessTokenException;
import com.seungju.blog.exception.NotFoundException;
import com.seungju.blog.jwt.dto.AccessTokenDto;
import com.seungju.blog.jwt.dto.AccessTokenDtoMapper;
import com.seungju.blog.jwt.dto.RefreshTokenDto;
import com.seungju.blog.jwt.dto.RefreshTokenDtoMapper;
import com.seungju.blog.jwt.entity.RefreshToken;
import com.seungju.blog.jwt.repository.RefreshTokenRedisRepository;
import com.seungju.blog.user.dto.UserDto;
import com.seungju.blog.user.service.UserService;
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

  private final UserService userService;

  public RefreshTokenDto.Response generateRefreshToken(
      final UserDto.GetUserWithUsernameAndPasswordRequest request) {
    UserDto.Response user = userService.getUserWithUsernameAndPassword(request);

    RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), user.getId());
    refreshTokenRedisRepository.save(refreshToken);

    RefreshTokenDto.Response response = RefreshTokenDtoMapper.INSTANCE.refreshTokenToResponse(
        refreshToken);

    return response;
  }

  public AccessTokenDto.Response generateAccessToken(final AccessTokenDto.Request request) {
    RefreshToken refreshToken = refreshTokenRedisRepository.findById(request.getRefreshToken())
        .orElseThrow(NotFoundException::new);

    UUID userId = refreshToken.getUserId();

    Date now = new Date();
    Date expiration = new Date(now.getTime() + ACCESS_TOKEN_EXPIRES);

    String accessToken = Jwts.builder().signWith(secretKey).setIssuedAt(now)
        .setExpiration(expiration).setSubject(userId.toString()).compact();

    AccessTokenDto.Response response = AccessTokenDtoMapper.INSTANCE.accessTokenToResponse(
        accessToken);

    return response;
  }

  public UUID extractUserId(final String accessToken) {
    try {
      String subject = Jwts.parserBuilder().setSigningKey(secretKey).build()
          .parseClaimsJws(accessToken).getBody().getSubject();

      UUID userId = UUID.fromString(subject);

      return userId;
    } catch (final JwtException e) {
      throw new InvalidAccessTokenException();
    }
  }

}
