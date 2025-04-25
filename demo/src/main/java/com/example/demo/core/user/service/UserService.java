package com.example.demo.core.user.service;

import com.example.demo.controllers.auth.dto.SignInDTO;
import com.example.demo.controllers.auth.dto.SignUpDTO;
import com.example.demo.core.profile.entity.ProfileEntity;
import com.example.demo.core.profile.repository.ProfileRepository;
import com.example.demo.core.user.entity.UserEntity;
import com.example.demo.core.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository _userRepository;
    private final ProfileRepository _profileRepository;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this._userRepository = userRepository;
        this._profileRepository = profileRepository;
    }

    public UserEntity SignIn(SignInDTO data) {
        Optional<UserEntity> entity = this._userRepository.findByLogin(data.getLogin());

        if (entity.isEmpty()) {
            return null;
        }

        if (!BCrypt.checkpw(data.getPassword(), entity.get().getPassword())) {
            return null;
        }

        return entity.get();
    }

    public UserEntity SignUp(SignUpDTO data) {
        ProfileEntity profile = this._profileRepository.save(
                new ProfileEntity(data.getName(), data.getAge())
        );

        return this._userRepository.save(
                new UserEntity(data.getLogin(), BCrypt.hashpw(data.getPassword(), BCrypt.gensalt(16)), profile)
        );
    }
}