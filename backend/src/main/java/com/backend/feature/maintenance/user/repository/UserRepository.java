package com.backend.feature.maintenance.user.repository;

import com.backend.feature.maintenance.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    Optional<User> findByName(String name);

    Optional<User> findByNameAndPasswordAndType(String name, String password, String type);
}
