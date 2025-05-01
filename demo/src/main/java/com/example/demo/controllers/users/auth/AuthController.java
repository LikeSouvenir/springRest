package com.example.demo.controllers.users.auth;

import com.example.demo.controllers.users.auth.dto.SignInDTO;
import com.example.demo.controllers.users.auth.dto.SignUpDTO;
import com.example.demo.controllers.users.auth.dto.UserProfileDTO;
import com.example.demo.core.users.user.entity.UserEntity;
import com.example.demo.core.users.user.projections.FullUserProjection;
import com.example.demo.core.users.user.service.UserService;
import com.example.demo.utils.exceptions.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Random;

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
    public ResponseEntity<UserProfileDTO> signUp(@RequestBody SignUpDTO body) {
        this.logger.info("signUp");

        if (!body.Validate()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(this._userService.SignUp(body));
    }

    @GetMapping("/error")
    public ResponseEntity<String> error() {
        this.logger.info("error");

        if (new Random().nextBoolean()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Application Exception: Something went wrong");
        }

        return ResponseEntity.ok("All good!");
    }

}

/*Пользователь
-имя
-возраст

Профиль
-логин
-пароль
-адрес доставки

Корзина
-id user
-id товара ''наличие(проверка при выводе цены)''

Товары в магазине
-id магазин
-id товар
-кол-во
-цена



Заказ
-способ оплаты
-общая стоимость
-дата заказа
-сатус
*/
