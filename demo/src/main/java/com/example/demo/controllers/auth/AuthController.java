package com.example.demo.controllers.auth;

import com.example.demo.controllers.auth.dto.SignInDTO;
import com.example.demo.controllers.auth.dto.SignUpDTO;
import com.example.demo.core.user.entity.UserEntity;
import com.example.demo.core.user.service.UserService;
import com.example.demo.utils.exceptions.ApplicationException;
import com.example.demo.utils.exceptions.DataNotFoundException;
import com.example.demo.utils.exceptions.ValidationException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/v1")
@Tag(
        name = "SignUp/signIn controller",
        description = "API for register new users and connect old users"
)
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService _userService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful connect"),
            @ApiResponse(responseCode = "204", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid login or password")
    })
    @PostMapping("/signIn")
    public ResponseEntity<UserEntity> signIn(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO to sign in user",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SignInDTO.class)
                    )
            )
            @RequestBody SignInDTO body
    ) {
        this.logger.info("signIn");

        if (!body.Validate()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Invalid login or password");
        }

        UserEntity response = this._userService.SignIn(body);

        if (response == null) {
            throw new DataNotFoundException(HttpStatus.NO_CONTENT, "User not found");
        }

        return ResponseEntity.ok(response);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful register"),
            @ApiResponse(responseCode = "400", description = "Invalid login or password")
    })
    @PostMapping("/signUp")
    public ResponseEntity<UserEntity> signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO to sign up user",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SignUpDTO.class)
                    )
            )
            @RequestBody SignUpDTO body) {
        this.logger.info("signUp");

        if (!body.Validate()) {
            throw new ValidationException(HttpStatus.BAD_REQUEST, "Invalid login or password");
        }

        return ResponseEntity.ok(this._userService.SignUp(body));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Test method, error"),
            @ApiResponse(responseCode = "200", description = "Test method, all nice")
    })
    @GetMapping("/error")
    public ResponseEntity<String> error() {
        this.logger.info("error");

        if (new Random().nextBoolean()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Application Exception: Something went wrong");
        }

        return ResponseEntity.ok("All good!");
    }

}