package com.buildyourevent.buildyourevent.model.data.productrate;

import com.buildyourevent.buildyourevent.model.data.productrate.ProductRateData;
import com.google.gson.annotations.SerializedName;

public class ProductRateResponse{

	@SerializedName("data")
	private ProductRateData productRateData;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setProductRateData(ProductRateData productRateData){
		this.productRateData = productRateData;
	}

	public ProductRateData getProductRateData(){
		return productRateData;
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
			"ProductRateResponse{" + 
			"data = '" + productRateData + '\'' +
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}