package com.rezende.javamongodbredisapi.adapter;

import com.rezende.javamongodbredisapi.domain.category.Category;
import com.rezende.javamongodbredisapi.endpoint.request.CategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CategoryAdapter {

    public static final CategoryAdapter INSTANCE = getMapper(CategoryAdapter.class);

    public abstract Category toEntity(final CategoryRequest request);

    public abstract void toUpdatedEntity(@MappingTarget final Category category, final CategoryRequest categoryRequest);
}
