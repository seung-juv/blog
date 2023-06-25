package com.seungju.blog.auth.controller;

import com.seungju.blog.auth.dto.AuthDto;
import com.seungju.blog.auth.entity.CustomUserDetails;
import com.seungju.blog.jwt.entity.RefreshToken;
import com.seungju.blog.jwt.service.JwtService;
import com.seungju.blog.user.dto.UserDto;
import com.seungju.blog.user.dto.UserDto.Response;
import com.seungju.blog.user.dto.UserDtoMapper;
import com.seungju.blog.user.entity.User;
import com.seungju.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Auth API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@RestController
public class AuthController {

  private final JwtService jwtService;
  private final UserService userService;

  @Operation(summary = "Get Me", description = "Get Me API")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = UserDto.Response.class)))})
  @GetMapping("/me")
  public final ResponseEntity<UserDto.Response> me(
      @AuthenticationPrincipal final CustomUserDetails user) {
    Response response = UserDtoMapper.INSTANCE.userToResponse(user.getUser());
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Sign In", description = "Sign In API")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(schema = @Schema(implementation = UserDto.Response.class)))})
  @PostMapping("/sign-in")
  public final ResponseEntity<AuthDto.SignInResponse> signIn(
      @RequestBody final UserDto.GetUserWithUsernameAndPassword request) {
    User user = this.userService.getUserWithUsernameAndPassword(request);
    RefreshToken refreshToken = this.jwtService.generateRefreshToken(user.getId());
    String accessToken = this.jwtService.generateAccessToken(refreshToken.getRefreshToken());

    AuthDto.SignInResponse response = new AuthDto.SignInResponse(accessToken,
        refreshToken.getRefreshToken(), UserDtoMapper.INSTANCE.userToResponse(user));

    return ResponseEntity.status(201).body(response);
  }

  @Operation(summary = "Sign Up", description = "Sign Up API")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(schema = @Schema(implementation = UserDto.Response.class)))})
  @PostMapping("/sign-up")
  public final ResponseEntity<UserDto.Response> signUp(@RequestBody final UserDto.Create request) {
    User user = this.userService.createUser(request);
    Response response = UserDtoMapper.INSTANCE.userToResponse(user);
    return ResponseEntity.status(201).body(response);
  }

}
