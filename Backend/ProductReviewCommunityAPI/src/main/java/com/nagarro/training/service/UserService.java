package com.nagarro.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nagarro.training.model.User;
import com.nagarro.training.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User registerUser(String name, String email, String password) {
		// Check if the email is already registered
		Optional<User> existingUser = userRepository.findByEmail(email);
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException("Email is already registered");
		}

		// Hash the password
		String hashedPassword = passwordEncoder.encode(password);

		// Create a new user object
		User user = new User(name, email, hashedPassword);

		// Save the user to the database
		return userRepository.save(user);
	}

	public User authenticateUser(String email, String password) {
		// Retrieve the user by email
		Optional<User> optionalUser = userRepository.findByEmail(email);
		User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

		// Verify the password
		if (!password.equals(user.getPassword())) {
			throw new IllegalArgumentException("Invalid email or password");
		}

		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByEmail(username);
		User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new ArrayList<>());
	}
}
