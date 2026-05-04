package com.shoplite.productservice.service;

import com.shoplite.productservice.dto.CategoryRequestDto;
import com.shoplite.productservice.dto.CategoryResponseDto;
import com.shoplite.productservice.entity.Category;
import com.shoplite.productservice.exception.CategoryNotFoundException;
import com.shoplite.productservice.repo.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryService::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new CategoryNotFoundException(id));
        return toResponseDto(category);
    }

    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category parentCategory = null;
        if(categoryRequestDto.getParentCategoryId()!= null){
            parentCategory = categoryRepository.findById(categoryRequestDto.getParentCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(categoryRequestDto.getParentCategoryId()));
        }
        Category category = toCategoryEntity(categoryRequestDto, parentCategory);
        return toResponseDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponseDto updateCategory(UUID id, CategoryRequestDto request){
        Category parentCategory = null;
        if(request.getParentCategoryId() != null){
            parentCategory = categoryRepository.findById(request.getParentCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(request.getParentCategoryId()));
        }
        Category updateCategory = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        updateCategory.setParentCategory(parentCategory);
        updateCategory.setCategoryName(request.getCategoryName());
        updateCategory.setActive(request.getIsActive());
        return toResponseDto(categoryRepository.save(updateCategory));

    }
    @Transactional
    public void deleteCategoryById(UUID id){
      Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
      category.setActive(false);
      categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getSubcategories(UUID id){
        if(!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        return categoryRepository.findByParentCategory_CategoryId(id)
                .stream()
                .map(CategoryService::toResponseDto)
                .toList();
    }

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

    private static Category toCategoryEntity(CategoryRequestDto categoryRequestDto, Category parentCategory) {
        return Category.builder()
                .categoryName(categoryRequestDto.getCategoryName())
                .parentCategory(parentCategory)
                .isActive(categoryRequestDto.getIsActive())
                .build();

    }

}
