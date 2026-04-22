package com.shoplite.userservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AddressResponse {
    private UUID addressId;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
