package com.seungju.blog.auth.controller;


import com.seungju.blog.jwt.dto.AccessTokenDto;
import com.seungju.blog.jwt.dto.RefreshTokenDto;
import com.seungju.blog.jwt.service.JwtService;
import com.seungju.blog.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Auth API")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

  private final JwtService jwtService;

  @Operation(summary = "Get Me", description = "Get Me API")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = User.class)))})
  @GetMapping("/me")
  public final ResponseEntity<User> getMe(@AuthenticationPrincipal final User user) {
    return ResponseEntity.ok(user);
  }

  @Operation(summary = "Generate Refresh Token", description = "Generate Refresh Token API")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(schema = @Schema(implementation = RefreshTokenDto.Response.class)))})
  @PostMapping("/refresh-token")
  public final ResponseEntity<RefreshTokenDto.Response> generateRefreshToken(
      @RequestBody final UserDto.GetUserWithUsernameAndPasswordRequest request) {
    RefreshTokenDto.Response response = jwtService.generateRefreshToken(request);
    return ResponseEntity.status(201).body(response);
  }

  @Operation(summary = "Generate Access Token", description = "Generate Access Token API")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(schema = @Schema(implementation = AccessTokenDto.Request.class)))})
  @PostMapping("/access-token")
  public final ResponseEntity<AccessTokenDto.Response> generateAccessToken(
      @RequestBody final AccessTokenDto.Request request) {
    AccessTokenDto.Response response = jwtService.generateAccessToken(request);
    return ResponseEntity.status(201).body(response);
  }

}
