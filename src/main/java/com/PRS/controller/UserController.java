package com.PRS.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.PRS.model.LoginDTO;
import com.PRS.model.User;
import com.PRS.db.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/Users")
public class UserController {

	@Autowired
	private UserRepo userRepo; // connects to UserRepo


	@GetMapping("/")
	public List<User> getAll() {
		return userRepo.findAll();
	}

	@GetMapping("{id}")
	public User getById(@PathVariable int id) {
	    Optional<User> userOptional = userRepo.findById(id);
	    if (userOptional.isPresent()) {
	        return userOptional.get();
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id: " + id);
	    }
	}


	@PostMapping("")
	public User addUser(@RequestBody User user) {
		return userRepo.save(user);
	}

	@PostMapping("login")
	public User login(@RequestBody LoginDTO login) {
		Optional<User> userOptional = userRepo.findByUsername(login.getUsername());
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
