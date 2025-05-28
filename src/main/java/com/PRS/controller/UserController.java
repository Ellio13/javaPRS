package com.PRS.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.PRS.model.LoginDTO;
import com.PRS.model.User;
import com.PRS.db.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepo userRepo; // Correct type


	@GetMapping("/")
	public List<User> getAll() {
		return userRepo.findAll(); // Now recognized
	}

	@GetMapping("{id}")
	public Optional<User> getById(@PathVariable int id){
		Optional<User> u = userRepo.findById(id);
		return userRepo.findById(id);
	}

	@PostMapping("")
	public User addUser(@RequestBody User user) {
		return userRepo.save(user);
	}

	@PostMapping("login")
	public User login(@RequestBody LoginDTO login) {
		Optional<User> userOptional = userRepo.findByUserName(login.getUserName());
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			if (user.getPassword().equals(login.getPassword()))
			{return user;}
			else {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
			}
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
	}

	@PutMapping("{id}")
	public User updateUser(@PathVariable int id, @RequestBody User user) {
		Optional<User> u = userRepo.findById(id);
		if (u.isPresent()) {
			user.setId(id);
			return userRepo.save(user);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id: " + id);
		}
	}
	@DeleteMapping("{id}")
	public void deleteUser(@PathVariable int id) {
		Optional<User> u = userRepo.findById(id);
		if (u.isPresent()) {
			userRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id: " + id);
		}
	}
}
