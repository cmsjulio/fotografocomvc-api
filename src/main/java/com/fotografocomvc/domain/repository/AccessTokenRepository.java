package com.fotografocomvc.domain.repository;

import com.fotografocomvc.domain.model.AccessToken;
import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
  Optional<AccessToken> findByTokenString(String token);

  @Modifying
  int deleteByBaseUser(BaseUser user);

  boolean existsByBaseUser(BaseUser baseUser);

  Optional<AccessToken> findByBaseUserId(Long baseUserId);

  // TODO implementar no serviço para limpeza dos tokens? fazer o mesmo o refreshToken
  void deleteAllByBaseUserId(Long baseUserId);

}
