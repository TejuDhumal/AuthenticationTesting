package com.javatechie.repo;

import com.javatechie.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByEmail(String email);
}
