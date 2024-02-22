package com.rezende.javamongodbredisapi.usecase.impl;

import com.rezende.javamongodbredisapi.adapter.CategoryAdapter;
import com.rezende.javamongodbredisapi.domain.category.Category;
import com.rezende.javamongodbredisapi.endpoint.request.CategoryRequest;
import com.rezende.javamongodbredisapi.external.repository.CategoryRepository;
import com.rezende.javamongodbredisapi.usecase.CategoryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.rezende.javamongodbredisapi.exception.CategoryNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryUseCaseImpl implements CategoryUseCase {
    private final CategoryRepository repository;

    public Category insert(final CategoryRequest categoryData) {
        Category newCategory = CategoryAdapter.INSTANCE.toEntity(categoryData);

        return this.repository.save(newCategory);
    }

    @Transactional
    public Category update(final String id, final CategoryRequest categoryData) {
        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        CategoryAdapter.INSTANCE.toUpdatedEntity(category, categoryData);

        return this.repository.save(category);
    }

    public void delete(final String id) {
        try {
            this.repository.deleteById(id);
        } catch (Exception e) {
            log.error("Error on delete category", e);
            throw new CategoryNotFoundException();
        }
    }

    public List<Category> getAll() {
        return this.repository.findAll();
    }

    public Optional<Category> getById(final String id) {
        return this.repository.findById(id);
    }
}
