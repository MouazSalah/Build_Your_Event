package com.buildyourevent.buildyourevent.model.auth.cities;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CityResponse{

	@SerializedName("data")
	private List<CityData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<CityData> data){
		this.data = data;
	}

	public List<CityData> getData(){
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
			"CityResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}