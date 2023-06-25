package com.seungju.blog.user.repository;

import com.seungju.blog.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(final String email);

  Optional<User> findByUsername(final String username);

  Optional<User> findByUsernameAndPassword(final String username, final String password);

}
