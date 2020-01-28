package com.buildyourevent.buildyourevent.model.data.productdetails;

import com.google.gson.annotations.SerializedName;

public class ProductDetailsResponse{

	@SerializedName("data")
	private ProductDetailsData data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(ProductDetailsData data){
		this.data = data;
	}

	public ProductDetailsData getData(){
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
			"ProductDetailsResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}