package com.buildyourevent.buildyourevent.model.data.category;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoryResponse{

	@SerializedName("data")
	private List<CategoryData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<CategoryData> data){
		this.data = data;
	}

	public List<CategoryData> getData(){
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
			"CategoryResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}