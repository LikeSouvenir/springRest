package com.example.demo.controllers.auth.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FullUserDTO {
    private UUID userId;
    private UUID profileId;
    private String name;
    private Integer age;
    private String login;
    private String password;
    private LocalDateTime userCreatedAt;
    private LocalDateTime userUpdatedAt;
    private LocalDateTime userDeletedAt;
    private LocalDateTime profileCreatedAt;
    private LocalDateTime profileUpdatedAt;
    private LocalDateTime profileDeletedAt;
}
