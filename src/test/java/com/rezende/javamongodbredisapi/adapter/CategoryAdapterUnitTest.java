package com.rezende.javamongodbredisapi.adapter;

import com.rezende.javamongodbredisapi.endpoint.request.CategoryRequest;
import com.rezende.javamongodbredisapi.fixtures.CategoryRequestFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryAdapterUnitTest {

    @DisplayName("Deve validar Category Adapter")
    @MethodSource("getTestCategoryRequest")
    @ParameterizedTest
    void whenThereIsACategoryRequest_ThenReturnCategory(final CategoryRequest request) {
        // when
        var category = CategoryAdapter.INSTANCE.toEntity(request);

        // then
        Assertions.assertAll("Validações dos Atributos Convertidos",
                () -> assertEquals(request.title(), category.getTitle()),
                () -> assertEquals(request.description(), category.getDescription()),
                () -> assertEquals(request.ownerId(), category.getOwnerId())
        );
    }

    @DisplayName("Deve validar Category Adapter quando Request for Nulo")
    @NullSource
    @ParameterizedTest
    void whenThereIsntACategoryRequest_ThenReturnNull(final CategoryRequest request) {
        // when
        var category = CategoryAdapter.INSTANCE.toEntity(request);

        // then
        Assertions.assertNull(category);
    }

    private static Stream<Arguments> getTestCategoryRequest() {
        return Stream.of(
                Arguments.of(CategoryRequestFixture.getAValidCategoryRequest())
        );
    }

}
