package com.rezende.javamongodbredisapi.usecase;

import com.rezende.javamongodbredisapi.domain.category.Category;
import com.rezende.javamongodbredisapi.endpoint.request.CategoryRequest;

import java.util.List;
import java.util.Optional;

public interface CategoryUseCase {
    Category insert(CategoryRequest categoryData);

    Category update(String id, CategoryRequest categoryData);

    void delete(String id);

    List<Category> getAll();

    Optional<Category> getById(String id);
}
