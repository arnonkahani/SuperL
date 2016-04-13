package BL;

import java.util.ArrayList;

import BE.*;
import DB.Database;

public class SupplierManager {
	
	private Database _db = new Database();
	
	public SupplierManager(Database db) {
		_db = db;
	}
	
	public ArrayList<Product> getProducts(String supID)
	{
		Supplier sp = _db.getSupplier(supID);
		return sp.get_products();
	}
	
	public void createSupplier(String name, int paymentMethod, String bankNumber, ArrayList<Contact> contacts){
		Supplier sp = new Supplier(name, paymentMethod, bankNumber);
		for (Contact contact : contacts) {
			sp.addContact(contact);
		}
		_db.insertSupplier(sp);
	}
	
	
	public void createProduct(String producerName,String productName,int dayOfvalid,int weight,String supId){
		_db.addProducer(producerName);
		Product pr = new Product(weight,dayOfvalid,productName);
		Supplier sp = _db.getSupplier(supId);
		_db.insertProduct(pr,sp);
	}
	
	public Contact createContact(String name,String email,String tel){
		return new Contact(tel, email, name);
	}
	
	public ArrayList<Product> getAllSupllierProduct(String id){
		Supplier sp=_db.getSupplier(id);
		return sp.get_products();
	}

	public void searchSupplier(String supID){
		Supplier sp=_db.getSupplier(supID);
	}
	
	public void searchProduct(String Pid){
		Product pd=_db.getProduct(Pid);
	}
	
	
}
