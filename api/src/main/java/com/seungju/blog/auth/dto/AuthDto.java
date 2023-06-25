package com.seungju.blog.auth.dto;

import com.seungju.blog.user.dto.UserDto;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

public class AuthDto {

  @Value
  @Getter
  @Setter
  public static class SignInResponse implements Serializable {

    String accessToken;

    String refreshToken;

    UserDto.Response user;

  }

}
