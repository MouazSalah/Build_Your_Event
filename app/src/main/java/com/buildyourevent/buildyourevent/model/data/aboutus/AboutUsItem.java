package com.buildyourevent.buildyourevent.model.data.aboutus;

import com.google.gson.annotations.SerializedName;

public class AboutUsItem {

	@SerializedName("image")
	private String image;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("text")
	private String text;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"image = '" + image + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",text = '" + text + '\'' + 
			"}";
		}
}