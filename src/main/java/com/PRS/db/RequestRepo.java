package com.PRS.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PRS.model.Request;

public interface RequestRepo extends JpaRepository<Request, Integer> {
	Request findTopByOrderByRequestNumberDesc();
	List<Request> findByStatusAndUser_Id(String status, int userId);
}
