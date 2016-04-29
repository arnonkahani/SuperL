package BE;

import java.util.ArrayList;

public class AgreementProduct {
	private String _sp;
	private SupplierProduct _product;
	private ArrayList<Discount> _discounts;
	private float _price;
	
	
	public AgreementProduct(SupplierProduct product,float price)
	{
		_product = product;
		_price = price;
	}
	
	public AgreementProduct(){
		
	}


	public ArrayList<Discount> get_discounts() {
		return _discounts;
	}

	public void set_discounts(ArrayList<Discount> _discounts) {
		this._discounts = _discounts;
	}

	public SupplierProduct get_product() {
		return _product;
	}


	public void set_product(SupplierProduct _product) {
		this._product = _product;
	}


	public float get_price() {
		return _price;
	}


	public void set_price(float _price) {
		this._price = _price;
	}


	public String get_sp() {
		return _sp;
	}


	public void set_sp(String _sp) {
		this._sp = _sp;
	}

	public String toString(){
		return "Supply ID: " + _sp + " Product SN: " + _product.get_serial_number() + " Prodcut Name: " + _product.get_product().get_name() + " Price: " + _price;
	}



}
