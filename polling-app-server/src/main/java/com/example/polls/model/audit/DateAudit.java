package com.example.polls.model.audit;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import java.time.Instant;


@Data
public abstract class DateAudit implements Persistable {

    @CreatedDate
    private Instant createDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    @Override
    public boolean isNew() {
        return createDate == null;
    }
}
