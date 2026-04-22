package com.shoplite.userservice.controller;

import com.shoplite.userservice.dto.UserRegistrationRequest;
import com.shoplite.userservice.dto.UserRegistrationResponse;
import com.shoplite.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserRegistrationResponse> createUser(
            @Valid
            @RequestBody
            UserRegistrationRequest userRegistrationRequestDto){
        UserRegistrationResponse response = userService.createUser(userRegistrationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
