package com.example.demo.controllers.users.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserProfileDTO {
    private UUID ProfileId;
    private String name;
    private Integer age;
    private String login;
    private String password;
}
