package com.example.polls.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;

/* spring data auto-audit

1 : Make sure you have spring-data-mongodb
2 : if you are using @CreatedDate or @LastModifiedDate then you don't need any additional configuration.
class ClassName {
  @CreatedDate
  private DateTime createdDate;
  @LastModifiedDate
  private DateTime @lastModifiedDate;

}
3: if you are using @CreatedBy and @LastModifiedBy then you have to implement AuditorAware<T> SPI interface
class ClassName {
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String lastModifiedBy;
}

public class AppAuditor implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        // get your user name here
        return "xxxx";
    }
}
Implementation of AuditorAware based on Spring Security from spring doc

class SpringSecurityAuditorAware implements AuditorAware<User> {

  public User getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }
    return ((MyUserDetails) authentication.getPrincipal()).getUser();
  }
}
 */

@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public abstract class DateAudit implements Serializable {

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}
