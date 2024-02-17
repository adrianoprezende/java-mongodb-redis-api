package com.rezende.javamongodbredisapi.external.repository;

import com.rezende.javamongodbredisapi.domain.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
