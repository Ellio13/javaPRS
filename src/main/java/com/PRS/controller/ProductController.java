// ProductController.java
package com.PRS.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.PRS.db.LineItemRepo;
import com.PRS.db.ProductRepo;
import com.PRS.db.VendorRepo;
import com.PRS.model.LineItem;
import com.PRS.model.Product;
import com.PRS.model.ProductDTO;
import com.PRS.model.Vendor;

@CrossOrigin
@RestController
@RequestMapping("/api/Products")
public class ProductController {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private VendorRepo vendorRepo;
	
	@Autowired
	private LineItemRepo lineItemRepo;

	@GetMapping("/")
	public List<Product> getAll() {
		return productRepo.findAll();
	}

	@GetMapping("{id}")
	public Product getById(@PathVariable int id) {
		return productRepo.findById(id).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: " + id));
	}

	@PostMapping("")
	public Product addProduct(@RequestBody ProductDTO dto) {
		Product product = new Product();
		Vendor vendor = vendorRepo.findById(dto.getVendorId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor not found"));

		product.setVendor(vendor);
		product.setPartNumber(dto.getPartNumber());
		product.setName(dto.getName());
		product.setPrice(dto.getPrice());
		product.setUnit(dto.getUnit());
		product.setPhotoPath(dto.getPhotoPath());

		return productRepo.save(product);
	}

	
	//edit product using ProductDTO
	@PutMapping("{id}")
	public Product updateProduct(@PathVariable int id, @RequestBody ProductDTO dto) {
		Product existingProduct = productRepo.findById(id).orElseThrow(() ->
			new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id: " + id));

		Vendor vendor = vendorRepo.findById(dto.getVendorId())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor not found"));

		existingProduct.setVendor(vendor);
		existingProduct.setPartNumber(dto.getPartNumber());
		existingProduct.setName(dto.getName());
		existingProduct.setPrice(dto.getPrice());
		existingProduct.setUnit(dto.getUnit());
		existingProduct.setPhotoPath(dto.getPhotoPath());

		return productRepo.save(existingProduct);
	}

	@DeleteMapping("{id}")
	public void deleteProduct(@PathVariable int id) {

	    if (!productRepo.existsById(id)) {
	        throw new ResponseStatusException(
	                HttpStatus.NOT_FOUND,
	                "Product not found for id: " + id);
	    }

	    
	    //findByProductId in LineItemRepo
	    List<LineItem> lines = lineItemRepo.findByProductId(id);
	    if (!lines.isEmpty()) {
	        lineItemRepo.deleteAll(lines);
	    }

	    //delete the product
	    productRepo.deleteById(id);
	}

}
