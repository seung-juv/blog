package com.seungju.blog.jwt.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

public class RefreshTokenDto {

  @Value
  @Getter
  @Setter
  public static class Response implements Serializable {

    String refreshToken;

  }

}
