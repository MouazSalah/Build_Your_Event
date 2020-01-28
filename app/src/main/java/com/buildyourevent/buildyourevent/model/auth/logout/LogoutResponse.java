package com.buildyourevent.buildyourevent.model.auth.logout;

import com.google.gson.annotations.SerializedName;

public class LogoutResponse{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

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
			"LogoutResponse{" + 
			"message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}