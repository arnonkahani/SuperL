package logic;

import java.util.ArrayList;

public class Supplier {

	private String _supID;
	private String _name;
	private int paymentMethod;
	private String bankNumber;
	private ArrayList<Contact> _contacts = new ArrayList<>();
	
	
	public Supplier(String _name,int paymentMethod,String companyNumber){
		this._name=_name;
		this.paymentMethod=paymentMethod;
		this.bankNumber=companyNumber;
	}
	
	public void addContact(Contact c)
	{
		_contacts.add(c);
	}

	public void addContacts(ArrayList<Contact> contacts) {
		for (Contact contact : contacts) {
			_contacts.add(new Contact(contact));
		}
		
	}
	
	
	
	
}
