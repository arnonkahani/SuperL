package BL;

import java.sql.SQLException;
import java.util.ArrayList;

import BE.*;
import DB.DAOSupplier;

public class SupplierManager extends LogicManager<DAOSupplier,Supplier>{
	
	
	
	public SupplierManager(DAOSupplier db) {
		super(db);
	}
	
	@Override
	public void create(Supplier value) throws SQLException{
		
		_db.insert(value);
	}

	public Supplier getSupplierByCN(String cn) throws SQLException{
		return getFromPK(new String[]{cn});
	}
		
	public ArrayList<Supplier> getAllSuplliers() throws SQLException{
		return getAll();
	}
	
	public ArrayList<Supplier> searchSupplier(int[] fields,String[] values) throws SQLException{
		return search(fields, values);
	}

	public String[] getSupplierFileds() {
		return getFileds();
	}
	
}
