package com.example.demo.core.profile.projections;

import java.util.UUID;

public interface ProfileProjection {
    UUID getID();
    String getName();
    Integer getAge();
}
