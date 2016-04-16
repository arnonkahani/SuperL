package BL;

import java.sql.SQLException;
import java.util.ArrayList;

import BE.*;
import DB.DB;

public class SupplierManager {
	
	private DB _db = new DB();
	
	public SupplierManager(DB db) {
		_db = db;
	}
	
	
	public void createSupplier(String name, int paymentMethod, String bankNumber, ArrayList<Contact> contacts){
		Supplier sp = new Supplier(name, paymentMethod, bankNumber);
		for (Contact contact : contacts) {
			sp.addContact(contact);
		}
		_db.insert(sp);
	}
	
	
	public void createProduct(String producerName,String productName,int dayOfvalid,int weight,String supId){
		Producer producer= new Producer(producerName);
		Product pr = new Product(weight,dayOfvalid,productName);
		pr.set_producer(producer);
		SupplierProduct supplierProduct = new SupplierProduct(pr);
		_db.insert(supplierProduct);
		
	}
	
	public Contact createContact(String name,String email,String tel){
		return new Contact(tel, email, name);
	}
	
	public ArrayList<SupplierProduct> getAllSupllierProduct(String id) throws SQLException{
		return searchProductSupplier(new int[]{2},new String[]{"id"});
	}
	public ArrayList<SupplierProduct> searchProductSupplier(int search_field[],String query[]) throws SQLException
	{
			ArrayList<SupplierProduct> searchResult = new ArrayList<>();
			return _db.search(searchResult,search_field,query,SupplierProduct.class);
	}
	public ArrayList<Supplier> searchSupplier(int search_field[],String query[]) throws SQLException
	{
			ArrayList<Supplier> searchResult = new ArrayList<>();
			return _db.search(searchResult,search_field,query,Supplier.class);
	}
	public ArrayList<Producer> searchProducer(int[] search_field,String[] query) throws SQLException
	{
			ArrayList<Producer> searchResult = new ArrayList<>();
			return _db.search(searchResult,search_field,query,Producer.class);
	}
	
	public ArrayList<Product> searchProduct(int[] search_field,String[] query) throws SQLException
	{
			ArrayList<Product> searchResult = new ArrayList<>();
			return _db.search(searchResult,search_field,query,Product.class);
	}


	public Supplier getSupplier(String supplierID) throws SQLException {
		return searchSupplier(new int[]{1}, new String[]{supplierID}).get(0);
		}
	

	
	
}
