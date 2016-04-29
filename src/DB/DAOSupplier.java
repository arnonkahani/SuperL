package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import BE.Contact;

import BE.Supplier;
import BE.SupplierProduct;


public class DAOSupplier extends DAO<Supplier> {

	DAOContact _contact;
	DAOSupplierProduct _supplierProduct;
	public DAOSupplier(Connection c) {
		super(c);
		_contact = new DAOContact(c);
		_supplierProduct = new DAOSupplierProduct(c);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","CN","Name","BANKNUMBER","PAYMANETMETHOD"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","Company Number","Name","Bank Number","Payment Method"};
	}

	@Override
	public String getTable() {
		return "Supplier";
	}

	@Override
	protected String[] getValues(Supplier object) {
		return new String[]{"'"+object.get_CN()+"'","'"+object.get_name()+"'",""+object.getPaymentMethod(),"'"+object.getBankNumber()+"'"};
	}

	@Override
	public Supplier getFromPK(String[] values) throws SQLException {
		return search(new int[]{1}, values).get(0);
	}

	@Override
	public void insert(Supplier object) throws SQLException{
		insert(getValues(object),false);
		for (Contact contact : object.get_contacts()) {
			System.out.println("i;m in 4");
			_contact.insert(contact);
		}
		
	}
	
	
	@Override
	public Supplier create(ResultSet rs) throws SQLException {
		Supplier supplier = new Supplier();
		supplier.set_CN(rs.getString("CN"));
		ArrayList<Contact> contacts = _contact.search(new int[]{4}, new String[]{rs.getString("CN")});
		supplier.set_contacts(contacts);
		supplier.set_name(rs.getString("name"));
		supplier.setBankNumber(rs.getString("banknumber"));
		supplier.setPaymentMethod(rs.getInt("PAYMANETMETHOD"));
		ArrayList<SupplierProduct> products = _supplierProduct.search(new int[]{4}, new String[]{supplier.get_CN()});
		supplier.set_products(products);
		
		return supplier;
	}

}
