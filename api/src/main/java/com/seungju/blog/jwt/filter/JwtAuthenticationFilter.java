package com.seungju.blog.jwt.filter;

import com.seungju.blog.auth.entity.CustomUserDetails;
import com.seungju.blog.exception.InvalidAccessTokenException;
import com.seungju.blog.jwt.constant.JwtConstant;
import com.seungju.blog.jwt.service.JwtService;
import com.seungju.blog.user.entity.User;
import com.seungju.blog.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader(JwtConstant.AUTH_HEADER);

    if (authHeader == null || !authHeader.toLowerCase()
        .startsWith(JwtConstant.TOKEN_TYPE.toLowerCase()) || authHeader.length() < 7) {
      filterChain.doFilter(request, response);
      return;
    }

    final String accessToken = authHeader.substring(7);
    final UUID id = this.jwtService.extractId(accessToken);

    if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      User user = this.userRepository.findById(id).orElseThrow(InvalidAccessTokenException::new);
      CustomUserDetails userDetails = new CustomUserDetails(user);
      Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
          userDetails.getPassword(), userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

}
