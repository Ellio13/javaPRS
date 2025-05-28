package com.PRS.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PRS.model.User;


public interface UserRepo extends JpaRepository<User, Integer> {  //provide CRUD functionality

	Optional<User> findByUserName(String userName);

}
