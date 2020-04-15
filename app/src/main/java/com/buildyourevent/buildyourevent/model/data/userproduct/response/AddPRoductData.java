package com.buildyourevent.buildyourevent.model.data.userproduct.response;

import com.google.gson.annotations.SerializedName;

public class AddPRoductData {

	@SerializedName("image")
	private String image;

	@SerializedName("new_available_qty")
	private String newAvailableQty;

	@SerializedName("owner_id")
	private String ownerId;

	@SerializedName("sub_cat_id")
	private String subCatId;

	@SerializedName("available_date")
	private String availableDate;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("current_stock")
	private String currentStock;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("price")
	private String price;

	@SerializedName("name")
	private String name;

	@SerializedName("cat_id")
	private String catId;

	@SerializedName("location")
	private String location;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setNewAvailableQty(String newAvailableQty){
		this.newAvailableQty = newAvailableQty;
	}

	public String getNewAvailableQty(){
		return newAvailableQty;
	}

	public void setOwnerId(String ownerId){
		this.ownerId = ownerId;
	}

	public String getOwnerId(){
		return ownerId;
	}

	public void setSubCatId(String subCatId){
		this.subCatId = subCatId;
	}

	public String getSubCatId(){
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

	public void setCurrentStock(String currentStock){
		this.currentStock = currentStock;
	}

	public String getCurrentStock(){
		return currentStock;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCatId(String catId){
		this.catId = catId;
	}

	public String getCatId(){
		return catId;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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
			",price = '" + price + '\'' + 
			",name = '" + name + '\'' + 
			",cat_id = '" + catId + '\'' + 
			",location = '" + location + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}