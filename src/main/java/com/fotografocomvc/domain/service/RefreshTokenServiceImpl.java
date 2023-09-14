package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.RefreshToken;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import com.fotografocomvc.domain.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements TokenService<RefreshToken>{

  private final RefreshTokenRepository refreshTokenRepository;

  private final BaseUserRepository userRepository;

  public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, BaseUserRepository userRepository) {
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

  @Transactional
  public void deleteByUserId(Long userId) {
    refreshTokenRepository.deleteByBaseUser(userRepository.findById(userId).get());
  }

  public void delete(RefreshToken token){
    refreshTokenRepository.delete(token);
  }

  @Override
  public List<RefreshToken> findAllByBaseUserId(Long baseUserId) {
    return refreshTokenRepository.findAllByBaseUserId(baseUserId);
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
