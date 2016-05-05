package BE;

import java.util.Date;


import java.util.ArrayList;



public class Order {

	private String orderID;
	private Supplier _supplier;
	private float _weight;
	private Date _date;
	private ArrayList<OrderProduct> _amountProduct = new ArrayList<>();
	private float _price = 0;
	
	public Order(Supplier samID,Date date,ArrayList<OrderProduct> amountProduct,float price) {
		this._supplier = samID;
		this._date = date;
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
	public Date get_date() {
		return _date;
	}
	public void set_date(Date _date) {
		this._date = _date;
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
	
	public String toString(){
		String str;
		str = "Supplier CN: " + get_supplier().get_CN() + "\n"
				+ "Date: " + get_date().toString() + "\n"
				+ "Weight: " +  get_weight() + "\n"
				+ "Price: " + get_price() +"\n"
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

	
}
