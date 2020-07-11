package com.nasim.model;

public class Product {
	private String id;
	private String productName;
	private String category;

	private String imagePath;
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", category=" + category 
				+ ", imagePath=" + imagePath + "]";
	}
	
	
}
