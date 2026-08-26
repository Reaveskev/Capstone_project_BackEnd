package com.capstone.backend.repository;

import com.capstone.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);
}

/** 
 * JpaRepository already gives you findAll(), findById(), save(), deleteById() 
 * for free — no extra code needed for basic CRUD
**/