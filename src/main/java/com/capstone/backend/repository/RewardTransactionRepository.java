package com.capstone.backend.repository;

import com.capstone.backend.entity.RewardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardTransactionRepository extends JpaRepository<RewardTransaction, Integer> {
}