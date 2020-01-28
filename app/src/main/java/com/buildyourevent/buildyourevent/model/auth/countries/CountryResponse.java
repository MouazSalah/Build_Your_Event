package com.buildyourevent.buildyourevent.model.auth.countries;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CountryResponse{

	@SerializedName("data")
	private List<CountryData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<CountryData> data){
		this.data = data;
	}

	public List<CountryData> getData(){
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
			"CountryResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}