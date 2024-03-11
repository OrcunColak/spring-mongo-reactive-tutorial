package com.colak.springmongoreactivetutorial.repository;

import com.colak.springmongoreactivetutorial.entity.Restaurants;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface RestaurantsRepository extends ReactiveMongoRepository<Restaurants, String> {
    Flux<Restaurants> findByNameIgnoreCase(String brand);
}
