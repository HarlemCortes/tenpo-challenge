package com.harlem.tenpo.sumservice.repository;

import java.util.Optional;

import com.harlem.tenpo.sumservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
