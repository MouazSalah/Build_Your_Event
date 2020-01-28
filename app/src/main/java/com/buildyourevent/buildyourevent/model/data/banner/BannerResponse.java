package com.buildyourevent.buildyourevent.model.data.banner;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BannerResponse{

	@SerializedName("data")
	private List<BannerData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<BannerData> data){
		this.data = data;
	}

	public List<BannerData> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"BannerResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}