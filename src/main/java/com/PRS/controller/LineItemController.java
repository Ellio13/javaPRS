package com.PRS.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.PRS.db.LineItemRepo;
import com.PRS.model.LineItem;
import com.PRS.model.LineItemDTO;
import com.PRS.model.Product;
import com.PRS.model.Request;
import com.PRS.db.ProductRepo;
import com.PRS.db.RequestRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/lineitem")
public class LineItemController {

	@Autowired
	private LineItemRepo lineItemRepo;
	
	@Autowired
	private RequestRepo requestRepo;
	
	@Autowired
	private ProductRepo productRepo;

	@GetMapping("/")
	public List<LineItem> getAll() {
		return lineItemRepo.findAll();
	}

	@GetMapping("{id}")
	public Optional<LineItem> getById(@PathVariable int id){
		Optional<LineItem> li = lineItemRepo.findById(id);
		return li;
	}

	@PostMapping("")
	public LineItem addLineItem(@RequestBody LineItemDTO dto) {
		Request request = requestRepo.findById(dto.getRequestId())
				.orElseThrow(() -> new RuntimeException("Request not found"));
		Product product = productRepo.findById(dto.getProductId())
				.orElseThrow(() -> new RuntimeException("Product not found"));
		
		LineItem li = new LineItem();
		li.setRequest(request);
		li.setProduct(product);
		li.setQuantity(dto.getQuantity());
		
		LineItem savedLineItem = lineItemRepo.save(li);
		updateRequestTotal(request.getId());
		return savedLineItem;
	}
	
	@PutMapping("{id}")
	public LineItem updateLineItem(@PathVariable int id, @RequestBody LineItemDTO dto) {
	    LineItem existing = lineItemRepo.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found"));

	    Request request = requestRepo.findById(dto.getRequestId())
	            .orElseThrow(() -> new RuntimeException("Request not found"));
	    Product product = productRepo.findById(dto.getProductId())
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    existing.setRequest(request);
	    existing.setProduct(product);
	    existing.setQuantity(dto.getQuantity());

	    LineItem updated = lineItemRepo.save(existing);
	    updateRequestTotal(request.getId());
	    return updated;
	}

	@DeleteMapping("{id}")
	public void deleteLineItem(@PathVariable int id) {
		Optional<LineItem> li = lineItemRepo.findById(id);
		if (li.isPresent()) {
			int requestId = li.get().getRequest().getId();  // get requestId before deleting
			lineItemRepo.deleteById(id);
			updateRequestTotal(requestId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id: " + id);
		}
	}

	private void updateRequestTotal(int requestId) {
		Request request = requestRepo.findById(requestId).orElseThrow(new Supplier<RuntimeException>() {
			public RuntimeException get() {
				return new RuntimeException("Request not found");
			}
		});  // closes the Supplier

		List<LineItem> lineItems = lineItemRepo.findByRequest_Id(requestId);
		BigDecimal total = BigDecimal.ZERO;  // sets total to $0.00 before adding up the line items

		for (LineItem li : lineItems) {
			BigDecimal price = li.getProduct().getPrice();  // product must have price to work
			BigDecimal quantity = BigDecimal.valueOf(li.getQuantity());
			total = total.add(price.multiply(quantity));
		}
		request.setTotal(total);
		requestRepo.save(request);
	}
}
