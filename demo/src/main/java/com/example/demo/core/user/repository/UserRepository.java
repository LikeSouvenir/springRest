package com.example.demo.core.user.repository;

import com.example.demo.core.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

//    @Query(value = """
//        SELECT ue.id AS "id", ue.login AS "login", ue.password AS "password", pe.id AS "profileId", pe.name AS "name", pe.age AS "age" FROM user_entity ue
//        LEFT JOIN profile_entity pe ON ue.profile_id = pe.id
//        WHERE ue.login = ?1;
//    """, nativeQuery = true)
//    FullUserProjection findByLogin(String login);

    @Query("select ue from UserEntity ue where ue.login = ?1")
    Optional<UserEntity> findByLogin(String login);
}
