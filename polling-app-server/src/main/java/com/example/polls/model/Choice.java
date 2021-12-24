package com.example.polls.model;

import com.example.polls.model.common.IMongoModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Document(collection = "choices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Choice implements IMongoModel {
    @Transient
    public static final String SEQUENCE_NAME = "choice_sequence";

    @Id
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String text;

    @DBRef
    private Poll poll;


    public Choice(String text) {
        this.text = text;
    }

    @Override
    public String getSequenceName() {
        return SEQUENCE_NAME;
    }
}
