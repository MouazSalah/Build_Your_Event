package com.buildyourevent.buildyourevent.model.auth.cities;

import com.google.gson.annotations.SerializedName;

public class CityData {

	@SerializedName("city_name")
	private String cityName;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("country_id")
	private int countryId;

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
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

	public void setCountryId(int countryId){
		this.countryId = countryId;
	}

	public int getCountryId(){
		return countryId;
	}

	@Override
 	public String toString(){
		return 
			"CountryData{" +
			"city_name = '" + cityName + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",country_id = '" + countryId + '\'' + 
			"}";
		}
}