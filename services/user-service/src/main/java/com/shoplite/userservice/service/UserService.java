package com.shoplite.userservice.service;

import com.shoplite.userservice.dto.*;
import com.shoplite.userservice.entity.Address;
import com.shoplite.userservice.entity.User;
import com.shoplite.userservice.exception.DuplicateKeyException;
import com.shoplite.userservice.exception.InvalidCredentialsException;
import com.shoplite.userservice.exception.UserNotFoundException;
import com.shoplite.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService1){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService1;
    }

    public UserRegistrationResponse createUser(UserRegistrationRequest userRegistrationRequestDto) {

        if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
            throw new DuplicateKeyException("Email already exists");
        }
        if (userRepository.existsByUsername(userRegistrationRequestDto.getUsername())) {
            throw new DuplicateKeyException("Username already exists");
        }

        User user = new User();
        user.setEmail(userRegistrationRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
        user.setPhoneNumber(userRegistrationRequestDto.getPhoneNumber());
        user.setActive(true);
        user.setUsername(userRegistrationRequestDto.getUsername());

        Address address = new Address();
        AddressRequest addressRequestDto = userRegistrationRequestDto.getAddresses();
        address.setCity(addressRequestDto.getCity());
        address.setState(addressRequestDto.getState());
        address.setStreet(addressRequestDto.getStreet());
        address.setCountry(addressRequestDto.getCountry());
        address.setZipCode(addressRequestDto.getZipCode());
        user.setAddresses(List.of(address));
        address.setUser(user);
        UserRegistrationResponse userResponseDto;
        User savedUser = userRepository.save(user);
        userResponseDto = UserRegistrationResponse.builder()
                    .userId(savedUser.getUserId())
                    .username(savedUser.getUsername())
                    .email(savedUser.getEmail())
                    .build();
        return userResponseDto;
    }

    public LoginResponse login(LoginRequest loginRequest){

        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user==null){
            throw new UserNotFoundException("User not found with email: " + loginRequest.getEmail());
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        String jstToken = jwtService.generateToken(user.getUserId().toString(), user.getEmail());

        return LoginResponse.builder().jwtToken(jstToken).build();

    }
}
