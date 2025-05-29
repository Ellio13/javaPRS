package com.PRS.model;

import java.math.BigDecimal;
import java.time.LocalDate;


//"import jakarta.persistence.Column;" - add if mapping to specific column 
//names for other databases

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // primary key, SQL column "ID", calculated internally

	@ManyToOne
	@JoinColumn(name = "UserID")
	private User user; // foreign key to User, string length(20)
	private String requestNumber; // string length(20), calculated internally
	private String description; // string length(100)
	private String justification; // string length(255)
	private LocalDate dateNeeded; // consoleutil has date formatting
	private String deliveryMode; // Mail or pickup, string length(25)
	private String status; // calculated internally, string length(20), NEW, REVIEW, APPROVED, REJECTED
	private BigDecimal total; // 10 numbers, 2 decimal places, calculated internally
	private LocalDate submittedDate; // calculated internally
	private String reasonForRejection;

	public Request() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public LocalDate getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public LocalDate getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", userId=" + (user != null ? user.getId() : "null") + ", requestNumber="
				+ requestNumber + ", description=" + description + ", justification=" + justification + ", dateNeeded="
				+ dateNeeded + ", deliveryMode=" + deliveryMode + ", status=" + status + ", total=" + total
				+ ", submittedDate=" + submittedDate + ", reasonForRejection=" + reasonForRejection + "]";
	}
}
