package com.rezende.javamongodbredisapi.endpoint;

import com.rezende.javamongodbredisapi.domain.product.Product;
import com.rezende.javamongodbredisapi.domain.validation.groups.CreateValidationGroup;
import com.rezende.javamongodbredisapi.domain.validation.groups.UpdateValidationGroup;
import com.rezende.javamongodbredisapi.endpoint.base.BaseController;
import com.rezende.javamongodbredisapi.endpoint.request.ProductRequest;
import com.rezende.javamongodbredisapi.endpoint.response.Response;
import com.rezende.javamongodbredisapi.endpoint.validation.ValidateFieldsService;
import com.rezende.javamongodbredisapi.usecase.ProductUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController extends BaseController {
    private final ProductUseCase useCase;
    private final ValidateFieldsService<ProductRequest> validateFieldsService;

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductRequest productData) {
        validateFieldsService.validateRequest(productData, CreateValidationGroup.class);
        Product newProduct = this.useCase.insert(productData);
        return ResponseEntity.ok().body(newProduct);
    }

    @GetMapping
    public ResponseEntity<Response<Product>> getAll(HttpServletRequest request,
                                                    @RequestParam(value = "page", required = false, defaultValue = "1") final int page,
                                                    @RequestParam(value = "page-size", required = false, defaultValue = "10") final int size){
        Response<Product> products = this.useCase.getAll(getUrlMeta(request, page, size));
        return ResponseEntity.ok().body(products);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") String id, @RequestBody ProductRequest productData){
        validateFieldsService.validateRequest(productData, UpdateValidationGroup.class);
        Product updatedProduct = this.useCase.update(id, productData);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") String id){
        this.useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
