package com.PRS.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User implements Comparable<User> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "UserName")
	private String userName;

	@Column(name = "Password")
	private String password;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "PhoneNumber")
	private String phoneNumber;

	@Column(name = "Email")
	private String email;

	@Column(name = "Reviewer")
	private boolean reviewer;

	@Column(name = "Admin")
	private boolean admin;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("ID cannot be empty");
		}
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		if (userName == null) {
			throw new IllegalArgumentException("UserName cannot be empty");
		}
		if (userName.length() > 20) {
			throw new IllegalArgumentException("UserName cannot be longer than 20 characters");
		}
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("Password cannot be empty");
		}
		if (password.length() > 10) {
			throw new IllegalArgumentException("Password cannot be longer than 10 characters");
		}
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName == null) {
			throw new IllegalArgumentException("FirstName cannot be empty");
		}
		if (firstName.length() > 20) {
			throw new IllegalArgumentException("FirstName cannot be longer than 20 characters");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName == null) {
			throw new IllegalArgumentException("LastName cannot be empty");
		}
		if (lastName.length() > 20) {
			throw new IllegalArgumentException("LastName cannot be longer than 20 characters");
		}
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		if (phoneNumber == null) {
			throw new IllegalArgumentException("PhoneNumber cannot be empty");
		}
		if (phoneNumber.length() > 12) {
			throw new IllegalArgumentException("PhoneNumber cannot be longer than 12 characters");
		}
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("Email cannot be empty");
		}
		if (email.length() > 75) {
			throw new IllegalArgumentException("Email cannot be longer than 75 characters");
		}
		this.email = email;
	}

	public boolean isReviewer() {
		return reviewer;
	}

	public void setReviewer(boolean reviewer) {
		this.reviewer = reviewer;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", email=" + email + ", reviewer="
				+ reviewer + ", admin=" + admin + "]";
	}

	@Override
	public int compareTo(User user) {
		return this.userName.compareTo(user.getUserName());
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


}