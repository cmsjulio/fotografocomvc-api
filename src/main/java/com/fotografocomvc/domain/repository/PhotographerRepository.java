package com.fotografocomvc.domain.repository;

import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {

    List<Photographer> findAllByLocationId (Long locationId);
    Photographer findByBaseUser(BaseUser baseUser);
}
