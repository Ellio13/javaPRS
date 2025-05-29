package com.PRS.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.PRS.db.LineItemRepo;
import com.PRS.db.ProductRepo;
import com.PRS.db.RequestRepo;
import com.PRS.model.LineItem;
import com.PRS.model.LineItemDTO;
import com.PRS.model.Product;
import com.PRS.model.Request;

@CrossOrigin
@RestController
@RequestMapping("/api/LineItems")
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
	public LineItem getById(@PathVariable int id) {
		Optional<LineItem> li = lineItemRepo.findById(id);
		if (li.isPresent()) {
			return li.get();
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id: " + id);
	}
	
	@GetMapping("lines-for-request/{id}")
	public List<LineItem> getLinesForRequest(@PathVariable int id) {

	    if (!requestRepo.existsById(id)) {
	        throw new ResponseStatusException(
	                HttpStatus.NOT_FOUND,
	                "Request not found for id: " + id);
	    }

	    return lineItemRepo.findByRequest_Id(id);
	}
	

	@PostMapping("")
	public LineItem addLineItem(@RequestBody LineItemDTO dto) {

		if (dto.getRequestId() <= 0 || dto.getProductId() <= 0 || dto.getQuantity() <= 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
		}
		
		// Look up Request
		Optional<Request> reqOptional = requestRepo.findById(dto.getRequestId());
		if (!reqOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request not found");
		}
		Request request = reqOptional.get();

		// Look up Product
		Optional<Product> prodOptional = productRepo.findById(dto.getProductId());
		if (!prodOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found");
		}
		Product product = prodOptional.get();

		// Build and save line-item
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

		Optional<LineItem> liOptional = lineItemRepo.findById(id);
		if (!liOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found");
		}
		LineItem existing = liOptional.get();

		// Validate request
		Optional<Request> reqOptional = requestRepo.findById(dto.getRequestId());
		if (!reqOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request not found");
		}
		Request request = reqOptional.get();

		// Validate product
		Optional<Product> prodOptional = productRepo.findById(dto.getProductId());
		if (!prodOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found");
		}
		Product product = prodOptional.get();

		// Update fields
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
			int requestId = li.get().getRequest().getId(); // capture before delete
			lineItemRepo.deleteById(id);
			updateRequestTotal(requestId);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LineItem not found for id: " + id);
		}
	}


	private void updateRequestTotal(int requestId) {

		Optional<Request> reqOptional = requestRepo.findById(requestId);
		if (!reqOptional.isPresent()) {
			// Should never happen; request existence already verified
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request not found");
		}
		Request request = reqOptional.get();

		List<LineItem> lineItems = lineItemRepo.findByRequest_Id(requestId);

		BigDecimal total = BigDecimal.ZERO; // sets total to $0.00 before adding up the line items
		for (LineItem li : lineItems) {
			BigDecimal price = li.getProduct().getPrice();
			if (price == null) {
				price = BigDecimal.ZERO; //prevents NullPointerException if price is null
			}
			BigDecimal quantity = BigDecimal.valueOf(li.getQuantity());
			total = total.add(price.multiply(quantity));
		}

		request.setTotal(total);
		requestRepo.save(request);
	}
}
