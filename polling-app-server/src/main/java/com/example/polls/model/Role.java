package com.example.polls.model;

import com.example.polls.model.common.IMongoModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "roles")
@Data
@NoArgsConstructor
public class Role implements IMongoModel {

    @Transient
    public static final String SEQUENCE_NAME = "role_sequence";

    @Id
    private Long id;

    private RoleName name;


    @Override
    public String getSequenceName() {
        return SEQUENCE_NAME;
    }
}
