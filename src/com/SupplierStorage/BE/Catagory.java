package com.SupplierStorage.BE;

public class Catagory {
	String name_cat;
	
	
	public Catagory(String name_cat) {
		this.name_cat = name_cat;
	}
	public String getName_cat() {
		return name_cat;
	}
	public void setName_cat(String name_cat) {
		this.name_cat = name_cat;
	}
	public String toString(){
		return "Name: "  + name_cat ;
	}
}
