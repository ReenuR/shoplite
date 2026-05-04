package com.shoplite.productservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDto {
    private UUID categoryId;
    private String categoryName;
    private boolean active;
    private UUID parentCategoryId;
    private String parentCategoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
