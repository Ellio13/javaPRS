package com.PRS.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class LineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "RequestId")
	@JsonIgnoreProperties({"lineItems"})
	private Request request;

	@ManyToOne
	@JoinColumn(name = "ProductId")
	@JsonIgnoreProperties({"lineItems"})  //prevents circular reference issues
	private Product product;

	@Column(name = "Quantity")
	private int quantity;

	// Getter and Setter for id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	// Getter and Setter for request
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}

	// Getter and Setter for product
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	// Getter and Setter for quantity
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
