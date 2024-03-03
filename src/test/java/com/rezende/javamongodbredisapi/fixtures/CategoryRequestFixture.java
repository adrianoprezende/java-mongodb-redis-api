package com.rezende.javamongodbredisapi.fixtures;

public class CategoryRequestFixture {

    public static String getValidChineseCategoryRequest() {
        return "{\n" +
                "    \"title\": \"Comida Tailandesa\", \n" +
                "    \"description\": \"Categoria de Comida Tailandesa\", \n" +
                "    \"ownerId\": \"987654322\"\n" +
                "}";
    }

    public static String getValidChineseFoodCategoryRequest() {
        return "{\n" +
                "    \"title\": \"Comida Chinesa\", \n" +
                "    \"description\": \"Categoria de Comida Chinesa\", \n" +
                "    \"ownerId\": \"123456\"\n" +
                "}";
    }
}
