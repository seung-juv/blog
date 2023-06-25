package com.seungju.blog.auth.service;

import com.seungju.blog.auth.entity.CustomUserDetails;
import com.seungju.blog.user.entity.User;
import com.seungju.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Not found user"));

    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    return customUserDetails;
  }
}
