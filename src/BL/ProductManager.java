package BL;

import java.sql.SQLException;
import java.util.ArrayList;

import BE.Catagory;
import BE.Producer;
import BE.Product;
import BE.SubCatagory;
import BE.SubSubCatagory;
import BE.SupplierProduct;
import DB.DAOProduct;
import DB.DAOSupplierProduct;


public class ProductManager extends LogicManager<DAOProduct>{

	SupplierProductManager _spm;
	
	public ProductManager(DAOProduct db)
	{
		super(db);
	}
	
	@Override
	public void create(Object[] values) throws SQLException{
		_spm.create(values);
		
		
	}
	public void createFromProduct(Product pro, String supplierid) throws SQLException {
		_spm.createFromProduct(pro, supplierid);
		
	}
	
	public Product getProductByPK(int id) throws SQLException {
		String [] values = new String[]{""+id}; 
		return getFromPK(values);
	}
	
	public ArrayList<Product> getAllProducts() throws SQLException{
		return getAll();
	}

	public ArrayList<Catagory> getAllCatagory() throws SQLException{
		return _db.getAllCatagory();
	}
	
	public ArrayList<SubCatagory> getAllSubCatagory(String catagory) throws SQLException{
		return _db.getAllSubCatagory(catagory);
	}
	public ArrayList<SubSubCatagory> getAllSubSubCatagory(String catagory,String sub_catagory) throws SQLException{
		return _db.getAllSubSubCatagory(catagory, sub_catagory);
	}

	
	public void setSupplierProductManager(DAOSupplierProduct create) {
	_spm = new SupplierProductManager(create);
	
}

public ArrayList<SupplierProduct> searchSupplierProduct(int[] fields,String[] values) throws SQLException{
	return _spm.search(fields, values);
}
class SupplierProductManager extends LogicManager<DAOSupplierProduct>{

	public SupplierProductManager(DAOSupplierProduct db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create(Object[] values) throws SQLException {
		Producer producer= new Producer((String)values[0]);
		Product pr = new Product((float)values[1],(int)values[2],(String)values[3]);
		pr.set_producer(producer);
		pr.set_category((String)values[4]);
		pr.set_sub_category((String)values[5]);
		pr.set_sub_sub_category((String)values[6]);
		pr.set_min_amount((int)values[7]);
		pr.set_price((float)values[8]);
		SupplierProduct supplierProduct = new SupplierProduct(pr);
		supplierProduct.set_supplier((String)values[9]);
		_db.insert(supplierProduct);
		
	}
	
	public void createFromProduct(Product pro, String supplierid) throws SQLException {
		create(new Object[]{pro.get_producer().getName(),
				pro.get_weight(),pro.get_shelf_life(),pro.get_name(),
				pro.get_category(),pro.get_sub_category(),pro.get_sub_sub_category(),
				pro.get_min_amount(),pro.get_price(),supplierid});
		
	}
	
	
}


	
	
	
	

}
