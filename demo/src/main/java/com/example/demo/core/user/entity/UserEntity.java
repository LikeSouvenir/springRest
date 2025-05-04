package com.example.demo.core.user.entity;

import com.example.demo.core.profile.entity.ProfileEntity;
import com.example.demo.utils.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_entity")
public class UserEntity extends BaseEntity {

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private ProfileEntity profile;

//    // mapping
//    public UserProfileDTO toDTO() {
//        return UserProfileDTO.builder()
//                .login(this.getLogin())
//                .name(this.getProfile().getName())
//                .age(this.getProfile().getAge())
//                .profileId(this.getProfile().getId())
//                .build();
//    }
}
