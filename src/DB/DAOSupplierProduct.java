package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import BE.Catagory;
import BE.SubCatagory;
import BE.SubSubCatagory;
import BE.SupplierProduct;
import PL.ViewController;

public class DAOSupplierProduct extends DAO<SupplierProduct> {

	DAOProduct _product;
	
	public DAOSupplierProduct(Connection c) {
		super(c);
		_product = new DAOProduct(c);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","SN","SUPPLIERCN","PRODUCT_ID"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","Serial Number","Supplier CN","Product ID"};
	}

	@Override
	public String getTable() {
		return "Supplier_Product";
	}

	@Override
	protected String[] getValues(SupplierProduct object) {
		return new String[]{""+object.get_serial_number(),"'"+object.get_supplier()+"'",""+object.get_id()};
	}

	@Override
	public SupplierProduct getFromPK(String[] values) throws SQLException {
		return search(new int[]{1}, values).get(0);
	}

	@Override
	public void insert(SupplierProduct object) throws SQLException
	{
		try{
			
			_product.insert(object);
			
		}catch(SQLException e)
		{}
		
		
		if(search(new int[]{2,3}, new String[]{"'"+object.get_supplier()+"'",
				""+object.get_id()}).size()!=0)
			throw new SQLException("Already exsists");
		int sn = 0;
		
		 insert(getValues(object),true);
		 sn = getLastAutoID();
		//TODO: Delete
			if(ViewController.debug)
		 System.out.println("SN:" + sn);
		object.set_serial_number(""+sn);
		
	}
	
	@Override
	public SupplierProduct create(ResultSet rs) throws SQLException {
		//TODO: Delete
		if(ViewController.debug)
		System.out.println("DAOSupplierProduct : " + _product.getFromPK(new String[]{""+rs.getInt("PRODUCT_ID")}));
		SupplierProduct supplierProduct = new SupplierProduct(_product.getFromPK(new String[]{""+rs.getInt("PRODUCT_ID")}));
		supplierProduct.set_supplier(rs.getString("SUPPLIERCN"));
		supplierProduct.set_serial_number(""+rs.getInt("SN"));

		return supplierProduct;
	}




}
