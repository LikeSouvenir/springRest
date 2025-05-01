package com.example.demo.core.users.user.projections;

import com.example.demo.core.users.profile.projections.ProfileProjection;

import java.util.UUID;

public interface FullUserProjection {
    UUID getId();
    String getLogin();
    String getPassword();
    UUID getProfileId();
    String getName();
    Integer getAge();
}
