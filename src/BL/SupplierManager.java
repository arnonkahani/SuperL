package BL;

import java.sql.SQLException;
import java.util.ArrayList;

import BE.*;
import DB.DB;

public class SupplierManager {
	
	private DB _db;
	
	public SupplierManager(DB db) {
		_db = db;
	}
	
	
	public void createSupplier(String name, String cn, int paymentMethod, String bankNumber, ArrayList<Contact> contacts) throws SQLException{
		Supplier sp = new Supplier(cn,name, paymentMethod, bankNumber);
		sp.set_contacts(contacts);
		_db.insert(sp);
	}
	
	
	public void createProduct(String producerName,String productName,int dayOfvalid,float weight,String supId) throws SQLException{
		Producer producer= new Producer(producerName);
		Product pr = new Product(weight,dayOfvalid,productName);
		pr.set_producer(producer);
		SupplierProduct supplierProduct = new SupplierProduct(pr);
		supplierProduct.set_supplier(supId);
		_db.insert(supplierProduct);
		
	}
	
	public Contact createContact(String name,String email,String tel){
		return new Contact(tel, email, name);
	}
	
	public ArrayList<SupplierProduct> getAllSupllierProduct(String id) throws SQLException{
		return searchProductSupplier(new int[]{4},new String[]{id});
	}
	public ArrayList<SupplierProduct> searchProductSupplier(int search_field[],String query[]) throws SQLException
	{
		
			return _db.search(search_field,query,SupplierProduct.class);
	}
	public ArrayList<Supplier> searchSupplier(int search_field[],String query[]) throws SQLException
	{
			return _db.search(search_field,query,Supplier.class);
	}
	public ArrayList<Producer> searchProducer(int[] search_field,String[] query) throws SQLException
	{
			return _db.search(search_field,query,Producer.class);
	}
	
	public ArrayList<Product> searchProduct(int[] search_field,String[] query) throws SQLException
	{
			
			return _db.search(search_field,query,Product.class);
	}


	public Supplier getSupplier(String supplierID) throws SQLException {
		return searchSupplier(new int[]{1}, new String[]{supplierID}).get(0);
		}


	public String [] getSearchFields(Class be_class) {
		return _db.getSearchFieldView(be_class);
	}
	
	
	
}
