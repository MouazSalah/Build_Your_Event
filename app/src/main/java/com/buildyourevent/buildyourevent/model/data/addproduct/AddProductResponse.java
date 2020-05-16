package com.buildyourevent.buildyourevent.model.data.addproduct;

import com.google.gson.annotations.SerializedName;

public class AddProductResponse{

	@SerializedName("data")
	private AddProductData addProductData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setAddProductData(AddProductData addProductData){
		this.addProductData = addProductData;
	}

	public AddProductData getAddProductData(){
		return addProductData;
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
			"data = '" + addProductData + '\'' +
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}