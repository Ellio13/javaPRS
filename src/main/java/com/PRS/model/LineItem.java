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
	private Product product; //FK to Product, prevents circular reference issues

	private int quantity;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
