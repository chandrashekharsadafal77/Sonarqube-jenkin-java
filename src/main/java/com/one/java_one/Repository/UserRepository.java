package com.one.java_one.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.one.java_one.entity.User;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
