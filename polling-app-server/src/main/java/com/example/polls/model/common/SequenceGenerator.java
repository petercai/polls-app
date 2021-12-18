package com.example.polls.model.common;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class SequenceGenerator {
    private final MongoOperations operations;

    public SequenceGenerator(MongoOperations operations) {
        this.operations = operations;
    }

    public long generate(String seqName) {
        Sequence sequence = operations.findAndModify(Query.query(where("_id").is(seqName)),
                                                     new Update().inc("seq",
                                                                           1),
                                                     FindAndModifyOptions.options().returnNew(true).upsert(true),
                                                     Sequence.class);
        return sequence != null ? sequence.getSeq() : 1;
    }

}
