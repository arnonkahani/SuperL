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
	
	public void createProduct(String supId,String producerName,String productName,int weight,int dayOfvalid){
		_db.addProducer(producerName);
		Product pr = new Product(weight,dayOfvalid,productName);
		Supplier sp = _db.getSupplier(supId);
		_db.insertProduct(pr,sp);
		
	}
	
	public Contact createContact(String name,String email,String tel){
		return new Contact(tel, email, name);
		
	}
	
	public Product givesAllSupllierProduct(String id){
		//to-do;
	}
	
	
}
