package com.arunbalachandran.ehealth.dto;

import java.util.UUID;

import com.arunbalachandran.ehealth.entity.Role;
import com.arunbalachandran.ehealth.entity.User;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO {
    UUID id;
    String name;
    String email;
    Role role;

    public static UserDTO mapToDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
