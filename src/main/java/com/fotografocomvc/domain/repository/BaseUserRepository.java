package com.fotografocomvc.domain.repository;

import com.fotografocomvc.domain.model.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {

    Optional<BaseUser> findByUsername(String email);
}
