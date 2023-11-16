package com.elsonkhoza.webfluxmongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "products")
public class Product {

    // Will not be saved to the database if annotated with @Transient
    @Transient
    public static final String SEQUENCE_NAME = "products_sequence";

    @Id
    private Long id;
    private String name;
    private Double price;


}
