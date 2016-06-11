package com.SupplierStorage.BE;

import java.util.HashMap;

import com.SupplierStorage.BE.SupplyAgreement.Day;

public class WeeklyOrder {
	
	private SupplyAgreement.Day day;
	private HashMap<Product,Integer> products;
	
	public WeeklyOrder(){
		
	}
	
	
	public WeeklyOrder(Day day, HashMap<Product, Integer> products) {
		super();
		this.day = day;
		this.products = products;
	}
	
	public SupplyAgreement.Day getDay() {
		return day;
	}
	public void setDay(SupplyAgreement.Day day) {
		this.day = day;
	}
	public HashMap<Product, Integer> getProducts() {
		return products;
	}
	public void setProducts(HashMap<Product, Integer> products) {
		this.products = products;
	}

}
