package com.buildyourevent.buildyourevent.model.data.carts;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CartResponse{

	@SerializedName("data")
	private List<CartDataItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<CartDataItem> data){
		this.data = data;
	}

	public List<CartDataItem> getData(){
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
			"CartResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}