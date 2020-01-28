package com.buildyourevent.buildyourevent.model.auth.code;

import com.google.gson.annotations.SerializedName;

public class VerifyCodeResponse{

	@SerializedName("data")
	private VerifyCodeData data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(VerifyCodeData data){
		this.data = data;
	}

	public VerifyCodeData getData(){
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
			"VerifyCodeResponse{" + 
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}