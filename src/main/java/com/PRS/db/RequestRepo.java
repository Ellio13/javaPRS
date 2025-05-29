package com.PRS.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PRS.model.Request;


//includes constraint "findTopByOrderByRequestNumberDesc" to get the last request number
// and "findByStatusAndUserIdNot" to get all requests where passed userId 
//is != request.userId and status = "REVIEW"

public interface RequestRepo extends JpaRepository<Request, Integer> {
	Request findTopByOrderByRequestNumberDesc();
	List<Request> findByStatusAndUser_IdNot(String status, int userId);
	
}
