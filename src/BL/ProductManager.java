package BL;

import java.sql.SQLException;


import BE.Producer;
import BE.Product;
import BE.SupplierProduct;

import DB.DAOSupplierProduct;


public class ProductManager extends LogicManager<DAOSupplierProduct>{

	
	public ProductManager(DAOSupplierProduct db)
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

	@Override
	public <K> K getFromPK(String[] values) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



	
	
	
	

}
