package com.colak.springutorial.repository;

import com.colak.springutorial.entity.Restaurants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@Testcontainers
@Slf4j
class TestContainerRestaurantsRepositoryTest {

    @Container
    @ServiceConnection
    private static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @Autowired
    private RestaurantsRepository repository;

    @Test
    void deleteById() {
        Mono<Void> deleteMono = repository.deleteById("1");
        StepVerifier.create(deleteMono)
                .verifyComplete();
    }

    @Test
    void findById() {
        Mono<Restaurants> findByIdMono = repository.findById("1");
        StepVerifier.create(findByIdMono)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void save() {
        Restaurants restaurants = new Restaurants();
        restaurants.setId("2");
        restaurants.setName("restaurant2");

        Mono<Restaurants> savedRestaurant = repository.save(restaurants);
        StepVerifier.create(savedRestaurant)
                .expectNextMatches(restaurant -> restaurant.getId().equals("2"))
                .verifyComplete();

        Flux<Restaurants> restaurantsFlux = repository.findAll();
        StepVerifier.create(restaurantsFlux)
                .expectNextMatches(restaurant -> restaurant.getId().equals("2"))
                .verifyComplete();

    }
}
