package com.backend_dev_test.similar_products.model;

public record Product(
    String id,
    String name,
    double price,
    boolean availability) {
}
