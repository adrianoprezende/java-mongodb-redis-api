package com.rezende.javamongodbredisapi.fixtures;

import com.rezende.javamongodbredisapi.endpoint.request.CategoryRequest;

public class CategoryRequestFixture {

    public static CategoryRequest getAValidCategoryRequest() {
        return new CategoryRequest("Comida Tailandesa","Categoria de Comida Tailandesa","987654322");
    }

    public static String getValidChineseCategoryJsonRequest() {
        return "{\n" +
                "    \"title\": \"Comida Tailandesa\", \n" +
                "    \"description\": \"Categoria de Comida Tailandesa\", \n" +
                "    \"ownerId\": \"987654322\"\n" +
                "}";
    }

    public static String getValidChineseFoodCategoryJsonRequest() {
        return "{\n" +
                "    \"title\": \"Comida Chinesa\", \n" +
                "    \"description\": \"Categoria de Comida Chinesa\", \n" +
                "    \"ownerId\": \"123456\"\n" +
                "}";
    }
}
