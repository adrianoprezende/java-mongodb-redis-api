package com.rezende.javamongodbredisapi.endpoint.request;

public record ProductRequest(String title, String description, String ownerId, Integer price, String categoryId) {
}
