package com.PRS.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.PRS.db.LineItemRepo;
import com.PRS.db.RequestRepo;
import com.PRS.db.UserRepo;
import com.PRS.model.LineItem;
import com.PRS.model.RejectDTO;
import com.PRS.model.Request;
import com.PRS.model.RequestDTO;
import com.PRS.model.User;

@CrossOrigin
@RestController
@RequestMapping("/api/Requests")

public class RequestController {

	@Autowired
	private RequestRepo requestRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private LineItemRepo lineItemRepo;

	@GetMapping("/")
	public List<Request> getAll() {
		return requestRepo.findAll();
	}


	@GetMapping("{id}")
	public Request getById(@PathVariable int id) {
		Optional<Request> requestExists = requestRepo.findById(id);
		if (requestExists.isPresent()) { 
			return requestExists.get();
		}
		else {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
		}
	}

	
	//function in repo to find requests by status and entered user id != userId in Request
	@GetMapping("list-review/{userId}")
	public List<Request> getRequestsForReview(@PathVariable int userId) {
		return requestRepo.findByStatusAndUser_IdNot("REVIEW", userId);
	}

	
	@PostMapping("")
	public Request addRequest(@RequestBody RequestDTO dto) {
	    Request request = convertToRequest(dto);
	    return requestRepo.save(request);
	}

	@PutMapping("{id}")   // uses DTO to update request
	public Request updateRequest(@PathVariable int id, @RequestBody RequestDTO dto) {

	    Optional<Request> requestExists = requestRepo.findById(id);
	    if (!requestExists.isPresent()) {
	        throw new ResponseStatusException(
	                HttpStatus.NOT_FOUND,
	                "Request not found for id: " + id);
	    }
	    Request existingRequest = requestExists.get();

	    if ("APPROVED".equalsIgnoreCase(existingRequest.getStatus())) {
	        throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST,
	                "Approved requests cannot be modified");
	    }
	    if ("REJECTED".equalsIgnoreCase(existingRequest.getStatus())) {
	        throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST,
	                "Rejected requests cannot be modified");
	    }

	    existingRequest.setDescription(dto.getDescription());
	    existingRequest.setJustification(dto.getJustification());
	    existingRequest.setDateNeeded(dto.getDateNeeded());
	    existingRequest.setDeliveryMode(dto.getDeliveryMode());


	    if (dto.getUserId() > 0) {
	        Optional<User> userExists = userRepo.findById(dto.getUserId());
	        if (!userExists.isPresent()) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id: " + dto.getUserId());
	        }
	        User user = userExists.get();       // gets full user object with fields populated
	        existingRequest.setUser(user);      // add the user object to the request
	    }

	    return requestRepo.save(existingRequest);
	}

	@PutMapping("submit-review/{id}")
	public Request setStatus(@PathVariable int id) {
		Optional<Request> requestExists = requestRepo.findById(id);
		if (requestExists.isPresent()) {
			Request request = requestExists.get();
			if (request.getTotal().compareTo(new BigDecimal("50")) <= 0)
			{
				request.setStatus("APPROVED");
			}
			else {
				request.setStatus("REVIEW");
			}
			return requestRepo.save(request); }
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}

	
	//approve request
	@PutMapping("approve/{id}")
	public Request approveRequest(@PathVariable int id) {
	    Request request = requestRepo.findById(id)
	        .orElseThrow(() -> new ResponseStatusException(
	            HttpStatus.NOT_FOUND, "Request not found for id: " + id));

	    if ("APPROVED".equalsIgnoreCase(request.getStatus())) {
	        throw new ResponseStatusException(
	            HttpStatus.BAD_REQUEST, "Request already approved");
	    }
	    
	    if ("REJECTED".equalsIgnoreCase(request.getStatus())) {
	        throw new ResponseStatusException(
	            HttpStatus.BAD_REQUEST, "Rejected requests cannot be approved");
	    }
	    
	    if("NEW".equalsIgnoreCase(request.getStatus())) {
	    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request cannot be approved in current status: " + request.getStatus());
	    }
	    else if ("REVIEW".equalsIgnoreCase(request.getStatus())) {
	        request.setStatus("APPROVED");
	    }
	    else {
	    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request cannot be approved in current status: " + request.getStatus());
	    }

	    request.setStatus("APPROVED");  
	    return requestRepo.save(request);
	}


	//"reason for rejection" is the only required field when using RejectDTO
	@PutMapping("reject/{id}")
	public Request rejectRequest(@PathVariable int id, @RequestBody RejectDTO dto) {
		Optional<Request> requestExists = requestRepo.findById(id);
		if (requestExists.isPresent()) {
			Request request = requestExists.get();
			request.setStatus("REJECTED");
			request.setReasonForRejection(dto.getReason());

			return requestRepo.save(request);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id); 
		}

	}

	@DeleteMapping("{id}")
	public void deleteRequest(@PathVariable int id) {
		Optional <Request> r = requestRepo.findById(id);
		if (r.isPresent()) {
			 List<LineItem> items = lineItemRepo.findByRequest_Id(id);
			    if (!items.isEmpty()) {
			        lineItemRepo.deleteAll(items);   //deletes line items connected to request
			    }

			requestRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
		}	 
	}


	// Generates a request number in the format RYYYYMMDDXXXX 
	//where YYYYMMDD is the current date and XXXX is a sequence number that resets daily
	private String generateRequestNumber() {
		Request latestRequest = requestRepo.findTopByOrderByRequestNumberDesc(); //method in repo
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String datePart = currentDate.format(formatter);

		int sequence = 1;

		if (latestRequest != null) {
			String lastRequestNumber = latestRequest.getRequestNumber();
			String lastDatePart = lastRequestNumber.substring(1, 9);
			String lastFourDigits = lastRequestNumber.substring(9);

			if (lastDatePart.equals(datePart)) {
				sequence = Integer.parseInt(lastFourDigits) + 1;
			}
		}

		String formatted = String.format("%04d", sequence);  //ensures 4 digits with leading zeros
		return "R" + datePart + formatted;
	}


	//takes values from DTO and puts into request fields of request object
	private Request convertToRequest(RequestDTO dto) {
		Request request = new Request();
		User user = new User();
		user.setId(dto.getUserId());  //sets user id from dto to user object
		request.setUser(user);  //set user object to request
		request.setDescription(dto.getDescription());
		request.setJustification(dto.getJustification());
		request.setDateNeeded(dto.getDateNeeded());
		request.setDeliveryMode(dto.getDeliveryMode());

		request.setStatus("NEW");
		request.setSubmittedDate(LocalDate.now());
		request.setTotal(BigDecimal.ZERO);  //sets total to 0.00 as starting value
		request.setRequestNumber(generateRequestNumber());


		return request;
	}
}


