package com.PRS.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PRS.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{  //provide CRUD functionality
}
