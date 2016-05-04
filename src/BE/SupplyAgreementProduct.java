package BE;

import java.util.ArrayList;

public class SupplyAgreementProduct extends SupplierProduct{
	private String _sp;
	private ArrayList<Discount> _discounts;
	private float _price;
	
	
	public SupplyAgreementProduct(SupplierProduct product,float price)
	{
		super(product);
		_price = price;
	}
	
	public SupplyAgreementProduct(SupplyAgreementProduct agreementProduct) {
		super(agreementProduct);
		_sp = agreementProduct._sp;
		_price = agreementProduct._price;
		_discounts = agreementProduct._discounts;
	}

	public SupplyAgreementProduct(SupplierProduct sp) {
		super(sp);
	}

	public ArrayList<Discount> get_discounts() {
		return _discounts;
	}

	public void set_discounts(ArrayList<Discount> _discounts) {
		this._discounts = _discounts;
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
		return "Supply ID: " + _sp + " Product SN: " + get_serial_number() + " Prodcut Name: " + get_name() + " Price: " + _price;
	}

	public boolean equals(Object o){
		return o.toString().toUpperCase().equals(toString().toUpperCase());
	}

}
