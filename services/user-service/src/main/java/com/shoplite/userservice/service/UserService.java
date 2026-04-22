package com.shoplite.userservice.service;

import com.shoplite.userservice.dto.AddressRequest;
import com.shoplite.userservice.dto.UserRegistrationRequest;
import com.shoplite.userservice.dto.UserRegistrationResponse;
import com.shoplite.userservice.entity.Address;
import com.shoplite.userservice.entity.User;
import com.shoplite.userservice.exception.DuplicateKeyException;
import com.shoplite.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
}
