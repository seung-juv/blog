package com.seungju.blog.jwt.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

public class AccessTokenDto {

  @Value
  @Getter
  @Setter
  public static class Response implements Serializable {

    String accessToken;

  }

  @NoArgsConstructor
  @Getter
  @Setter
  public static class Request implements Serializable {

    String refreshToken;

  }

}
