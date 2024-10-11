package com.colak.springutorial.repository;

import com.colak.springutorial.entity.Restaurants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RestaurantsRepositoryTest {

    @Autowired
    private RestaurantsRepository repository;

    @BeforeEach
    public void setup() {
        Flux<Restaurants> deleteAndInsert = repository.deleteAll()
                .thenMany(repository.saveAll(
                        Flux.just(
                                new Restaurants(null, "restaurant1")
                        )));

        StepVerifier.create(deleteAndInsert)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void findAll() {
        StepVerifier.create(repository.findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(_ -> true)
                .consumeRecordedWith(restaurants -> {
                    assertThat(restaurants).hasSize(1);
                    assertThat(restaurants).extracting(Restaurants::getName)
                            .containsExactlyInAnyOrder("restaurant1");
                })
                .verifyComplete();
    }

    @Test
    public void findByNameIgnoreCase() {
        StepVerifier.create(repository.findByNameIgnoreCase("RESTAURANT1"))
                .expectNextMatches(restaurants -> restaurants.getName().equals("restaurant1"))
                .verifyComplete();
    }

    @Test
    void save() {
        Restaurants restaurants = new Restaurants();
        restaurants.setId("2");
        restaurants.setName("restaurant2");

        // Save the restaurant and then verify using StepVerifier
        StepVerifier.create(repository.save(restaurants))
                .expectNextMatches(saved -> saved.getId().equals("2") && saved.getName().equals("restaurant2"))
                .expectComplete()
                .verify();

        // Clean up by deleting the restaurant entry
        StepVerifier.create(repository.deleteById("2"))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteById() {
        // Clean up by deleting the restaurant entry
        StepVerifier.create(repository.deleteById("2"))
                .expectComplete()
                .verify();
    }

    @Test
    void saveAll() {
        Flux<Restaurants> deleteAndInsert = repository.deleteAll()
                .thenMany(repository.saveAll(
                        Flux.just(
                                new Restaurants(null, "restaurant 3"),
                                new Restaurants(null, "restaurant 4"),
                                new Restaurants(null, "restaurant 5"),
                                new Restaurants(null, "restaurant 6")
                        )));

        StepVerifier.create(deleteAndInsert)
                .expectNextCount(4)
                .verifyComplete();
    }
}
