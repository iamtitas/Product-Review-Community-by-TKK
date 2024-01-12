package com.nagarro.training.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.training.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	// function to find an user by email
	Optional<User> findByEmail(String email);
}
