package com.buildyourevent.buildyourevent.model.data.userproduct.response;

import com.google.gson.annotations.SerializedName;

public class UserOwnProductData{

	@SerializedName("cat_image")
	private String catImage;

	@SerializedName("image")
	private String image;

	@SerializedName("new_available_qty")
	private int newAvailableQty;

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("sub_cat_id")
	private int subCatId;

	@SerializedName("available_date")
	private String availableDate;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("current_stock")
	private int currentStock;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("rate")
	private Object rate;

	@SerializedName("sub_cat_name")
	private String subCatName;

	@SerializedName("price")
	private String price;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("name")
	private String name;

	@SerializedName("cat_id")
	private int catId;

	@SerializedName("status")
	private String status;

	@SerializedName("sub_cat_image")
	private String subCatImage;

	public void setCatImage(String catImage){
		this.catImage = catImage;
	}

	public String getCatImage(){
		return catImage;
	}

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

	public void setCatName(String catName){
		this.catName = catName;
	}

	public String getCatName(){
		return catName;
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

	public void setSubCatName(String subCatName){
		this.subCatName = subCatName;
	}

	public String getSubCatName(){
		return subCatName;
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

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setSubCatImage(String subCatImage){
		this.subCatImage = subCatImage;
	}

	public String getSubCatImage(){
		return subCatImage;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"cat_image = '" + catImage + '\'' + 
			",image = '" + image + '\'' + 
			",new_available_qty = '" + newAvailableQty + '\'' + 
			",cat_name = '" + catName + '\'' + 
			",sub_cat_id = '" + subCatId + '\'' + 
			",available_date = '" + availableDate + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",current_stock = '" + currentStock + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",rate = '" + rate + '\'' + 
			",sub_cat_name = '" + subCatName + '\'' + 
			",price = '" + price + '\'' + 
			",product_id = '" + productId + '\'' + 
			",name = '" + name + '\'' + 
			",cat_id = '" + catId + '\'' + 
			",status = '" + status + '\'' + 
			",sub_cat_image = '" + subCatImage + '\'' + 
			"}";
		}
}