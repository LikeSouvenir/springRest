package com.example.demo.dataFunc.jpa.repository;

import com.example.demo.dataFunc.entitys.math.MathHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MathHistoryRepository extends JpaRepository<MathHistory, Long> {
//    Optional<MathHistory> findById(Long Id);
    List<MathHistory> findByOperator(String operator);

    List<MathHistory> deleteByOperator(String operator);
}

