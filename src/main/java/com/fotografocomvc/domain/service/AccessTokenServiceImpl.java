package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.model.AccessToken;
import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.RefreshToken;
import com.fotografocomvc.domain.repository.AccessTokenRepository;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccessTokenServiceImpl implements TokenService<AccessToken>{

  private final AccessTokenRepository accessTokenRepository;

  private final BaseUserRepository userRepository;

  public AccessTokenServiceImpl(AccessTokenRepository accessTokenRepository, BaseUserRepository userRepository) {
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
  }

  @Override
  public AccessToken verifyExpiration(AccessToken tokenEntity) {
    return null;
  }

  @Override
  public boolean verifyExpirationBoolean(AccessToken token) {
    return false;
  }

  @Transactional
  public void deleteByUserId(Long userId) {
    accessTokenRepository.deleteByBaseUser(userRepository.findById(userId).get());
  }

  public void delete(AccessToken token){
    accessTokenRepository.delete(token);
  }

  @Override
  public List<AccessToken> findAllByBaseUserId(Long baseUserId) {
    return accessTokenRepository.findAllByBaseUserId(baseUserId);
  }

  @Override
  public void deleteAllByUserId(Long baseUserId) {
    List<AccessToken> accessTokenList = accessTokenRepository.findAllByBaseUserId(baseUserId);
    accessTokenRepository.deleteAll(accessTokenList);
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
