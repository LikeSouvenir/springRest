package com.example.demo.core.users.user.service;

import com.example.demo.controllers.users.auth.dto.SignInDTO;
import com.example.demo.controllers.users.auth.dto.SignUpDTO;
import com.example.demo.core.users.profile.entity.ProfileEntity;
import com.example.demo.core.users.profile.repository.ProfileRepository;
import com.example.demo.core.users.user.entity.UserEntity;
import com.example.demo.core.users.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository _userRepository;
    private final ProfileRepository _profileRepository;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this._userRepository = userRepository;
        this._profileRepository = profileRepository;
    }

    public HashMap<String, Object> SignIn(SignInDTO data) {
        Optional<UserEntity> entity = this._userRepository.findByLogin(data.getLogin());

        if (entity.isEmpty()) {
            return null;
        }

        if (!BCrypt.checkpw(data.getPassword(), entity.get().getPassword())) {
            return null;
        }

        return _excludeSystemFields(entity.get());
    }

    public HashMap<String, Object> SignUp(SignUpDTO data) {
        ProfileEntity profile = this._profileRepository.save(
                new ProfileEntity(data.getName(), data.getAge())
        );

        UserEntity user = this._userRepository.save(
                new UserEntity(data.getLogin(), BCrypt.hashpw(data.getPassword(), BCrypt.gensalt(16)), profile)
        );
        return _excludeSystemFields(user);
    }

    // exclude other field //// exclude other field //// exclude other field //// exclude other field //
    private HashMap<String, Object> _excludeSystemFields(UserEntity user) {
        HashMap<String, Object> value = new HashMap<>();
        value.put("login", user.getLogin());
        value.put("profile_id", user.getId());
        value.put("name", user.getProfile().getName());
        value.put("age", user.getProfile().getAge());

        return value;
    }
}