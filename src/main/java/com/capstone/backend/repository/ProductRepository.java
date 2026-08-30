package com.capstone.backend.repository;

import com.capstone.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsBySku(String sku);
}