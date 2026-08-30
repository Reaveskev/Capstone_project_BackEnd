package com.capstone.backend.repository;

import com.capstone.backend.entity.SaleItem;
import com.capstone.backend.entity.SaleItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem, SaleItemId> {
    List<SaleItem> findBySaleId(Integer saleId);
}
