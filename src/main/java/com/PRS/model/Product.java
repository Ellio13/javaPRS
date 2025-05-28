package com.PRS.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // primary key, internally generated

	@ManyToOne
	@JoinColumn(name = "VendorId")
	private Vendor vendor; // FK to Vendor, internally generated

	private String partNumber; // string of 50 characters

	private String name; // string of 150 characters

	private BigDecimal price; // 10 digits, 2 decimal places

	private String unit; // nullable, string of 255 characters

	private String photoPath; // string of 255 characters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", vendorId=" + (vendor != null ? vendor.getId() : "null") + ", partNumber="
				+ partNumber + ", name=" + name + ", price=" + price + ", unit=" + unit + ", photoPath=" + photoPath
				+ "]";
	}

	public Product() {
		super();
	}
}
