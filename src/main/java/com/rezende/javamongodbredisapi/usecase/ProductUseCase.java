package com.rezende.javamongodbredisapi.usecase;

import com.rezende.javamongodbredisapi.domain.product.Product;
import com.rezende.javamongodbredisapi.endpoint.request.ProductRequest;
import com.rezende.javamongodbredisapi.endpoint.request.UrlMeta;
import com.rezende.javamongodbredisapi.endpoint.response.Response;

import java.util.List;

public interface ProductUseCase {
    Product insert(ProductRequest productData);

    Product update(String id, ProductRequest productData);

    void delete(String id);

    Response<Product> getAll(UrlMeta urlMeta);
}
