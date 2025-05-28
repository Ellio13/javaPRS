package com.PRS.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.PRS.model.Vendor;
import com.PRS.db.VendorRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")

public class VendorController {

	@Autowired
	private VendorRepo vendorRepo; // Correct type


	@GetMapping("/")
	public List<Vendor> getAll() {
		return vendorRepo.findAll(); // Now recognized
	}

	@GetMapping("{id}")
	public Vendor getById(@PathVariable int id){
		Optional<Vendor> v = vendorRepo.findById(id);
		if (v.isPresent()) {
			return v.get();
		}
		else {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id: " + id);
		}}

	@PostMapping("")
	public Vendor addVendor(@RequestBody Vendor vendor) {
		return vendorRepo.save(vendor);
	}

	@PutMapping("{id}")
	public Vendor updateVendor(@PathVariable int id, @RequestBody Vendor vendor) {
		Optional<Vendor> u = vendorRepo.findById(id);
		if (u.isPresent()) {
			vendor.setId(id);
			return vendorRepo.save(vendor);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id: " + id);
		}
	}
	@DeleteMapping("{id}")
	public void deleteVendor(@PathVariable int id) {
		Optional<Vendor> v = vendorRepo.findById(id);
		if (v.isPresent()) {
			vendorRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id: " + id);
		}
	}
}


