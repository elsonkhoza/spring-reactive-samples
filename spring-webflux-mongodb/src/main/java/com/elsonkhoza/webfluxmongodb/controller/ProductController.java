package com.elsonkhoza.webfluxmongodb.controller;

import com.elsonkhoza.webfluxmongodb.model.Product;
import com.elsonkhoza.webfluxmongodb.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Mono<Product> getProduct(@PathVariable Long id) {
        return productService.findProductByID(id);
    }

    @GetMapping(path = "/products")
    public Flux<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/price-range")
    public Flux<Product> getProductBetweenPriceRange(@RequestParam double min, double max) {
        return productService.findProductBetweenPriceRange(min, max);
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody Product product) {
        return productService.insertProduct(product);
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }


}
