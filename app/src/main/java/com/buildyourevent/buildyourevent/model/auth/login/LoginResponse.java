package com.buildyourevent.buildyourevent.model.auth.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse
{
	@SerializedName("data")
	private UserData userData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setUserData(UserData userData){
		this.userData = userData;
	}

	public UserData getUserData(){
		return userData;
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
			"LoginResponse{" + 
			"userData = '" + userData + '\'' +
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}