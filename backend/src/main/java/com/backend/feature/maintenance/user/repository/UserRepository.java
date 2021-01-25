package com.backend.feature.maintenance.user.repository;

import com.backend.feature.maintenance.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByName(String name);
    Optional<User> findByNameAndPassword(String name, String password);
}
