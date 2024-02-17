package com.rezende.javamongodbredisapi.external.repository;

import com.rezende.javamongodbredisapi.domain.category.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
}
