package com.buildyourevent.buildyourevent.model.data.product;

import com.google.gson.annotations.SerializedName;

public class ProductData {

	@SerializedName("available_quantity")
	private int availableQuantity;

	@SerializedName("image")
	private String image;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("price")
	private String price;

	@SerializedName("owner_id")
	private int ownerId;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("name")
	private String name;

	@SerializedName("cat_id")
	private int catId;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("status")
	private String status;

	public void setAvailableQuantity(int availableQuantity){
		this.availableQuantity = availableQuantity;
	}

	public int getAvailableQuantity(){
		return availableQuantity;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setOwnerId(int ownerId){
		this.ownerId = ownerId;
	}

	public int getOwnerId(){
		return ownerId;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCatId(int catId){
		this.catId = catId;
	}

	public int getCatId(){
		return catId;
	}

	public void setCreatedAt(Object createdAt){
		this.createdAt = createdAt;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ProductData{" +
			"available_quantity = '" + availableQuantity + '\'' + 
			",image = '" + image + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",price = '" + price + '\'' + 
			",owner_id = '" + ownerId + '\'' + 
			",product_id = '" + productId + '\'' + 
			",name = '" + name + '\'' + 
			",cat_id = '" + catId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}