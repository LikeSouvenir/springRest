package com.example.demo.controllers.users.auth.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDTO {
    private UUID profileId;
    private String name;
    private Integer age;
    private String login;
}
