package com.buildyourevent.buildyourevent.model.data.product;

import com.google.gson.annotations.SerializedName;

public class ProductData{

	@SerializedName("available_quantity")
	private int availableQuantity;

	@SerializedName("image")
	private String image;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("sub_cat_name")
	private String subCatName;

	@SerializedName("price")
	private String price;

	@SerializedName("owner_id")
	private int ownerId;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("sub_cat_id")
	private int subCatId;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("status")
	private String status;

	@SerializedName("sub_cat_image")
	private String subCatImage;

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

	public void setSubCatId(int subCatId){
		this.subCatId = subCatId;
	}

	public int getSubCatId(){
		return subCatId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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
			"available_quantity = '" + availableQuantity + '\'' + 
			",image = '" + image + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",sub_cat_name = '" + subCatName + '\'' + 
			",price = '" + price + '\'' + 
			",owner_id = '" + ownerId + '\'' + 
			",product_id = '" + productId + '\'' + 
			",sub_cat_id = '" + subCatId + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",status = '" + status + '\'' + 
			",sub_cat_image = '" + subCatImage + '\'' + 
			"}";
		}
}