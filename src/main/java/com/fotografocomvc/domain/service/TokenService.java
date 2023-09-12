package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Customer;
import com.fotografocomvc.domain.model.RefreshToken;

import java.util.Optional;

public interface TokenService<E> {

    Optional<E> findByToken(String tokenString);
    Optional<E> findByBaseUserId(Long userId);
    E createToken(E tokenEntity);
    E verifyExpiration(E tokenEntity);
    public boolean verifyExpirationBoolean(E token);
    void deleteByUserId(Long userId);
    boolean checkIfUserHasToken(BaseUser baseUser);
    void delete(E tokenEntity);

}
