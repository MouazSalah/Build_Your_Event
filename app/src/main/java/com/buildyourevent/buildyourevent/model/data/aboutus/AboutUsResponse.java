package com.buildyourevent.buildyourevent.model.data.aboutus;

import com.google.gson.annotations.SerializedName;

public class AboutUsResponse{

	@SerializedName("aboutData")
	private AboutData aboutData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public AboutData getAboutData(){
		return aboutData;
	}

	public String getMessage(){
		return message;
	}

	public int getStatus(){
		return status;
	}
}