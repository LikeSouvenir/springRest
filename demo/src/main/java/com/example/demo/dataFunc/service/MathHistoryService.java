package com.example.demo.dataFunc.service;

import com.example.demo.dataFunc.entitys.math.MathHistory;
import com.example.demo.dataFunc.jpa.repository.MathHistoryRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class MathHistoryService {
    private final MathHistoryRepository mathHistoryRepository;

    public MathHistoryService(MathHistoryRepository mathHistoryRepository) {
        this.mathHistoryRepository = mathHistoryRepository;
    }

    @Transactional
    public void save(String operator, float a, float b, float result) {
        mathHistoryRepository.save(new MathHistory(operator, a, b, result));
    }

    @Transactional
    public void save(MathHistory mathHistory) {
        mathHistoryRepository.save(mathHistory);
    }

    public List<MathHistory> findAll() {
        return mathHistoryRepository.findAll();
    }

    public Optional<MathHistory> findById(Long id) {
        return mathHistoryRepository.findById(id);
    }

    public List<MathHistory> findByOperator(String operator) {
        return mathHistoryRepository.findByOperator(operator);
    }

    public void deleteById(Long id) {
        mathHistoryRepository.deleteById(id);
    }
    public List<MathHistory> deleteByOperator(String operator) {
        return mathHistoryRepository.deleteByOperator(operator);
    }
}
