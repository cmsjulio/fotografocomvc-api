package com.fotografocomvc.domain.repository;

import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByTokenString(String token);

  @Modifying
  int deleteByBaseUser(BaseUser user);

  boolean existsByBaseUser(BaseUser baseUser);

  Optional<RefreshToken> findByBaseUserId(Long baseUserId);

}
