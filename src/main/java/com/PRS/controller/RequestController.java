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

import com.PRS.db.RequestRepo;
import com.PRS.model.RejectDTO;
import com.PRS.model.Request;
import com.PRS.model.RequestDTO;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")

public class RequestController {

	@Autowired
	private RequestRepo requestRepo;

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

	@GetMapping("list-review/{userId}")
	public List<Request> getRequestsForReview(@PathVariable int userId) {
		return requestRepo.findByStatusAndUserId("REVIEW", userId);
	}


	@PostMapping("")
	public Request addRequest(@RequestBody RequestDTO dto) {
		Request request = convertToRequest(dto);
		return requestRepo.save(request);
	}

	@PutMapping("{id}")
	public Request updateRequest(@PathVariable int id, @RequestBody Request updateRequest) {
		Optional<Request> requestExists = requestRepo.findById(id);
		if (requestExists.isPresent()) {
			Request existingRequest = requestExists.get();
			if ("APPROVED".equalsIgnoreCase(existingRequest.getStatus())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Approved requests cannot be modified");
			}
			updateRequest.setId(id);
			return requestRepo.save(updateRequest);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id); 
		}
	}


	@PutMapping("approve/{id}")
	public Request approveRequest(@PathVariable int id) {
		Optional<Request> requestExists = requestRepo.findById(id);
		if (requestExists.isPresent()) {
			Request request = requestExists.get();
			request.setStatus("APPROVED");
			return requestRepo.save(request);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id); 
		}
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
			requestRepo.deleteById(id);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found for id: " + id);
		}	 
	}


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
		request.setUserId(dto.getUserId());
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


