package com.elsonkhoza.functionalendpoints.handler;

import com.elsonkhoza.functionalendpoints.model.Product;
import com.elsonkhoza.functionalendpoints.repository.ProductRepository;
import com.elsonkhoza.functionalendpoints.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductRepository productRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public Mono<ServerResponse> createProduct(ServerRequest request) {

        Mono<Product> saveProductMono = request.bodyToMono(Product.class).
                map(product -> {
                    product.setId(
                            sequenceGeneratorService.getSequenceNumber(Product.SEQUENCE_NAME));
                    return product;
                }).flatMap(productRepository::save);

        return ServerResponse.ok().body(saveProductMono, Product.class);

    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        Flux<Product> products = productRepository.findAll();
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(products, Product.class);
    }

}
