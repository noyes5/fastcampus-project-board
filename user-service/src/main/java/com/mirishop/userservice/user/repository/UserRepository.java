package com.mirishop.userservice.user.repository;

import com.mirishop.userservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNumberAndIsDeletedFalse(Long memberNumber);

    List<User> findAllByIsDeletedFalse();
}
