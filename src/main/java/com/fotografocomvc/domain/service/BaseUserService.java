package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.model.BaseUser;

import java.util.Optional;

public interface BaseUserService {

    BaseUser save(BaseUser baseUser);
    BaseUser findById(Long id);
    BaseUser update(BaseUser baseUser);
    boolean existsByUsername(String username);
    Optional<BaseUser> findByUsername(String email);

}
