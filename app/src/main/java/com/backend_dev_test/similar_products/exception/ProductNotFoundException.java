package com.backend_dev_test.similar_products.exception;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String productId) {
    super("Product with id " + productId + " not found.");
  }
}
