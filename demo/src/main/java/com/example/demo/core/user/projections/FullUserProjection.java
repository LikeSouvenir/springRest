package com.example.demo.core.user.projections;

import java.util.UUID;

public interface FullUserProjection {
    UUID getId();
    String getLogin();
    String getPassword();
    UUID getProfileId();
    String getName();
    Integer getAge();
}
