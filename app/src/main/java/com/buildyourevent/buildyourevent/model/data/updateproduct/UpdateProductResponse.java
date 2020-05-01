package com.buildyourevent.buildyourevent.model.data.updateproduct;

import com.google.gson.annotations.SerializedName;

public class UpdateProductResponse {

	@SerializedName("data")
	private UpdateProductData updateProductData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setUpdateProductData(UpdateProductData updateProductData){
		this.updateProductData = updateProductData;
	}

	public UpdateProductData getUpdateProductData(){
		return updateProductData;
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
			"UpateProductResponse{" + 
			"data = '" + updateProductData + '\'' +
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}