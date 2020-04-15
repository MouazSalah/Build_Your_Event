package com.buildyourevent.buildyourevent.model.data.userproduct.response;

import com.google.gson.annotations.SerializedName;

public class AddProductResponse{

	@SerializedName("data")
	private AddPRoductData addPRoductData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setAddPRoductData(AddPRoductData addPRoductData){
		this.addPRoductData = addPRoductData;
	}

	public AddPRoductData getAddPRoductData(){
		return addPRoductData;
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
			"AddProductResponse{" + 
			"data = '" + addPRoductData + '\'' +
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}