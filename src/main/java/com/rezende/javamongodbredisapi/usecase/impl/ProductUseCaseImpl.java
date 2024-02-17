package com.rezende.javamongodbredisapi.usecase.impl;

import com.rezende.javamongodbredisapi.domain.product.Product;
import com.rezende.javamongodbredisapi.endpoint.request.ProductRequest;
import com.rezende.javamongodbredisapi.exception.CategoryNotFoundException;
import com.rezende.javamongodbredisapi.exception.ProductNotFoundException;
import com.rezende.javamongodbredisapi.external.repository.ProductRepository;
import com.rezende.javamongodbredisapi.usecase.CategoryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductUseCaseImpl {
    private final CategoryUseCase categoryUseCase;
    private final ProductRepository repository;

    public Product insert(final ProductRequest productData) {
        this.categoryUseCase.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Product newProduct = new Product(productData);

        return this.repository.save(newProduct);
    }

    public Product update(final String id, final ProductRequest productData) {
        this.categoryUseCase.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Product product = new Product(productData);
        product.setId(id);

        try {
            return this.repository.save(product);
        } catch (Exception e) {
            log.error("Error on update product", e);
            throw new ProductNotFoundException();
        }
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
