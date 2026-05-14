package com.backend_dev_test.similar_products.client;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.backend_dev_test.similar_products.exception.ProductNotFoundException;
import com.backend_dev_test.similar_products.model.Product;

import reactor.core.publisher.Mono;

/**
 * HTTP client to interact with the Products API.
 */
@Component
public class ProductClient {

  private final WebClient webClient;
  private final Duration detailTimeout;

  public ProductClient(
      WebClient.Builder builder,
      @Value("${product.api.base-url:http://localhost:3001}") String baseUrl,
      @Value("${product.api.detail-timeout-seconds:2}") int detailTimeoutSeconds) {
    this.webClient = builder.baseUrl(baseUrl).build();
    this.detailTimeout = Duration.ofSeconds(detailTimeoutSeconds);
  }

  /**
   * Gets the list of similar product IDs for a given product ID.
   * 
   * @param productId the ID of the product to get similar products for
   * @return a Mono containing a list of similar product IDs as strings
   * @throws ProductNotFoundException if the product is not found (HTTP 4xx error)
   */
  public Mono<List<String>> getSimilarIds(String productId) {
    return webClient.get()
        .uri("/product/{productId}/similarids", productId)
        .retrieve()
        .onStatus(
            HttpStatusCode::is4xxClientError,
            response -> Mono.error(new ProductNotFoundException(productId)))
        .bodyToFlux(Integer.class)
        .map(String::valueOf)
        .collectList();
  }

  /**
   * Gets the details of a product by its ID.
   * The method retrieves the product details from the API and applies a timeout
   * to ensure that it does not take too long.
   * 
   * @param productId the ID of the product to retrieve details for
   * @return a Mono containing the product details, or an empty Mono if the
   *         request fails or times out
   */
  public Mono<Product> getProductDetail(String productId) {
    return webClient.get()
        .uri("/product/{productId}", productId)
        .retrieve()
        .bodyToMono(Product.class)
        .timeout(detailTimeout)
        .onErrorResume(ex -> Mono.empty());
  }
}
