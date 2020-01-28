package com.buildyourevent.buildyourevent.model.data.subcategory;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubCategoryResponse
{
	@SerializedName("data")
	private List<SubCategoryData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<SubCategoryData> data){
		this.data = data;
	}

	public List<SubCategoryData> getData(){
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
			"SubCategoryResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}