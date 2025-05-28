package com.PRS.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.PRS.db.ProductRepo;
import com.PRS.model.Product;


@CrossOrigin
@RestController
@RequestMapping("/api/products")


public class ProductController {

	@Autowired
	private ProductRepo productRepo;

	@GetMapping("/")
	public List<Product> getAll() {
		return productRepo.findAll();
	}

	@GetMapping("{id}") 
	public Optional<Product> getById(@PathVariable int id){
		Optional<Product> p = productRepo.findById(id);
		if (p.isPresent()) {
			return productRepo.findById(id);
		}
		else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: " + id);
	}

	@PostMapping("")
	public Product addProduct(@RequestBody Product product) {
		return productRepo.save(product);
	}

	@PutMapping("{id}")
	public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
		Optional<Product> p = productRepo.findById(id);
		if (p.isPresent()) {
			product.setId(id);
			return productRepo.save(product);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: " + id);
		}}

	@DeleteMapping("{id}")
	public void deleteProduct(@PathVariable int id) {
		Optional<Product> p = productRepo.findById(id);
		if (p.isPresent()) {
			productRepo.deleteById(id); }
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: " + id);
		}
	}

}

