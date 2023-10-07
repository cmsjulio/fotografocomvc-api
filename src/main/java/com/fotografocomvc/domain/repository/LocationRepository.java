package com.fotografocomvc.domain.repository;

import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
