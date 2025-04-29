package com.example.demo.core.users.profile.repository;

import com.example.demo.core.users.profile.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {

//    @Query(value = "select pe from ProfileEntity pe where pe.id = ?1")
//    Optional<ProfileEntity> findById(UUID id);
}
