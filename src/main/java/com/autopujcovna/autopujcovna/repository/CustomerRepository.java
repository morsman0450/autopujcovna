package com.autopujcovna.autopujcovna.repository;

import com.autopujcovna.autopujcovna.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
