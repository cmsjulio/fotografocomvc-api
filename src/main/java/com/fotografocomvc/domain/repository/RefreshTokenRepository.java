package com.fotografocomvc.domain.repository;

import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByTokenString(String token);

  @Modifying
  void deleteByBaseUser(BaseUser user);

  boolean existsByBaseUser(BaseUser baseUser);

  Optional<RefreshToken> findByBaseUserId(Long baseUserId);

  // TODO implementar no serviço para limpeza dos tokens? fazer o mesmo o accessToken
  List<RefreshToken> findAllByBaseUserId(Long baseUserId);

}
