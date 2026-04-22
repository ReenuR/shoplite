package com.shoplite.userservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserProfileResponse {
        private UUID userId;
        private String username;
        private String email;
        private List<AddressResponse> addresses;
        private String phoneNumber;
        private boolean isActive;
}
