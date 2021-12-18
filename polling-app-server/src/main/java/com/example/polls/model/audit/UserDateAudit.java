package com.example.polls.model.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;



public abstract class UserDateAudit extends DateAudit {

    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long listModifiedBy;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getListModifiedBy() {
        return listModifiedBy;
    }

    public void setListModifiedBy(Long listModifiedBy) {
        this.listModifiedBy = listModifiedBy;
    }
}
