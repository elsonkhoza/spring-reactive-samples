package com.elsonkhoza.functionalendpoints.handler;

import com.elsonkhoza.functionalendpoints.model.Product;
import com.elsonkhoza.functionalendpoints.repository.ProductRepository;
import com.elsonkhoza.functionalendpoints.service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
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

        Mono<Product> productMono = request.bodyToMono(Product.class);
        productMono.map(product -> {
            product.setId(
                    // setting the product id
                    sequenceGeneratorService.getSequenceNumber(Product.SEQUENCE_NAME));
            return product;
        }).flatMap(productRepository::save);
        return ServerResponse.ok().body(productMono,Product.class);

    }

}
