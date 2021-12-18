package com.example.polls.model;

import com.example.polls.model.audit.DateAudit;
import com.example.polls.model.common.IMongoModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "votes")
@Data
public class Vote extends DateAudit implements IMongoModel {

    @Transient
    public static final String SEQUENCE_NAME = "vote_sequence";

    @Id
    private Long id;


    @DBRef
    @Indexed
    private Poll poll;

    @DBRef
    private Choice choice;

    @DBRef
    @Indexed
    private User user;

    @Override
    public String getSequenceName() {
        return SEQUENCE_NAME;
    }
}
