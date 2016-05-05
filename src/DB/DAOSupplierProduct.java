package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import BE.SupplierProduct;

public class DAOSupplierProduct extends DAO<SupplierProduct> {

	DAOProduct _product;
	
	public DAOSupplierProduct(Connection c) {
		super(c);
		_product = new DAOProduct(c);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","SN","ProductName","ProductProducerName","SupplierCN","PRODUCT_ID","PRODUCT_CATAGORY","PRODUCT_SUB_CATAGORY"
				,"PRODUCT_SUB_SUB_CATAGORY"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","Serial Number","Product Name","Product's Producer's Name","Supplier CN","Product ID",
				"Product Catagory","Product Sub Catagory","Product Sub Sub Catagory"};
	}

	@Override
	public String getTable() {
		return "Supplier_Product";
	}

	@Override
	protected String[] getValues(SupplierProduct object) {
		return new String[]{""+object.get_serial_number(),"'"+object.get_name()+"'",
				"'"+object.get_producer().getName()+"'","'"+object.get_supplier()+"'",""+object.get_id(),"'"+object.get_category()+"'",
				"'"+object.get_sub_category()+"'","'"+object.get_sub_category()+"'","'"+object.get_sub_sub_category()+"'",
				};
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
		
		
		if(search(new int[]{2,3,4}, new String[]{"'"+object.get_name()+"'",
				"'"+object.get_producer().getName()+"'",object.get_supplier()}).size()!=0)
			throw new SQLException("Already exsists");
		int sn = 0;
		
		 insert(getValues(object),true);
		 sn = getLastAutoID();
		 System.out.println("SN:" + sn);
		object.set_serial_number(""+sn);
		
	}
	
	@Override
	public SupplierProduct create(ResultSet rs) throws SQLException {
		SupplierProduct supplierProduct = new SupplierProduct(_product.getFromPK(new String[]{
				"'"+rs.getString("ProductName")+"'","'"+rs.getString("ProductProducerName")+"'"}));
		supplierProduct.set_serial_number(rs.getString("SN"));
		supplierProduct.set_supplier(rs.getString("SupplierCN"));
		supplierProduct.set_category(rs.getString("PRODUCT_CATAGORY"));
		supplierProduct.set_sub_category(rs.getString("PRODUCT_SUB_CATAGORY"));
		supplierProduct.set_sub_sub_category(rs.getString("PRODUCT_SUB_SUB_CATAGORY"));
		supplierProduct.set_id(rs.getInt("PRODUCT_ID"));
		return supplierProduct;
	}

}
