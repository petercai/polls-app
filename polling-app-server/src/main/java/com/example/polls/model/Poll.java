package com.example.polls.model;

import com.example.polls.model.audit.UserDateAudit;
import com.example.polls.model.common.CascadeSave;
import com.example.polls.model.common.IMongoModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "polls")
@Data
public class Poll extends UserDateAudit implements IMongoModel {
    @Transient
    public static final String SEQUENCE_NAME = "poll_sequence";

    @Id
    private Long id;

    @NotBlank
    @Size(max = 140)
    private String question;

    @DBRef
    @CascadeSave
    @Size(min = 2, max = 6)
    private List<Choice> choices = new ArrayList<>();

    @NotNull
    private Instant expirationDateTime;

    public void addChoice(Choice choice) {
        choices.add(choice);
        choice.setPoll(this);
        choice.setPoll(this);
    }

    public void removeChoice(Choice choice) {
        choices.remove(choice);
        choice.setPoll(null);
    }

    @Override
    public String getSequenceName() {
        return SEQUENCE_NAME;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", expirationDateTime=" + expirationDateTime +
                '}';
    }
}
