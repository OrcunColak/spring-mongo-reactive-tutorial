package com.colak.springmongoreactivetutorial.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "restaurants")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Restaurants {
    @Id
    private String id;

    private String name;
}
