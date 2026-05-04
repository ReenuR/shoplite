package com.shoplite.productservice.controller;

import com.shoplite.productservice.dto.CategoryRequestDto;
import com.shoplite.productservice.dto.CategoryResponseDto;
import com.shoplite.productservice.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        List<CategoryResponseDto> response = categoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable UUID id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<List<CategoryResponseDto>> getSubcategories(@PathVariable UUID id){
        return ResponseEntity.ok(categoryService.getSubcategories(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable UUID id, @RequestBody CategoryRequestDto requestDto){
        CategoryResponseDto responseDto = categoryService.updateCategory(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteCategoryById(@PathVariable UUID id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
