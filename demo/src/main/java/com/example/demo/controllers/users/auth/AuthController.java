package com.example.demo.controllers.users.auth;

import com.example.demo.controllers.users.auth.dto.SignInDTO;
import com.example.demo.controllers.users.auth.dto.SignUpDTO;
import com.example.demo.controllers.users.auth.dto.UserProfileDTO;
import com.example.demo.core.users.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService _userService;

    @PostMapping("/signIn")
    public ResponseEntity<UserProfileDTO> signIn(@RequestBody SignInDTO body) {
        this.logger.info("signIn");

        if (!body.Validate()) {
            return ResponseEntity.badRequest().build();
        }

        UserProfileDTO response = this._userService.SignIn(body);

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
