package com.seungju.blog.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "provider", nullable = false)
  private String provider;

  @Column(name = "provider_account_id", nullable = false)
  private String providerAccountId;

  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name = "access_token")
  private String accessToken;

  @Column(name = "expires_at")
  private Long expiresAt;

  @Column(name = "token_type")
  private String tokenType;

  @Column(name = "scope")
  private String scope;

  @Column(name = "id_token")
  private String idToken;

  @Column(name = "session_state")
  private String sessionState;

  @Column(name = "oauth_token_secret")
  private String oauthTokenSecret;

  @Column(name = "oauth_token")
  private String oauthToken;

}
