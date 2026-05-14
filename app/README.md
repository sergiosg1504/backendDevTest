# Similar Products Service

A reactive Spring Boot service that provides a list of similar products for a given product ID.

## Description

This application implements the REST contract agreed upon in [similarProducts.yaml](../similarProducts.yaml). It uses the existing APIs documented in [existingApis.yaml](../existingApis.yaml) to:

1. Fetch similar product IDs
2. Retrieve details for each similar product in parallel
3. Return the complete list of similar products

## Features

- **Reactive**: WebFlux for maximum performance and scalability
- **Non-blocking**: Uses Mono/Flux for asynchronous operations
- **Parallel**: Requests product details simultaneously
- **Resilient**: HTTP error handling and configurable timeouts

## Main Components

- **ProductController**: HTTP endpoint, error handling
- **ProductService**: Orchestrates parallel requests
- **ProductClient**: Reactive HTTP client
- **ProductNotFoundException**: Custom exception
- **WebClientConfig**: Bean configuration

## Installation and Setup

### Build

```bash
mvn clean package
```

### Run

```bash
java -jar target/similar-products-0.0.1-SNAPSHOT.jar
```

The application will be available at `http://localhost:5000`
