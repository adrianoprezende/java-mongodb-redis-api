package com.rezende.javamongodbredisapi.usecase;

import com.rezende.javamongodbredisapi.domain.category.Category;
import com.rezende.javamongodbredisapi.endpoint.request.CategoryRequest;
import com.rezende.javamongodbredisapi.endpoint.request.UrlMeta;
import com.rezende.javamongodbredisapi.endpoint.response.Response;

import java.util.List;
import java.util.Optional;

public interface CategoryUseCase {
    Category insert(CategoryRequest categoryData);

    Category update(String id, CategoryRequest categoryData);

    void delete(String id);

    Response<Category> getAll(UrlMeta urlMeta);

    Optional<Category> getById(String id);
}
