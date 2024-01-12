package com.nagarro.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import com.nagarro.training.dto.AuthenticationRequest;
import com.nagarro.training.model.*;
import com.nagarro.training.repository.UserRepository;

@RestController
public class AuthenticationController {
	private final UserRepository userRepository;

	@Autowired
	public AuthenticationController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/login")
	public String getMessage() {
		return "GET SUCCESS";
	}

	@PostMapping("/login")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Boolean> login(@RequestBody AuthenticationRequest authenticationRequest) {
		String email = authenticationRequest.getUsername();
		String password = authenticationRequest.getPassword();

		// Validate email and password against the data stored in the database for
		// authentication
		Optional<User> optionalUser = userRepository.findByEmail(email);
		boolean isAuthenticated = optionalUser.isPresent() && password.equals(optionalUser.get().getPassword());

		if (isAuthenticated) {
			return ResponseEntity.ok(true); // Authentication successful
		} else {
			return ResponseEntity.ok(false); // Invalid credentials
		}
	}

}
