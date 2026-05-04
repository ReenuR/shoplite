package com.shoplite.productservice.repo;

import com.shoplite.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    public List<Category> findByParentCategoryId(Category parentCategoryId);
}
