package com.PRS.model;

import jakarta.persistence.*;

@Entity
public class LineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //primary key, internally generated

	@ManyToOne
	@JoinColumn(name = "RequestId")
	private Request request; //FK to Request, internally generated

	@ManyToOne
	@JoinColumn(name = "ProductId")
	private Product product; //prevents circular reference issues

	private int quantity; //number of items requested

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
