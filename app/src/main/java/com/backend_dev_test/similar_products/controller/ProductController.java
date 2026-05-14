package com.backend_dev_test.similar_products.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.backend_dev_test.similar_products.exception.ProductNotFoundException;
import com.backend_dev_test.similar_products.model.Product;
import com.backend_dev_test.similar_products.service.ProductService;

import reactor.core.publisher.Mono;

/**
 * REST controller that handles HTTP requests related to products and their
 * similar products.
 */
@RestController
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  /**
   * Endpoint to get the list of similar products for a given product ID.
   * 
   * @param productId the ID of the product to get similar products for
   * @return a Mono containing a ResponseEntity with the list of similar products,
   *         or a 404 Not Found response if the product is not found
   * @throws ProductNotFoundException if the product is not found (HTTP 4xx error)
   */
  @GetMapping("/product/{productId}/similar")
  public Mono<ResponseEntity<List<Product>>> getSimilarProducts(
      @PathVariable String productId) {

    return productService.getSimilarProducts(productId)
        .map(ResponseEntity::ok)
        .onErrorResume(
            ProductNotFoundException.class,
            ex -> Mono.just(ResponseEntity.notFound().<List<Product>>build()));
  }
}
