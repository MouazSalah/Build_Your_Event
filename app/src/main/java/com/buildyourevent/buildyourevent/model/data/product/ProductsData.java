package com.buildyourevent.buildyourevent.model.data.product;

import com.google.gson.annotations.SerializedName;

public class ProductsData{

	@SerializedName("cat_image")
	private String catImage;

	@SerializedName("image")
	private String image;

	@SerializedName("new_available_qty")
	private int newAvailableQty;

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("owner_id")
	private int ownerId;

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

	public String getCatImage(){
		return catImage;
	}

	public String getImage(){
		return image;
	}

	public int getNewAvailableQty(){
		return newAvailableQty;
	}

	public String getCatName(){
		return catName;
	}

	public int getOwnerId(){
		return ownerId;
	}

	public int getSubCatId(){
		return subCatId;
	}

	public String getAvailableDate(){
		return availableDate;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getCurrentStock(){
		return currentStock;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public Object getRate(){
		return rate;
	}

	public String getSubCatName(){
		return subCatName;
	}

	public String getPrice(){
		return price;
	}

	public int getProductId(){
		return productId;
	}

	public String getName(){
		return name;
	}

	public int getCatId(){
		return catId;
	}

	public String getStatus(){
		return status;
	}

	public String getSubCatImage(){
		return subCatImage;
	}
}