package com.elsonkhoza.webfluxmongodb.service;

import com.elsonkhoza.webfluxmongodb.model.Product;
import com.elsonkhoza.webfluxmongodb.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public Mono<Product> findProductByID(Long id) {
        return productRepository.findById(id);
    }

    public Flux<Product> findAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Find products between a price a range
     * @param minPrice minimum price
     * @param maxPrice maximum price
     * @return a flux of products between the given price range
     */
    public Flux<Product> findProductBetweenPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(
                Range.closed(minPrice, maxPrice)
        );
    }

    public Mono<Product> updateProduct(Long id, Product requestProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(requestProduct.getName());
                    product.setPrice(requestProduct.getPrice());
                    return product;
                })
                .flatMap(productRepository::save);
    }

    public Mono<Product> insertProduct(Product requestProduct) {
        // Generate an id
        requestProduct.setId(sequenceGeneratorService.getSequenceNumber(Product.SEQUENCE_NAME));
        return productRepository.save(requestProduct);
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }


}
