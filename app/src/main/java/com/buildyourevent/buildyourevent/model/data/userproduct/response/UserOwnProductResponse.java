package com.buildyourevent.buildyourevent.model.data.userproduct.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UserOwnProductResponse{

	@SerializedName("data")
	private List<UserOwnProductData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<UserOwnProductData> data){
		this.data = data;
	}

	public List<UserOwnProductData> getData(){
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
			"UserOwnProductResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}