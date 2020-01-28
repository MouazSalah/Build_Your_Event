package com.buildyourevent.buildyourevent.model.data.category;

import com.google.gson.annotations.SerializedName;

public class CategoryData {

	@SerializedName("category_image")
	private String categoryImage;

	@SerializedName("category_name")
	private String categoryName;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("id")
	private int id;

	public void setCategoryImage(String categoryImage){
		this.categoryImage = categoryImage;
	}

	public String getCategoryImage(){
		return categoryImage;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setCreatedAt(Object createdAt){
		this.createdAt = createdAt;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"CategoryData{" +
			"category_image = '" + categoryImage + '\'' + 
			",category_name = '" + categoryName + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}