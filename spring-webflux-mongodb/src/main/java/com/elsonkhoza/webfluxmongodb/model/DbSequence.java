package com.elsonkhoza.webfluxmongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "db_sequences")
public class DbSequence {

    @Id
    private String id;
    private Long sequenceNumber;
}
