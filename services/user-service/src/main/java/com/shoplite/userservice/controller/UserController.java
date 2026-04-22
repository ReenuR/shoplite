package com.shoplite.userservice.controller;

import com.shoplite.userservice.dto.UserProfileResponse;
import com.shoplite.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserProfileResponse> findUserById(@PathVariable UUID id){
        UserProfileResponse userProfileResponse = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userProfileResponse);
    }
}
