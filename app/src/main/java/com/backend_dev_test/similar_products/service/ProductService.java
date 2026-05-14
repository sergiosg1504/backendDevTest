package com.backend_dev_test.similar_products.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend_dev_test.similar_products.client.ProductClient;
import com.backend_dev_test.similar_products.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service class responsible for handling business logic related to products and
 * their similar products.
 */
@Service
public class ProductService {

  private final ProductClient productClient;

  public ProductService(ProductClient productClient) {
    this.productClient = productClient;
  }

  /**
   * Gets the list of similar products for a given product ID.
   * The method first retrieves the list of similar product IDs and then fetches
   * the details for each similar product in parallel. The results are collected
   * into a list and returned as a Mono.
   * 
   * @param productId the ID of the product to get similar products for
   * @return a Mono containing a list of similar products, or an empty list if the
   *         product is not found or if there are no similar products
   */
  public Mono<List<Product>> getSimilarProducts(String productId) {
    return productClient.getSimilarIds(productId)
        .flatMapMany(Flux::fromIterable)
        .flatMap(productClient::getProductDetail) // send requests in parallel
        .collectList();
  }
}
