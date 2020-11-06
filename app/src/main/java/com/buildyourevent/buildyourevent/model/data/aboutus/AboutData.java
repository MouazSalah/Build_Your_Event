package com.buildyourevent.buildyourevent.model.data.aboutus;

import com.google.gson.annotations.SerializedName;

public class AboutData {

	@SerializedName("image")
	private String image;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("text")
	private String text;

	public String getImage(){
		return image;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getText(){
		return text;
	}
}