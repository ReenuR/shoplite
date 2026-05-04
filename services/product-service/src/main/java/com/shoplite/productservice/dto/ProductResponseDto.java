package com.shoplite.productservice.dto;

import com.shoplite.productservice.entity.ProductStatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private UUID productId;
    private String productName;
    private String productDescription;
    private BigDecimal productSellingPrice;
    private BigDecimal productOriginalPrice;
    private String categoryName;
    private ProductStatusCode productStatus;
    private List<ProductImageResponseDto> images;
    private boolean isActive;

}
