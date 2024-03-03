package com.rezende.javamongodbredisapi.usecase.impl;

import com.rezende.javamongodbredisapi.adapter.ProductAdapter;
import com.rezende.javamongodbredisapi.domain.exception.CategoryNotFoundException;
import com.rezende.javamongodbredisapi.domain.exception.ProductNotFoundException;
import com.rezende.javamongodbredisapi.domain.product.Product;
import com.rezende.javamongodbredisapi.endpoint.request.ProductRequest;
import com.rezende.javamongodbredisapi.endpoint.request.UrlMeta;
import com.rezende.javamongodbredisapi.endpoint.response.Response;
import com.rezende.javamongodbredisapi.external.repository.ProductRepository;
import com.rezende.javamongodbredisapi.usecase.CategoryUseCase;
import com.rezende.javamongodbredisapi.usecase.ProductUseCase;
import com.rezende.javamongodbredisapi.usecase.base.BasePagedUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductUseCaseImpl extends BasePagedUseCase<Product> implements ProductUseCase {
    private final CategoryUseCase categoryUseCase;
    private final ProductRepository repository;

    protected ProductUseCaseImpl(PagedResourcesAssembler<Product> pagedResourcesAssembler, CategoryUseCase categoryUseCase, ProductRepository repository) {
        super(pagedResourcesAssembler);
        this.categoryUseCase = categoryUseCase;
        this.repository = repository;
    }

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
            throw new ProductNotFoundException("Produto não encontrado", e);
        }
    }

    @Cacheable(cacheNames = "Product", key = "(#root.method.name).concat('-').concat(#urlMeta.getPage()).concat('-').concat(#urlMeta.getPageSize())")
    public Response<Product> getAll(final UrlMeta urlMeta) {
        Pageable pageable = PageRequest.of(urlMeta.getPage() - 1, urlMeta.getPageSize());
        var pagedCollection = this.repository.findAll(pageable);
        return super.execute(pagedCollection, urlMeta);
    }
}
