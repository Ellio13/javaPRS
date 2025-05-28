
package com.PRS.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class RequestDTO {

	
	private int userId; //foreign key to User, string length(20)
	
	private String description; //string length(100)

	private String justification; //string length(255)

	private LocalDate dateNeeded; //consoleutil has date formatting

	private String deliveryMode; //Mail or pickup, string length(25)


	public RequestDTO() {}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	@Override
	public String toString() {
		return "Request [ userId= " + userId +  "description="+ description + ", justification=" + justification + ", dateNeeded=" + dateNeeded + ", deliveryMode="
				+ deliveryMode + "]";
	}
	
}
