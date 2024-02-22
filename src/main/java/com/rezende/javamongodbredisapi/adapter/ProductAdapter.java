package com.rezende.javamongodbredisapi.adapter;

import com.rezende.javamongodbredisapi.domain.product.Product;
import com.rezende.javamongodbredisapi.endpoint.request.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ProductAdapter {

    public static final ProductAdapter INSTANCE = getMapper(ProductAdapter.class);

    @Mapping(target = "category", source = "categoryId")
    public abstract Product toEntity(final ProductRequest request);

    @Mapping(target = "category", source = "categoryId")
    public abstract void toUpdatedEntity(@MappingTarget final Product product, final ProductRequest productRequest);
}
