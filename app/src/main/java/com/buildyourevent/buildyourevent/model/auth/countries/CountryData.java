package com.buildyourevent.buildyourevent.model.auth.countries;

import com.google.gson.annotations.SerializedName;

public class CountryData {

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("country_name")
	private String countryName;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("id")
	private int id;

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setCountryName(String countryName){
		this.countryName = countryName;
	}

	public String getCountryName(){
		return countryName;
	}

	public void setCreatedAt(Object createdAt){
		this.createdAt = createdAt;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"CountryData{" +
			"country_code = '" + countryCode + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",country_name = '" + countryName + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}