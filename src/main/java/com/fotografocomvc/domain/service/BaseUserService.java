package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.model.BaseUser;

public interface BaseUserService {

    BaseUser save(BaseUser baseUser);
    BaseUser findById(Long id);
    BaseUser update(BaseUser baseUser);
    boolean existsByUsername(String username);

}
