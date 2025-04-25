package com.example.demo.controllers.auth.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDTO {
    private String name;
    private Integer age;
    private String login;
    private String password;

    public boolean Validate() {
        if (this.login == null || this.login.isEmpty()) {
            return false;
        }

        if (this.password == null || this.password.isEmpty()) {
            return false;
        }

        if (this.name == null || this.name.isEmpty()) {
            return false;
        }

        if (this.age == null || this.age == 0) {
            return false;
        }

        return true;
    }
}
