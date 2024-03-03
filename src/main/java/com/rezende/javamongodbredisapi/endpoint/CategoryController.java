package com.rezende.javamongodbredisapi.endpoint;

import com.rezende.javamongodbredisapi.domain.category.Category;
import com.rezende.javamongodbredisapi.domain.validation.groups.CreateValidationGroup;
import com.rezende.javamongodbredisapi.domain.validation.groups.UpdateValidationGroup;
import com.rezende.javamongodbredisapi.endpoint.base.BaseController;
import com.rezende.javamongodbredisapi.endpoint.request.CategoryRequest;
import com.rezende.javamongodbredisapi.endpoint.request.UrlMeta;
import com.rezende.javamongodbredisapi.endpoint.response.Response;
import com.rezende.javamongodbredisapi.endpoint.validation.ValidateFieldsService;
import com.rezende.javamongodbredisapi.usecase.CategoryUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController extends BaseController {

    private final CategoryUseCase useCase;
    private final ValidateFieldsService<CategoryRequest> validateFieldsService;

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryRequest categoryData) {
        validateFieldsService.validateRequest(categoryData, CreateValidationGroup.class);
        Category newCategory = this.useCase.insert(categoryData);
        return ResponseEntity.ok().body(newCategory);
    }

    @GetMapping
    public ResponseEntity<Response<Category>> getAll(HttpServletRequest request,
                                                 @RequestParam(value = "page", required = false, defaultValue = "1") final int page,
                                                 @RequestParam(value = "page-size", required = false, defaultValue = "10") final int size) {
        Response<Category> categories = this.useCase.getAll(getUrlMeta(request, page, size));
        return ResponseEntity.ok().body(categories);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") String id, @RequestBody CategoryRequest categoryData) {
        validateFieldsService.validateRequest(categoryData, UpdateValidationGroup.class);
        Category updatedCategory = this.useCase.update(id, categoryData);
        return ResponseEntity.ok().body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable("id") String id) {
        this.useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
