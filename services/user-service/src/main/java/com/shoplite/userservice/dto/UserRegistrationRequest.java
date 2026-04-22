package com.shoplite.userservice.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserRegistrationRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private AddressRequest addresses;
    private String phoneNumber;
}
