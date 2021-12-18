package com.example.polls.model;

import com.example.polls.model.audit.DateAudit;
import com.example.polls.model.common.IMongoModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class User extends DateAudit implements IMongoModel {
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Id
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 15)
    @Indexed
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    @Indexed
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();


    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }


    @Override
    public String getSequenceName() {
        return SEQUENCE_NAME;
    }
}