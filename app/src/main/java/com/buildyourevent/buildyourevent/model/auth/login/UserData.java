package com.buildyourevent.buildyourevent.model.auth.login;

import com.google.gson.annotations.SerializedName;

public class UserData
{
	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("email")
	private String email;

	@SerializedName("mobile")
	private int mobile;

	@SerializedName("country_name")
	private String countryName;

	@SerializedName("city_name")
	private String cityName;

	@SerializedName("country_id")
	private int countryId;

	@SerializedName("city_id")
	private int cityId;

	@SerializedName("token")
	private String token;

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMobile(int mobile){
		this.mobile = mobile;
	}

	public int getMobile(){
		return mobile;
	}

	public void setCountryName(String countryName){
		this.countryName = countryName;
	}

	public String getCountryName(){
		return countryName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setCountryId(int countryId){
		this.countryId = countryId;
	}

	public int getCountryId(){
		return countryId;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setCityId(int cityId){
		this.cityId = cityId;
	}

	public int getCityId(){
		return cityId;
	}

	@Override
 	public String toString(){
		return 
			"UserData{" +
			"city_name = '" + cityName + '\'' + 
			",name = '" + name + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",country_name = '" + countryName + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",country_id = '" + countryId + '\'' + 
			",token = '" + token + '\'' + 
			",city_id = '" + cityId + '\'' + 
			"}";
		}
}