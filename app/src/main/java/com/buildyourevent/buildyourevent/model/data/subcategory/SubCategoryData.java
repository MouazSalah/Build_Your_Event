package com.buildyourevent.buildyourevent.model.data.subcategory;


import com.google.gson.annotations.SerializedName;


public class SubCategoryData {

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("id")
	private int subCatId;

	@SerializedName("subcategory_name")
	private String subcategoryName;

	@SerializedName("subcategory_image")
	private String subcategoryImage;

	@SerializedName("created_at")
	private Object createdAt;


	boolean isSelect = false;

	public void setCategoryId(int categoryId){
		this.categoryId = categoryId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setSubCatId(int subCatId){
		this.subCatId = subCatId;
	}

	public int getSubCatId(){
		return subCatId;
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

	public void setCreatedAt(Object createdAt){
		this.createdAt = createdAt;
	}

	public Object getCreatedAt(){
		return createdAt;
	}


	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean select) {
		isSelect = select;
	}


	@Override
 	public String toString(){
		return 
			"SubCategoryData{" +
			"category_id = '" + categoryId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",sub_cat_id = '" + subCatId + '\'' + 
			",subcategory_name = '" + subcategoryName + '\'' + 
			",subcategory_image = '" + subcategoryImage + '\'' + 
			",created_at = '" + createdAt + '\'' +
					",is_selected = '" + isSelect + '\'' +

					"}";
		}
}