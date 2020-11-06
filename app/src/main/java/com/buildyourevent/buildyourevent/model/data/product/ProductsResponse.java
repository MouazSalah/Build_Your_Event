package com.buildyourevent.buildyourevent.model.data.product;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProductsResponse{

	@SerializedName("data")
	private List<ProductsData> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public List<ProductsData> getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}