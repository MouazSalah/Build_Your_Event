package com.buildyourevent.buildyourevent.model.data.banner;

import com.google.gson.annotations.SerializedName;

public class BannerData {

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("banner_id")
	private int bannerId;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("banner_image")
	private String bannerImage;

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setBannerId(int bannerId){
		this.bannerId = bannerId;
	}

	public int getBannerId(){
		return bannerId;
	}

	public void setCreatedAt(Object createdAt){
		this.createdAt = createdAt;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public void setBannerImage(String bannerImage){
		this.bannerImage = bannerImage;
	}

	public String getBannerImage(){
		return bannerImage;
	}

	@Override
 	public String toString(){
		return 
			"BannerData{" +
			"updated_at = '" + updatedAt + '\'' + 
			",banner_id = '" + bannerId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",banner_image = '" + bannerImage + '\'' + 
			"}";
		}
}