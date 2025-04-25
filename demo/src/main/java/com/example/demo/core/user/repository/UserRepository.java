package com.example.demo.core.user.repository;

import com.example.demo.core.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query(name = "select ue from UserEntity ue where login = ?1")
    Optional<UserEntity> findByLogin(String login);
}
