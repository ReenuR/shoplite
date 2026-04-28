package com.shoplite.productservice.dto;

import com.shoplite.productservice.entity.ProductImage;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductRequestDto {
    private String productName;
    private String productDescription;
    private BigDecimal productSellingPrice;
    private BigDecimal productOriginalPrice;
    private UUID productCategoryI;
    private List<ProductImage> images;
}
