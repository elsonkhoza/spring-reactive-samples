package com.elsonkhoza.webfluxmongodb.repository;

import com.elsonkhoza.webfluxmongodb.model.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product,Long> {

    Flux<Product> findByPriceBetween(Range<Double> priceRange);
}
