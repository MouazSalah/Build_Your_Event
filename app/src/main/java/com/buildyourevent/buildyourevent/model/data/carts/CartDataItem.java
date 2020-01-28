package com.buildyourevent.buildyourevent.model.data.carts;

import com.google.gson.annotations.SerializedName;

public class CartDataItem{

	@SerializedName("cart_id")
	private int cartId;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("product_image")
	private String productImage;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("product_price")
	private String productPrice;

	@SerializedName("product_name")
	private String productName;

	public void setCartId(int cartId){
		this.cartId = cartId;
	}

	public int getCartId(){
		return cartId;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setProductImage(String productImage){
		this.productImage = productImage;
	}

	public String getProductImage(){
		return productImage;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setProductPrice(String productPrice){
		this.productPrice = productPrice;
	}

	public String getProductPrice(){
		return productPrice;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"cart_id = '" + cartId + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",product_image = '" + productImage + '\'' + 
			",product_id = '" + productId + '\'' + 
			",product_price = '" + productPrice + '\'' + 
			",product_name = '" + productName + '\'' + 
			"}";
		}
}