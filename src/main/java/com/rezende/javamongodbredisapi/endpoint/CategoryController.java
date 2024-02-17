package com.rezende.javamongodbredisapi.endpoint;

import com.rezende.javamongodbredisapi.domain.category.Category;
import com.rezende.javamongodbredisapi.endpoint.request.CategoryRequest;
import com.rezende.javamongodbredisapi.usecase.CategoryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryUseCase useCase;

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryRequest categoryData) {
        Category newCategory = this.useCase.insert(categoryData);
        return ResponseEntity.ok().body(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = this.useCase.getAll();
        return ResponseEntity.ok().body(categories);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") String id, @RequestBody CategoryRequest categoryData) {
        Category updatedCategory = this.useCase.update(id, categoryData);
        return ResponseEntity.ok().body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable("id") String id) {
        this.useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
