package com.rezende.javamongodbredisapi.usecase.impl;

import com.rezende.javamongodbredisapi.adapter.ProductAdapter;
import com.rezende.javamongodbredisapi.domain.product.Product;
import com.rezende.javamongodbredisapi.endpoint.request.ProductRequest;
import com.rezende.javamongodbredisapi.exception.CategoryNotFoundException;
import com.rezende.javamongodbredisapi.exception.ProductNotFoundException;
import com.rezende.javamongodbredisapi.external.repository.ProductRepository;
import com.rezende.javamongodbredisapi.usecase.CategoryUseCase;
import com.rezende.javamongodbredisapi.usecase.ProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductUseCaseImpl implements ProductUseCase {
    private final CategoryUseCase categoryUseCase;
    private final ProductRepository repository;

    public Product insert(final ProductRequest productData) {
        this.categoryUseCase.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Product newProduct = ProductAdapter.INSTANCE.toEntity(productData);

        return this.repository.save(newProduct);
    }

    public Product update(final String id, final ProductRequest productData) {
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.categoryUseCase.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        ProductAdapter.INSTANCE.toUpdatedEntity(product, productData);

        return this.repository.save(product);
    }

    public void delete(final String id) {
        try {
            this.repository.deleteById(id);
        } catch (Exception e) {
            log.error("Error on delete product", e);
            throw new ProductNotFoundException();
        }
    }

    public List<Product> getAll() {
        return this.repository.findAll();
    }
}
