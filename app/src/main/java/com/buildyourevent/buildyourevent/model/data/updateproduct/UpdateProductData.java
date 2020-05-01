package com.buildyourevent.buildyourevent.model.data.updateproduct;

import com.google.gson.annotations.SerializedName;

public class UpdateProductData {

	@SerializedName("image")
	private String image;

	@SerializedName("new_available_qty")
	private int newAvailableQty;

	@SerializedName("owner_id")
	private int ownerId;

	@SerializedName("sub_cat_id")
	private int subCatId;

	@SerializedName("available_date")
	private String availableDate;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("current_stock")
	private int currentStock;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("rate")
	private Object rate;

	@SerializedName("price")
	private String price;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("name")
	private String name;

	@SerializedName("cat_id")
	private int catId;

	@SerializedName("location")
	private String location;

	@SerializedName("status")
	private String status;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setNewAvailableQty(int newAvailableQty){
		this.newAvailableQty = newAvailableQty;
	}

	public int getNewAvailableQty(){
		return newAvailableQty;
	}

	public void setOwnerId(int ownerId){
		this.ownerId = ownerId;
	}

	public int getOwnerId(){
		return ownerId;
	}

	public void setSubCatId(int subCatId){
		this.subCatId = subCatId;
	}

	public int getSubCatId(){
		return subCatId;
	}

	public void setAvailableDate(String availableDate){
		this.availableDate = availableDate;
	}

	public String getAvailableDate(){
		return availableDate;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setCurrentStock(int currentStock){
		this.currentStock = currentStock;
	}

	public int getCurrentStock(){
		return currentStock;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setRate(Object rate){
		this.rate = rate;
	}

	public Object getRate(){
		return rate;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
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

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
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
			"Data{" + 
			"image = '" + image + '\'' + 
			",new_available_qty = '" + newAvailableQty + '\'' + 
			",owner_id = '" + ownerId + '\'' + 
			",sub_cat_id = '" + subCatId + '\'' + 
			",available_date = '" + availableDate + '\'' + 
			",description = '" + description + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",current_stock = '" + currentStock + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",rate = '" + rate + '\'' + 
			",price = '" + price + '\'' + 
			",product_id = '" + productId + '\'' + 
			",name = '" + name + '\'' + 
			",cat_id = '" + catId + '\'' + 
			",location = '" + location + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}