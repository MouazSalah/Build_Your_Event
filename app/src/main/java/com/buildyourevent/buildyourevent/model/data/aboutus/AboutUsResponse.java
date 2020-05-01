package com.buildyourevent.buildyourevent.model.data.aboutus;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AboutUsResponse{

	@SerializedName("data")
	private List<AboutUsItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(List<AboutUsItem> data){
		this.data = data;
	}

	public List<AboutUsItem> getData(){
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
			"AboutUsResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}