package com.PRS.db;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PRS.model.LineItem;

public interface LineItemRepo extends JpaRepository<LineItem, Integer>{ //provide CRUD functionality
 
	//finds line items by request ID, and product ID

	List<LineItem> findByProductId(int productId);
	List<LineItem> findByRequest_Id(int requestId);
}
