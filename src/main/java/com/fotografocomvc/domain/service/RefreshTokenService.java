package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.exception.TokenRefreshException;
import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.RefreshToken;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import com.fotografocomvc.domain.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.fotografocomvc.api.security.SecurityConstants.REFRESH_TOKEN_EXPIRATION;

@Service
public class RefreshTokenService {

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private BaseUserRepository userRepository;

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByTokenString(token);
  }

  public Optional<RefreshToken> findByBaseUserId(Long baseUserId){
    return refreshTokenRepository.findByBaseUserId(baseUserId);
  }

  public RefreshToken createRefreshToken(Long userId) {
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setBaseUser(userRepository.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION));
    refreshToken.setTokenString(UUID.randomUUID().toString());

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getTokenString(), "Refresh token was expired. Please make a new signin request");
    }
    return token;
  }

  public boolean verifyExpirationBoolean(RefreshToken token){
    if (token.getExpiryDate().compareTo(Instant.now()) < 0){
      return true;
    }
    else {
      return false;
    }
  }

  @Transactional
  public int deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByBaseUser(userRepository.findById(userId).get());
  }

  public void delete(RefreshToken token){
    refreshTokenRepository.delete(token);
  }

  public boolean checkIfUserHasValidRefreshToken(Long userID){
    if (refreshTokenRepository.existsByBaseUser(userRepository.findById(userID).get())){
      if (!verifyExpirationBoolean(refreshTokenRepository.findByBaseUserId(userID).get())){
        return true;
      }
    }
    return false;
  }

  public boolean checkIfUserHasRefreshToken(BaseUser baseUser){
    return refreshTokenRepository.existsByBaseUser(baseUser);
  }
}
