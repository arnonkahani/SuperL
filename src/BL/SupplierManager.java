package BL;

import java.sql.SQLException;
import java.util.ArrayList;

import BE.*;
import DB.DB;

public class SupplierManager extends LogicManager{
	
	
	
	public SupplierManager(DB db) {
		super(db);
	}
	
	@Override
	public void create(Object[] values) throws SQLException{
		Supplier sp = new Supplier((String)values[1],(String)values[0], (int)values[2], (String)values[3]);
		sp.set_contacts((ArrayList<Contact>)values[4]);
		_db.insert(sp);
	}

	public Supplier getSupplier(String string) throws SQLException {
		return (Supplier) search(new int[]{1}, new String[]{string}, Supplier.class).get(0);
	}
	
	
	
	
	
	
	
	
	



	
	
	
}
