package com.Common.Models;

import com.SupplierStorage.BE.OrderProduct;
import com.SupplierStorage.BE.Supplier;
import com.SupplierStorage.BE.SupplyAgreement;
import com.Transpotation.Models.Place;

import java.util.Date;


import java.util.ArrayList;



public class Order {
	private String orderID;
	private Supplier _supplier;
	private float _weight;
	private Date _order_date;
	private Date _delevery_date;
	private ArrayList<OrderProduct> _amountProduct = new ArrayList<>();
	private float _price = 0;
	private int sent = 0;
	public Order(Supplier samID,Date date,ArrayList<OrderProduct> amountProduct,float price) {
		this._supplier = samID;
		this._order_date = date;
		for (OrderProduct product : amountProduct) {
			_amountProduct.add(product);
			_weight = _weight + (product.get_weight()*product.getAmount());
		}
		_price = price;
	}
	public Order() {
		
	}
	public Supplier get_supplier() {
		return _supplier;
	}
	public void set_supplier(Supplier _supllier) {
		this._supplier = _supllier;
	}
	public float get_weight() {
		return _weight;
	}
	public void set_weight(float _weight) {
		this._weight = _weight;
	}
	public Date get_order_date() {
		return _order_date;
	}
	public void set_order_date(Date _date) {
		this._order_date = _date;
	}
	public ArrayList<OrderProduct> get_amountProduct() {
		return _amountProduct;
	}
	public void set_amountProduct(ArrayList<OrderProduct> _amountProduct) {
		this._amountProduct = _amountProduct;
	}
	public float get_price() {
		return _price;
	}
	public void set_price(float _price) {
		this._price = _price;
	}
	public Place getPlace(){
		return _supplier.get_Place();
	}
	public String toString(){
		String str;
		String was_sent = "Sent";
		if(sent == 0)
			was_sent = "Not Sent";
		str = "Supplier CN: " + get_supplier().get_CN() + "\n"
				+ "Date: " + get_order_date().toString() + "\n"
				+ "Weight: " +  get_weight() + "\n"
				+ "Price: " + get_price() +"\n"
				+ "Status: " + was_sent +"\n"
				+ "Product    Amount     Price\n"
				+ "---------------------------- \n";
		String productlist = "";
		for (OrderProduct product : get_amountProduct()) {
			productlist = productlist + product.get_name() + " | " + product.getAmount() + " | " + product.getPrice() +"\n";
		}
		
		return str + productlist;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public boolean equals(Object o)
	{
		return o.toString().toUpperCase().equals(toString().toUpperCase());
	}
	public Date get_delevery_date() {
		return _delevery_date;
	}
	public void set_delevery_date(Date _delevery_date) {
		this._delevery_date = _delevery_date;
	}


	public int getSent() {
		return sent;
	}

	public void setSent(int sent) {
		this.sent = sent;
	}


}
