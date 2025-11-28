package com.agmkhair.ecom.repository;

import com.agmkhair.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String pass);

    Optional<User> findByEmail(String email);
}
