package com.example.polls.model.audit;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

//import java.io.Serializable;
import java.time.Instant;



public abstract class DateAudit /*implements Serializable*/ {

    @CreatedDate
    private Instant createDate;

    @LastModifiedDate
    private Instant lastModifiedDate;

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
