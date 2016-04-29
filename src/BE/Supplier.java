package BE;

import java.util.ArrayList;

public class Supplier{
	
	private String _CN;
	private String _name;
	private int paymentMethod;
	private String bankNumber;
	private ArrayList<Contact> _contacts = new ArrayList<>();
	private ArrayList<SupplierProduct> _products = new ArrayList<>();
	
	public Supplier(String _name,String _cn, int paymentMethod,String companyNumber){
		this._name=_name;
		this.paymentMethod=paymentMethod;
		this.bankNumber=companyNumber;
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
			contact.set_supplier(this);
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
		return "CN: " + _CN + " Name: " + _name + " PaymentMethod: " + paymentMethod + " Banknumber: " + bankNumber;
	}
	
}
	