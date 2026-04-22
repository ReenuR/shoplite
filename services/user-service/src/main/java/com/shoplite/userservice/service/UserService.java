package com.shoplite.userservice.service;

import com.shoplite.userservice.dto.*;
import com.shoplite.userservice.entity.Address;
import com.shoplite.userservice.entity.User;
import com.shoplite.userservice.exception.DuplicateKeyException;
import com.shoplite.userservice.exception.InvalidCredentialsException;
import com.shoplite.userservice.exception.UserNotFoundException;
import com.shoplite.userservice.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService1) {
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

        User user = getUser(userRegistrationRequestDto);
        Address address = getAddress(userRegistrationRequestDto);

        user.setAddresses(List.of(address));
        address.setUser(user);

        User savedUser = userRepository.save(user);
        UserRegistrationResponse userResponseDto = UserRegistrationResponse.builder()
                .userId(savedUser.getUserId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
        return userResponseDto;
    }

    private static @NonNull Address getAddress(UserRegistrationRequest userRegistrationRequestDto) {
        Address address = new Address();
        AddressRequest addressRequestDto = userRegistrationRequestDto.getAddresses();
        address.setCity(addressRequestDto.getCity());
        address.setState(addressRequestDto.getState());
        address.setStreet(addressRequestDto.getStreet());
        address.setCountry(addressRequestDto.getCountry());
        address.setZipCode(addressRequestDto.getZipCode());
        return address;
    }

    private @NonNull User getUser(UserRegistrationRequest userRegistrationRequestDto) {
        User user = new User();
        user.setEmail(userRegistrationRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
        user.setPhoneNumber(userRegistrationRequestDto.getPhoneNumber());
        user.setActive(true);
        user.setUsername(userRegistrationRequestDto.getUsername());
        return user;
    }

    public LoginResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + loginRequest.getEmail());
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        String jstToken = jwtService.generateToken(user.getUserId().toString(), user.getEmail());

        return LoginResponse.builder().jwtToken(jstToken).build();

    }

    @Transactional
    public UserProfileResponse findUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Address> address = user.getAddresses();
        List<AddressResponse> addressResponse = toAddressResponses(address);
        return getUserProfileResponse(user, addressResponse);

    }

    private UserProfileResponse getUserProfileResponse(User user, List<AddressResponse> addressResponse) {
        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .addresses(addressResponse)
                .isActive(user.isActive()).build();
    }

    private List<AddressResponse> toAddressResponses(List<Address> address) {
        return address.stream().map(addr -> AddressResponse.builder()
                .addressId(addr.getAddressId())
                .state(addr.getState())
                .city(addr.getCity())
                .zipCode(addr.getZipCode())
                .street(addr.getStreet())
                .country(addr.getCountry()).build()).toList();

    }
}
