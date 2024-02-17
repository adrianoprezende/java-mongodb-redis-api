package com.rezende.javamongodbredisapi.usecase;

import com.rezende.javamongodbredisapi.domain.product.Product;
import com.rezende.javamongodbredisapi.endpoint.request.ProductRequest;

import java.util.List;

public interface ProductUseCase {
    Product insert(ProductRequest productData);

    Product update(String id, ProductRequest productData);

    void delete(String id);

    List<Product> getAll();
}
