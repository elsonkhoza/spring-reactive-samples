package com.elsonkhoza.functionalendpoints.service;


import com.elsonkhoza.functionalendpoints.model.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;


@Service
public class SequenceGeneratorService {

    @Autowired
    private ReactiveMongoOperations mongoOperations;

    public long getSequenceNumber(String sequenceName) {

        // Get the sequence query
        Query query = new Query(Criteria.where("id").is(sequenceName));
        // Update operation
        Update update = new Update().inc("sequenceNumber", 1);

        Mono<DbSequence> counter = mongoOperations
                .findAndModify(
                        query,
                        update,
                        options().returnNew(true).upsert(true),
                        DbSequence.class
                );
        long counterValue = 1;
        try {
            DbSequence dbSequence = counter.toFuture().get();
            counterValue = !Objects.isNull(dbSequence) ? dbSequence.getSequenceNumber() : 1;
        } catch (ExecutionException | InterruptedException i) {
            System.out.println(i.getMessage());
        }

        return counterValue;

    }
}
