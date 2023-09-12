package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.exception.TokenRefreshException;
import com.fotografocomvc.domain.model.AccessToken;
import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.RefreshToken;
import com.fotografocomvc.domain.repository.AccessTokenRepository;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import com.fotografocomvc.domain.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
public class AccessTokenService implements TokenService<AccessToken>{

  private final AccessTokenRepository accessTokenRepository;

  private final BaseUserRepository userRepository;

  public AccessTokenService(AccessTokenRepository accessTokenRepository, BaseUserRepository userRepository) {
    this.accessTokenRepository = accessTokenRepository;
    this.userRepository = userRepository;
  }

  public Optional<AccessToken> findByToken(String token) {
    return accessTokenRepository.findByTokenString(token);
  }

  public Optional<AccessToken> findByBaseUserId(Long baseUserId){
    return accessTokenRepository.findByBaseUserId(baseUserId);
  }

  public AccessToken createToken(AccessToken accessToken) {
    return accessTokenRepository.save(accessToken);
//    RefreshToken refreshToken = new RefreshToken();
//
//    refreshToken.setBaseUser(userRepository.findById(userId).get());
//    refreshToken.setExpiryDate(Date.from(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION)));
//    refreshToken.setTokenString(UUID.randomUUID().toString());
//
//    refreshToken = refreshTokenRepository.save(refreshToken);

  }

  @Override
  public AccessToken verifyExpiration(AccessToken tokenEntity) {
    return null;
  }

  @Override
  public boolean verifyExpirationBoolean(AccessToken token) {
    return false;
  }

//  public AccessToken verifyExpiration(AccessToken token) {
//    if (token.getExpiryDate().compareTo(Date.from(Instant.now())) < 0) { //verificar, pois agora usando DATE!=INSTANT
//      accessTokenRepository.delete(token);
//      throw new TokenRefreshException(token.getTokenString(), "Refresh token was expired. Please make a new signin request");
//    }
//    return token;
//  }
//
//  public boolean verifyExpirationBoolean(AccessToken token){ //verificar, pois agora usando DATE!=INSTANT
//    if (token.getExpiryDate().compareTo(Date.from(Instant.now())) < 0){
//      return true;
//    }
//    else {
//      return false;
//    }
//  }

  @Transactional
  public void deleteByUserId(Long userId) {
    accessTokenRepository.deleteByBaseUser(userRepository.findById(userId).get());
  }

  public void delete(AccessToken token){
    accessTokenRepository.delete(token);
  }

  public boolean checkIfUserHasValidRefreshToken(Long userID){
    if (accessTokenRepository.existsByBaseUser(userRepository.findById(userID).get())){
      if (!verifyExpirationBoolean(accessTokenRepository.findByBaseUserId(userID).get())){
        return true;
      }
    }
    return false;
  }

  public boolean checkIfUserHasToken(BaseUser baseUser){
    return accessTokenRepository.existsByBaseUser(baseUser);
  }
}
