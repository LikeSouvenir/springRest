package com.example.demo.core.users.user.repository;

import com.example.demo.controllers.users.auth.dto.UserProfileDTO;
import com.example.demo.core.users.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    //    private UUID ProfileId;
    //    private String name;
    //    private Integer age;
    //    private String login;
    //    private String password;
    @Query(value = "select new com.example.demo.controllers.users.auth.dto.UserProfileDTO" +
            "(ue.profile.id, ue.profile.name, ue.profile.age, ue.login, ue.password) from UserEntity ue where ue.login = ?1")
    UserProfileDTO findByLogin(String login);
}
