package com.SupplierStorage.BE;

import com.Transpotation.Models.Place;

import java.util.ArrayList;

public class Supplier{
	
	private String _CN;
	private String _address;
	private String _name;
	private int paymentMethod;
	private String bankNumber;
	private ArrayList<Contact> _contacts = new ArrayList<>();
	private ArrayList<SupplierProduct> _products = new ArrayList<>();
	private Place _Place;
	
	public Supplier(String _name,String _cn, int paymentMethod,String bankNumber){
		this._name=_name;
		this.paymentMethod=paymentMethod;
		this.bankNumber=bankNumber;
		_CN = _cn;
	}
	
	public Supplier()
	{
		
	}
	
	public void addContact(Contact c)
	{
		_contacts.add(c);
	}

	public String get_CN() {
		return _CN;
	}

	public void set_CN(String _CN) {
		this._CN = _CN;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public ArrayList<Contact> get_contacts() {
		return _contacts;
	}

	public void set_contacts(ArrayList<Contact> _contacts) {
		for (Contact contact : _contacts) {
			contact.set_supplier_cn(_CN);
		}
		this._contacts = _contacts;
	}

	public ArrayList<SupplierProduct> get_products() {
		return _products;
	}

	public void set_products(ArrayList<SupplierProduct> _products) {
		this._products = _products;
	}


	public String toString(){
		return "CN: " + _CN + " Name: " + _name + " PaymentMethod: " + paymentMethod + " Banknumber: " + bankNumber + " Address: " + _address;
	}
	
	public boolean equals(Object o){
		if(o.getClass().equals(Supplier.class))
		{
			boolean ans = true;
			for (Contact contact : _contacts) {
				ans = ans && ((Supplier)o)._contacts.contains(contact);
			}
			for (SupplierProduct product : _products) {
				ans = ans && ((Supplier)o)._products.contains(product);
			}
			return ans && _CN.toUpperCase().equals(((Supplier)o)._CN) && _name.toUpperCase().equals(((Supplier)o)._name) && bankNumber.toUpperCase().equals(((Supplier)o).bankNumber);
		}
		else
			return false;
	}

	public String get_address() {
		return _address;
	}

	public void set_address(String _address) {
		this._address = _address;
	}

	public Place get_Place() {
		return _Place;
	}
}
	