package com.example.demo.core.user.service;

import com.example.demo.controllers.auth.dto.SignInDTO;
import com.example.demo.controllers.auth.dto.SignUpDTO;
import com.example.demo.core.cart.entity.CartEntity;
import com.example.demo.core.cart.repository.CartRepository;
import com.example.demo.core.profile.entity.ProfileEntity;
import com.example.demo.core.profile.repository.ProfileRepository;
import com.example.demo.core.user.entity.UserEntity;
import com.example.demo.core.user.repository.UserRepository;
import com.example.demo.utils.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository _userRepository;
    private final ProfileRepository _profileRepository;
    private final CartRepository _cartRepository;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository, CartRepository cartRepository) {
        this._userRepository = userRepository;
        this._profileRepository = profileRepository;
        this._cartRepository = cartRepository;
    }

    public UserEntity SignIn(SignInDTO data) {
//        FullUserProjection entity = this._userRepository.findByLogin(data.getLogin());
        Optional<UserEntity> entity = this._userRepository.findByLogin(data.getLogin());

        if (entity.isEmpty()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Incorrect login");
        }

        if (!BCrypt.checkpw(data.getPassword(), entity.get().getPassword())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Incorrect password");
        }

        return entity.get();
    }

    public UserEntity SignUp(SignUpDTO data) {
        ProfileEntity profile = this._profileRepository.save(
                new ProfileEntity(data.getName(), data.getAge())
        );

        UserEntity user =  this._userRepository.save(
                new UserEntity(data.getLogin(), BCrypt.hashpw(data.getPassword(), BCrypt.gensalt(16)), profile)
        );

        // создание корзины пользователя
        this._cartRepository.save(new CartEntity(user.getId(), user, new ArrayList<>(), 0));

        return user;
    }

    public Optional<ProfileEntity> findProfileById(UUID userId) {
        return this._profileRepository.findById(userId);
    }
    public Optional<UserEntity> findUserById(UUID userId) {
        return this._userRepository.findById(userId);
    }
}