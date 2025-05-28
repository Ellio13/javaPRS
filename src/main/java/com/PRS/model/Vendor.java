package com.PRS.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Vendor {

	@Id
	@Column(name = "ID")
	public int id; //internally generated

	@Column(name = "Code")
	public String code; //string of 10 characters

	@Column(name = "Name")
	public String name; //string of 255 characters

	@Column(name = "Address")
	public String address; //string of 255 characters

	@Column(name = "City")
	public String city; //string of 255 characters

	@Column(name = "State")
	public String state; //string of 2 characters

	@Column(name = "Zip")
	public String zip; //string of 5 characters

	@Column(name = "PhoneNumber")
	public String phoneNumber; //string of 12 characters

	@Column(name = "Email")
	public String email; //string of 100 characters

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id; 	
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Vendor [id=" + id + ", code=" + code + ", name=" + name + ", address=" + address + ", city=" + city
				+ ", state=" + state + ", zip=" + zip + ", phoneNumber=" + phoneNumber + ", email=" + email + "]";
	}
	public Vendor() {
		super();
	}

} 
