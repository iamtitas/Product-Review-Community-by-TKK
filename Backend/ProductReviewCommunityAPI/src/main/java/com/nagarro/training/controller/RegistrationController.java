package com.nagarro.training.controller;

import com.nagarro.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@RestController
public class RegistrationController {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	public RegistrationController() {
	}

	@PostMapping("/register")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			User newUser = new User();
			newUser.setName(request.getName());
			newUser.setEmail(request.getEmail());
			newUser.setPassword(request.getPassword());

			entityManager.persist(newUser);
			entityManager.getTransaction().commit();

			return ResponseEntity.ok("User registered successfully");
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} finally {
			entityManager.close();
		}
	}

	static class UserRegistrationRequest {
		private String name;
		private String email;
		private String password;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
}
