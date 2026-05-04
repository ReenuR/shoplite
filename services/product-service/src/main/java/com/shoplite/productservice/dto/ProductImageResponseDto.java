package com.shoplite.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageResponseDto {
    private UUID imageId;
    private String imageURL;
    private boolean isPrimary;
    private int displayOrder;
}
