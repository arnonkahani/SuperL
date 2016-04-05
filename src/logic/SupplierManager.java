package logic;

import java.util.ArrayList;

public class SupplierManager {
	
	private SupplierCollection _sCollection;
	
	public void create(String name, int paymentMethod, String bankNumber, ArrayList<Contact> contacts){
		Supplier sp = new Supplier(name, paymentMethod, bankNumber);
		sp.addContacts(contacts);
		
	}
}
