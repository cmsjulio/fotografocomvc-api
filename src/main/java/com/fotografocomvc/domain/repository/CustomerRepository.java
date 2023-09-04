package com.fotografocomvc.domain.repository;

import com.fotografocomvc.domain.model.Customer;
import com.fotografocomvc.domain.model.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
