package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.exception.TokenRefreshException;
import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.RefreshToken;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import com.fotografocomvc.domain.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.fotografocomvc.api.security.SecurityConstants.REFRESH_TOKEN_EXPIRATION;

@Service
public class RefreshTokenService implements TokenService<RefreshToken>{

  private final RefreshTokenRepository refreshTokenRepository;

  private final BaseUserRepository userRepository;

  public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, BaseUserRepository userRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
  }

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByTokenString(token);
  }

  public Optional<RefreshToken> findByBaseUserId(Long baseUserId){
    return refreshTokenRepository.findByBaseUserId(baseUserId);
  }

  public RefreshToken createToken(RefreshToken refreshToken) {
    return refreshTokenRepository.save(refreshToken);
  }

  @Override
  public RefreshToken verifyExpiration(RefreshToken tokenEntity) {
    return tokenEntity;
  }

  @Override
  public boolean verifyExpirationBoolean(RefreshToken token) {
    return false;
  }

//  public RefreshToken verifyExpiration(RefreshToken token) {
//    if (token.getExpiryDate().compareTo(Date.from(Instant.now())) < 0) { //verificar, pois agora usando DATE!=INSTANT
//      refreshTokenRepository.delete(token);
//      throw new TokenRefreshException(token.getTokenString(), "Refresh token was expired. Please make a new signin request");
//    }
//    return token;
//  }
//
//  public boolean verifyExpirationBoolean(RefreshToken token){ //verificar, pois agora usando DATE!=INSTANT
//    if (token.getExpiryDate().compareTo(Date.from(Instant.now())) < 0){
//      return true;
//    }
//    else {
//      return false;
//    }
//  }

  @Transactional
  public void deleteByUserId(Long userId) {
    refreshTokenRepository.deleteByBaseUser(userRepository.findById(userId).get());
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

  public boolean checkIfUserHasToken(BaseUser baseUser){
    return refreshTokenRepository.existsByBaseUser(baseUser);
  }
}
