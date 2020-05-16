package com.buildyourevent.buildyourevent.model.data.subcategory;

import com.google.gson.annotations.SerializedName;

public class SubCategoryData{

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("subcategory_name_ar")
	private String subcategoryNameAr;

	@SerializedName("subcategory_name")
	private String subcategoryName;

	@SerializedName("subcategory_image")
	private String subcategoryImage;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	public SubCategoryData() {
	}

	public SubCategoryData(int categoryId, String updatedAt, String subcategoryNameAr, String subcategoryName, String subcategoryImage, String createdAt, int id) {
		this.categoryId = categoryId;
		this.updatedAt = updatedAt;
		this.subcategoryNameAr = subcategoryNameAr;
		this.subcategoryName = subcategoryName;
		this.subcategoryImage = subcategoryImage;
		this.createdAt = createdAt;
		this.id = id;
	}

	public void setCategoryId(int categoryId){
		this.categoryId = categoryId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setSubcategoryNameAr(String subcategoryNameAr){
		this.subcategoryNameAr = subcategoryNameAr;
	}

	public Object getSubcategoryNameAr(){
		return subcategoryNameAr;
	}

	public void setSubcategoryName(String subcategoryName){
		this.subcategoryName = subcategoryName;
	}

	public String getSubcategoryName(){
		return subcategoryName;
	}

	public void setSubcategoryImage(String subcategoryImage){
		this.subcategoryImage = subcategoryImage;
	}

	public String getSubcategoryImage(){
		return subcategoryImage;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	boolean isSelect = false;

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean select) {
		isSelect = select;
	}


	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"category_id = '" + categoryId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",subcategory_name_ar = '" + subcategoryNameAr + '\'' + 
			",subcategory_name = '" + subcategoryName + '\'' + 
			",subcategory_image = '" + subcategoryImage + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}