package com.colak.springutorial.repository;

import com.colak.springutorial.entity.Restaurants;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface RestaurantsRepository extends ReactiveMongoRepository<Restaurants, String> {
    Flux<Restaurants> findByNameIgnoreCase(String brand);
}
