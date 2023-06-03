package com.seungju.blog.user.controller;


import com.seungju.blog.user.dto.UserDto;
import com.seungju.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "User API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

  private final UserService userService;

  @Operation(summary = "Create user", description = "Create user API")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(schema = @Schema(implementation = UserDto.Response.class)))})
  @PostMapping
  public final ResponseEntity<UserDto.Response> createUser(
      @RequestBody final UserDto.Request request) {
    UserDto.Response response = userService.createUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

}
