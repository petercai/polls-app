package com.example.polls.model.common;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequence_generator")
@Data
public class Sequence {
    @Id
    private String id;
    private Long seq;
}
