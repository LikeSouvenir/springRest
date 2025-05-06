package com.example.demo.core.contractor.repository;

import com.example.demo.core.contractor.entity.ContractorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContractorRepository extends JpaRepository<ContractorEntity, UUID> {
}
