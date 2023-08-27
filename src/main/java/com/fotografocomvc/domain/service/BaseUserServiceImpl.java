package com.fotografocomvc.domain.service;

import com.fotografocomvc.domain.exception.NotFoundException;
import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.repository.BaseUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaseUserServiceImpl implements BaseUserService{

    private final BaseUserRepository baseUserRepository;

    @Override
    public BaseUser save(BaseUser baseUser) {
        return baseUserRepository.save(baseUser);
    }

    @Override
    public BaseUser findById(Long id) {
        return baseUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BaseUser not found"));
    }

    @Override
    public BaseUser update(BaseUser baseUser) {
        baseUserRepository.findById(baseUser.getId())
                .orElseThrow(() -> new NotFoundException("BaseUser not found"));
        baseUserRepository.save(baseUser);
        return baseUser;
    }
}
