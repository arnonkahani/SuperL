package BL;

import java.sql.SQLException;
import java.util.ArrayList;

import BE.Catagory;
import BE.Producer;
import BE.Product;
import BE.SubCatagory;
import BE.SubSubCatagory;
import BE.SupplierProduct;
import BE.SupplyAgreementProduct;
import DB.DAOProduct;
import DB.DAOSupplierProduct;


public class ProductManager extends LogicManager<DAOProduct,Product>{

	SupplierProductManager _spm;
	
	public ProductManager(DAOProduct db)
	{
		super(db);
	}
	
	@Override
	public void create(Product value) throws SQLException{
		_db.insert(value);
		
		
		
		
	}
	public void createSupplyProduct(Product pro, String supplierid) throws SQLException {
		SupplierProduct supplierProduct = new SupplierProduct(pro);
		supplierProduct.set_supplier(supplierid);
		_spm.create(supplierProduct);
		
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
class SupplierProductManager extends LogicManager<DAOSupplierProduct,SupplierProduct>{

	public SupplierProductManager(DAOSupplierProduct db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create(SupplierProduct value) throws SQLException {
		_db.insert(value);
		
	}
		
	
}


	
	
	
	

}
