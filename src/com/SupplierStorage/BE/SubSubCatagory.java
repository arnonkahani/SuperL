package com.SupplierStorage.BE;

public class SubSubCatagory {
	String name_sscat;
	String name_scat;
	String name_catagory;
	
	
	public SubSubCatagory(String name_sscat, String name_scat,String name_catagory) {
		this.name_sscat = name_sscat;
		this.name_scat = name_scat;
		this.name_catagory = name_catagory;
		
	}


	public String getName_sscat() {
		return name_sscat;
	}


	public void setName_sscat(String name_sscat) {
		this.name_sscat = name_sscat;
	}


	public String getName_scat() {
		return name_scat;
	}


	public void setName_scat(String name_scat) {
		this.name_scat = name_scat;
	}
	
	public String getName_catagory() {
		return name_catagory;
	}


	public void setName_catagory(String name_catagory) {
		this.name_catagory = name_catagory;
	}


	public String toString()
	{
		return "Catagory: " + name_catagory + " Sub Catagory Name: " + name_scat + " Sub Sub Catagory Name: " + name_sscat ;
	}
	
	
}



