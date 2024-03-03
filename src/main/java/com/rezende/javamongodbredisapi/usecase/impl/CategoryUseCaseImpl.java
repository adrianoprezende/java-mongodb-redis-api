package com.rezende.javamongodbredisapi.usecase.impl;

import com.rezende.javamongodbredisapi.adapter.CategoryAdapter;
import com.rezende.javamongodbredisapi.domain.category.Category;
import com.rezende.javamongodbredisapi.domain.exception.CategoryNotFoundException;
import com.rezende.javamongodbredisapi.endpoint.request.CategoryRequest;
import com.rezende.javamongodbredisapi.endpoint.request.UrlMeta;
import com.rezende.javamongodbredisapi.endpoint.response.Response;
import com.rezende.javamongodbredisapi.external.repository.CategoryRepository;
import com.rezende.javamongodbredisapi.usecase.CategoryUseCase;
import com.rezende.javamongodbredisapi.usecase.base.BasePagedUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CategoryUseCaseImpl extends BasePagedUseCase<Category> implements CategoryUseCase {
    private final CategoryRepository repository;

    protected CategoryUseCaseImpl(final PagedResourcesAssembler<Category> pagedResourcesAssembler, final CategoryRepository repository) {
        super(pagedResourcesAssembler);
        this.repository = repository;
    }

    public Category insert(final CategoryRequest categoryData) {
        Category newCategory = CategoryAdapter.INSTANCE.toEntity(categoryData);

        return this.repository.save(newCategory);
    }

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
            throw new CategoryNotFoundException("Categoria não encontrada.", e);
        }
    }

    @Cacheable(cacheNames = "Category", key = "(#root.method.name).concat('-').concat(#urlMeta.getPage()).concat('-').concat(#urlMeta.getPageSize())")
    public Response<Category> getAll(final UrlMeta urlMeta) {
        Pageable pageable = PageRequest.of(urlMeta.getPage() - 1, urlMeta.getPageSize());
        var pagedCollection = this.repository.findAll(pageable);
        return super.execute(pagedCollection, urlMeta);
    }

    @Cacheable(cacheNames = "Category", key = "(#id).concat(#root.method.name)")
    public Optional<Category> getById(final String id) {
        return this.repository.findById(id);
    }
}
