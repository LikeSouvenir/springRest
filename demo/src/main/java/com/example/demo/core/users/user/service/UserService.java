package com.example.demo.core.users.user.service;

import com.example.demo.controllers.users.auth.dto.SignInDTO;
import com.example.demo.controllers.users.auth.dto.SignUpDTO;
import com.example.demo.controllers.users.auth.dto.UserProfileDTO;
import com.example.demo.core.users.profile.entity.ProfileEntity;
import com.example.demo.core.users.profile.repository.ProfileRepository;
import com.example.demo.core.users.user.entity.UserEntity;
import com.example.demo.core.users.user.projections.FullUserProjection;
import com.example.demo.core.users.user.repository.UserRepository;
import com.example.demo.libs.MathModule;
import com.example.demo.utils.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository _userRepository;
    private final ProfileRepository _profileRepository;
    private final MathModule _mathModule;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository, MathModule mathModule) {
        this._userRepository = userRepository;
        this._profileRepository = profileRepository;

        this._mathModule = mathModule;
    }

//    public FullUserProjection SignIn(SignInDTO data) {
//        FullUserProjection entity = this._userRepository.findByLogin(data.getLogin());
//
//        if (entity == null) {
//            return null;
//        }
//
//        if (!BCrypt.checkpw(data.getPassword(), entity.getPassword())) {
//            return null;
//        }
//
//        return entity;
//    }

    public UserEntity SignIn(SignInDTO data) {
        Optional<UserEntity> entity = this._userRepository.findByLogin(data.getLogin());

        if (entity.isEmpty()) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Incorrect login");
        }

        if (!BCrypt.checkpw(data.getPassword(), entity.get().getPassword())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Incorrect password");
        }

        return entity.get();
    }

    public UserProfileDTO SignUp(SignUpDTO data) {
        ProfileEntity profile = this._profileRepository.save(
                new ProfileEntity(data.getName(), data.getAge())
        );

        UserEntity user = this._userRepository.save(
                new UserEntity(data.getLogin(), BCrypt.hashpw(data.getPassword(), BCrypt.gensalt(16)), profile)
        );
        //    private UUID ProfileId;
        //    private String name;
        //    private Integer age;
        //    private String login;
        //    private String password;
        return new UserProfileDTO(user.getProfile().getId(), data.getName(), data.getAge(), data.getLogin());
    }

    public Optional<ProfileEntity> findById(UUID id) {
        return this._profileRepository.findById(id);
    }

    // exclude other field //// exclude other field //// exclude other field //// exclude other field //
//    private HashMap<String, Object> _excludeSystemFields(UserEntity user) {
//        HashMap<String, Object> value = new HashMap<>();
//        value.put("login", user.getLogin());
//        value.put("profile_id", user.getId());
//        value.put("name", user.getProfile().getName());
//        value.put("age", user.getProfile().getAge());
//
//        return value;
//    }
}