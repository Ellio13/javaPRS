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
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
		}
	}

	// list all REVIEW requests except those created by the given user
	@GetMapping("list-review/{userId}")
	public List<Request> getRequestsForReview(@PathVariable int userId) {
		return requestRepo.findByStatusAndUser_IdNot("REVIEW", userId);
	}

	@PostMapping("")
	public Request addRequest(@RequestBody RequestDTO dto) {

		User user = userRepo.findById(dto.getUserId()) // get real row
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

		Request request = convertToRequest(dto); // build rest of info
		request.setUser(user); // ← swap the stub user for the real one

		Request saved = requestRepo.save(request);
		return saved; // JSON now has user
	}

	@PutMapping("{id}")
	public Request updateRequest(@PathVariable int id, @RequestBody RequestDTO dto) {

		Optional<Request> requestExists = requestRepo.findById(id);
		if (!requestExists.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
		}
		Request existingRequest = requestExists.get();

		if ("APPROVED".equalsIgnoreCase(existingRequest.getStatus())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Approved requests cannot be modified");
		}
		if ("REJECTED".equalsIgnoreCase(existingRequest.getStatus())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rejected requests cannot be modified");
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
			User user = userExists.get();
			existingRequest.setUser(user);
		}

		return requestRepo.save(existingRequest);
	}

	@PutMapping("submit-review/{id}")
	public Request setStatus(@PathVariable int id) {
		Optional<Request> requestExists = requestRepo.findById(id);
		if (requestExists.isPresent()) {
			Request request = requestExists.get();
			if (request.getTotal().compareTo(new BigDecimal("50")) <= 0) {
				request.setStatus("APPROVED");
			} else {
				request.setStatus("REVIEW");
			}
			return requestRepo.save(request);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id " + id);
		}
	}

	@PutMapping("approve/{id}")
	public Request approveRequest(@PathVariable int id) {
		Optional<Request> requestExists = requestRepo.findById(id);
		if (requestExists.isPresent()) {
			Request request = requestExists.get();
			request.setStatus("APPROVED");
			return requestRepo.save(request);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
		}
	}
	
	// reject request
	@PutMapping("reject/{id}")
	public Request rejectRequest(@PathVariable int id, @RequestBody RejectDTO dto) {
		Optional<Request> requestExists = requestRepo.findById(id);
		if (requestExists.isPresent()) {
			Request request = requestExists.get();
			request.setStatus("REJECTED");
			request.setReasonForRejection(dto.getReason());
			return requestRepo.save(request);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
		}
	}

	@DeleteMapping("{id}")
	public void deleteRequest(@PathVariable int id) {
		Optional<Request> r = requestRepo.findById(id);
		if (r.isPresent()) {
			List<LineItem> items = lineItemRepo.findByRequest_Id(id);
			if (!items.isEmpty()) {
				lineItemRepo.deleteAll(items);
			}
			requestRepo.deleteById(id);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
		}
	}

	// Generates a request number like RYYYYMMDDXXXX
	private String generateRequestNumber() {
		Request latestRequest = requestRepo.findTopByOrderByRequestNumberDesc();
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
		return "R" + datePart + String.format("%04d", sequence);
	}

	// build entity from DTO
	private Request convertToRequest(RequestDTO dto) {
		Request request = new Request();

		User user = new User();
		user.setId(dto.getUserId()); // only id set
		request.setUser(user);

		request.setDescription(dto.getDescription());
		request.setJustification(dto.getJustification());
		request.setDateNeeded(dto.getDateNeeded());
		request.setDeliveryMode(dto.getDeliveryMode());

		request.setStatus("NEW");
		request.setSubmittedDate(LocalDate.now());
		request.setTotal(BigDecimal.ZERO);
		request.setRequestNumber(generateRequestNumber());
		return request;
	}
}
