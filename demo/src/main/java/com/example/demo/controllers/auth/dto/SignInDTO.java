package com.example.demo.controllers.auth.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInDTO {
    private String login;
    private String password;

    public boolean Validate() {
        if (this.login == null || this.login.isEmpty()) {
            return false;
        }

        if (this.password == null || this.password.isEmpty()) {
            return false;
        }

        return true;
    }
}
