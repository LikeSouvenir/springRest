package com.example.demo.controllers.auth;

import com.example.demo.controllers.auth.dto.SignInDTO;
import com.example.demo.controllers.auth.dto.SignUpDTO;
import com.example.demo.core.user.entity.UserEntity;
import com.example.demo.core.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService _userService;

    @PostMapping("/signIn")
    public ResponseEntity<UserEntity> signIn(@RequestBody SignInDTO body) {
        this.logger.info("signIn");

        if (!body.Validate()) {
            return ResponseEntity.badRequest().build();
        }

        UserEntity response = this._userService.SignIn(body);

        if (response == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserEntity> signUp(@RequestBody SignUpDTO body) {
        this.logger.info("signUp");

        if (!body.Validate()) {
            return ResponseEntity.badRequest().build();
        }

        UserEntity response = this._userService.SignUp(body);

        return ResponseEntity.ok(response);
    }

}
