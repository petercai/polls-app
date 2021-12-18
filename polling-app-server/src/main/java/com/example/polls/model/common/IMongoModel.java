package com.example.polls.model.common;

public interface IMongoModel {
    String getSequenceName();
    Long getId();
    void setId(Long id);
}
