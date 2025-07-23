package com.funding.sandbox.dto;
import lombok.Data;
import com.funding.sandbox.model.UserRole;
@Data

public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private UserRole role;

}
