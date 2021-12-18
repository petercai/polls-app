package com.example.polls.repository;

import com.example.polls.model.Role;
import com.example.polls.model.RoleName;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
