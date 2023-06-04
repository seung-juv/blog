package com.seungju.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig {

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf((httpSecurityCsrfConfigurer) -> httpSecurityCsrfConfigurer.disable())
        .authorizeHttpRequests(
            (authorizationManagerRequestMatcherRegistry) -> authorizationManagerRequestMatcherRegistry.anyRequest()
                .permitAll()).sessionManagement(
            (httpSecuritySessionManagementConfigurer) -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
        .formLogin((httpSecurityFormLoginConfigurer) -> httpSecurityFormLoginConfigurer.disable()).httpBasic((httpSecurityHttpBasicConfigurer) -> httpSecurityHttpBasicConfigurer.disable())
        .build();
  }

}
