package com.PRS.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	public int id; //primary key, internally generated

	@Column(name = "VendorId")
	public int vendorId; //FK to Vendor, internally generated

	@Column(name = "PartNumber")
	public String partNumber; //string of 50 characters

	@Column(name = "Name")
	public String name; //string of 150 characters

	@Column(name = "Price")
	public BigDecimal price; // 10 digits, 2 decimal places

	@Column(name = "Unit")
	public String unit; // nullable, string of 255 characters

	@Column(name = "PhotoPath")
	public String photoPath; //string of 255 characters

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
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
		return "Product [id=" + id + ", vendorId=" + vendorId + ", partNumber=" + partNumber + ", name=" + name
				+ ", price=" + price + ", unit=" + unit + ", photoPath=" + photoPath + "]";
	}
	public Product() {
		super();
	}
	
	
}
