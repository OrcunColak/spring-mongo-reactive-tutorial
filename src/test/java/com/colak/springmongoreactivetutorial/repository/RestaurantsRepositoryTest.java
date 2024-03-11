package com.colak.springmongoreactivetutorial.repository;

import com.colak.springmongoreactivetutorial.entity.Restaurants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
class RestaurantsRepositoryTest {

    @Autowired
    private RestaurantsRepository repository;

    @Test
    void save() {
        repository.deleteById("1");

        Restaurants restaurants = new Restaurants();
        restaurants.setId("2");
        restaurants.setName("restaurant2");

        Restaurants saved = repository.save(restaurants).block();
        assertNotNull(saved);

        repository.findAll().subscribe(restaurant -> {
            log.info("Restaurant : {}", restaurant);
        });
    }
}
