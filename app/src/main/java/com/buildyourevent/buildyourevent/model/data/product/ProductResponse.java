package com.buildyourevent.buildyourevent.model.data.product;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductResponse{

	@SerializedName("data")
	private List<ProductData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<ProductData> data){
		this.data = data;
	}

	public List<ProductData> getData(){
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
			"ProductResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}