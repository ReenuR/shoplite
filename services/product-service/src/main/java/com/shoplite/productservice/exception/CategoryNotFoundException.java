package com.shoplite.productservice.exception;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(UUID id) {
        super("Category not found with id: " + id);
    }
    public CategoryNotFoundException(UUID id, Throwable cause) {
        super("Category not found with id: " + id, cause);
    }
}
