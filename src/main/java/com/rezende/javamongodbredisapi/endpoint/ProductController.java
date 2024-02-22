package com.rezende.javamongodbredisapi.endpoint;

import com.rezende.javamongodbredisapi.domain.product.Product;
import com.rezende.javamongodbredisapi.endpoint.request.ProductRequest;
import com.rezende.javamongodbredisapi.usecase.ProductUseCase;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductUseCase useCase;

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductRequest productData){
        Product newProduct = this.useCase.insert(productData);
        return ResponseEntity.ok().body(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){
        List<Product> products = this.useCase.getAll();
        return ResponseEntity.ok().body(products);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") String id, @RequestBody ProductRequest productData){
        Product updatedProduct = this.useCase.update(id, productData);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") String id){
        this.useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
