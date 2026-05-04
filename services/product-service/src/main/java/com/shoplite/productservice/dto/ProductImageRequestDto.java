    package com.shoplite.productservice.dto;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ProductImageRequestDto {
        private String imageURL;
        private boolean isPrimary;
        private int displayOrder;
    }
