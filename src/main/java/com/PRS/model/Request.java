package com.PRS.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Request {

	@Id
	@Column(name = "ID")
	public int id;  //primary key, SQL column "ID", calculated internally

	@Column(name = "UserID")
	public int userId; //foreign key to User, string length(20)

	@Column(name = "RequestNumber")
	public String requestNumber; //string length(20), calculated internally

	@Column(name = "Description")
	public String description; //string length(100)

	@Column(name = "Justification")
	public String justification; //string length(255)

	@Column(name = "DateNeeded")
	public LocalDate dateNeeded; //consoleutil has date formatting

	@Column(name = "DeliveryMode")
	public String deliveryMode; //Mail or pickup, string length(25)

	@Column(name = "Status")
	public String status; //calculated internally, string length(20), NEW, REVIEW, APPROVED, REJECTED

	@Column(name = "Total")
	public BigDecimal total;  //10 numbers, 2 decimal places, calculated internally

	@Column(name = "SubmittedDate")
	public LocalDate submittedDate; //calculated internally

	@Column(name = "ReasonForRejection")
	public String reasonForRejection;


	public Request() {
		super();
		// TODO Auto-generated constructor stub


	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int i) {
		this.userId = i;
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
		return "Request [id=" + id + ", userId=" + userId + ", requestNumber=" + requestNumber + ", description="
				+ description + ", justification=" + justification + ", dateNeeded=" + dateNeeded + ", deliveryMode="
				+ deliveryMode + ", status=" + status + ", total=" + total + ", submittedDate=" + submittedDate
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}


}
