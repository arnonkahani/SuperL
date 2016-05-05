package BL;

import java.sql.SQLException;
import java.util.ArrayList;

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
		pr.set_category((String)values[4]);
		pr.set_sub_category((String)values[5]);
		pr.set_sub_sub_category((String)values[6]);
		pr.set_min_amount((int)values[7]);
		pr.set_price((float)values[8]);
		SupplierProduct supplierProduct = new SupplierProduct(pr);
		supplierProduct.set_supplier((String)values[4]);
		_db.insert(supplierProduct);
		
	}

	
	public Product getProductByPK(int id,String name,String producer_name,String catagory,
			String sub_catagory,String sub_sub_catagory) throws SQLException {
		String [] values = new String[]{}; 
		return getFromPK(values);
	}
	
	public ArrayList<Product> getAllProducts() throws SQLException{
		return getAll();
	}



	
	
	
	

}
