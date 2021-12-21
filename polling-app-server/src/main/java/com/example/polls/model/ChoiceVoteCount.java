package com.example.polls.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChoiceVoteCount {
    private Long _id;
    private Long choiceId;
    private Long voteCount;

    public Long getChoiceId() {
        return _id;
    }
}

