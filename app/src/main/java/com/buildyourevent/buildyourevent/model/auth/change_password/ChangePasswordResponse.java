package com.buildyourevent.buildyourevent.model.auth.change_password;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponse{

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
			"ChangePasswordResponse{" + 
			"message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}