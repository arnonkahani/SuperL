package BL;

import java.sql.SQLException;
import java.util.ArrayList;

import BE.Producer;
import BE.Product;
import BE.SupplierProduct;
import DB.DB;

public class ProductManager extends LogicManager{

	
	public ProductManager(DB db)
	{
		super(db);
	}
	
	@Override
	public void create(Object[] values) throws SQLException{
		Producer producer= new Producer((String)values[0]);
		Product pr = new Product((float)values[3],(int)values[2],(String)values[1]);
		pr.set_producer(producer);
		SupplierProduct supplierProduct = new SupplierProduct(pr);
		supplierProduct.set_supplier((String)values[4]);
		_db.insert(supplierProduct);
		
	}

	
	
	
	

}
