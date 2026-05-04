package com.shoplite.productservice.service;

import com.shoplite.productservice.dto.CategoryRequestDto;
import com.shoplite.productservice.dto.CategoryResponseDto;
import com.shoplite.productservice.entity.Category;
import com.shoplite.productservice.exception.CategoryNotFoundException;
import com.shoplite.productservice.repo.CategoryRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getAllCategories() {
    }

    public CategoryResponseDto getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException(id));
        return toResponseDto(category);
    }

    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category parentCategory = null;
        if(categoryRequestDto.getParentCategoryId()!= null){
            parentCategory = categoryRepository.findById(categoryRequestDto.getParentCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(categoryRequestDto.getParentCategoryId()));
        }
        Category category = Category.builder()
                .categoryName(categoryRequestDto.getCategoryName())
                .parentCategory(parentCategory)
                .isActive(categoryRequestDto.getIsActive())
                .build();

        return toResponseDto(categoryRepository.save(category));
    }

    public CategoryResponseDto updateCategory(UUID id, CategoryRequestDto request){}

    public void deleteCategoryById(UUID id){}

    public List<CategoryResponseDto> getSubcategories(UUID id){}

    private static CategoryResponseDto toResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .createdAt(category.getCreatedAt())
                .active(category.isActive())
                .updatedAt(category.getUpdatedAt())
                .parentCategoryId(
                        category.getParentCategory() != null
                        ? category.getParentCategory().getCategoryId() : null
                )
                .parentCategoryName( category.getParentCategory() != null
                        ? category.getParentCategory().getCategoryName()
                        : null)
                .build();
    }

}
