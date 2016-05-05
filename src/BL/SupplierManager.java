package BL;

import java.sql.SQLException;
import java.util.ArrayList;

import BE.*;
import DB.DAOSupplier;

public class SupplierManager extends LogicManager<DAOSupplier>{
	
	
	
	public SupplierManager(DAOSupplier db) {
		super(db);
	}
	
	@Override
	public void create(Object[] values) throws SQLException{
		Supplier sp = new Supplier((String)values[1],(String)values[0], (int)values[2], (String)values[3]);
		sp.set_contacts((ArrayList<Contact>)values[4]);
		sp.set_address((String)values[5]);
		_db.insert(sp);
	}

	public Supplier getSupplierByCN(String cn) throws SQLException{
		return getFromPK(new String[]{cn});
	}
		
	public ArrayList<Supplier> getAllSuplliers() throws SQLException{
		return getAll();
	}
	public ArrayList<SupplierProduct> searchSupplierProduct(int[] fields,String[] values) throws SQLException{
		return search(fields, values);
	}
	public ArrayList<Supplier> searchSupplier(int[] fields,String[] values) throws SQLException{
		return search(fields, values);
	}

	public String[] getSupplierFileds() {
		return getFileds();
	}
	
}
