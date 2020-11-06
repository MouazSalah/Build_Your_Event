package com.buildyourevent.buildyourevent.model.data.category;

import com.google.gson.annotations.SerializedName;

public class CategoryData {

	@SerializedName("category_image")
	private String categoryImage;

	@SerializedName("category_name")
	private String categoryName;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

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

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
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

	@Override
	public String toString(){
		return
				"DataItem{" +
						"category_image = '" + categoryImage + '\'' +
						",category_name = '" + categoryName + '\'' +
						",updated_at = '" + updatedAt + '\'' +
						",created_at = '" + createdAt + '\'' +
						",id = '" + id + '\'' +
						"}";
	}
}