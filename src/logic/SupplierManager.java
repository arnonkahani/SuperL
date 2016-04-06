package logic;

import java.util.ArrayList;

import database.Database;

public class SupplierManager {
	private Database db = new Database();
	private SupplierCollection _sCollection;
	
	public void create(String name, int paymentMethod, String bankNumber, ArrayList<Contact> contacts){
		Supplier sp = new Supplier(name, paymentMethod, bankNumber);
		sp.addContacts(contacts);
		_sCollection.add(sp);
		db.insert(sp);
	}
	
	public void addProduct(String supId,String proName,int weight,int dayOfvalid){
		
		Product pr = new Product(weight,dayOfvalid,p)
		
	}
}
