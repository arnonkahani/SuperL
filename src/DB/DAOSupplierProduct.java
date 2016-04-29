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
		return new String[]{"All","SN","ProductName","ProductProducerName","SupplierCN"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","Serial Number","Product Name","Product's Producer's Name","Supplier CN"};
	}

	@Override
	public String getTable() {
		return "Supplier_Product";
	}

	@Override
	protected String[] getValues(SupplierProduct object) {
		return new String[]{""+object.get_serial_number(),"'"+object.get_product().get_name()+"'",
				"'"+object.get_product().get_producer().getName()+"'","'"+object.get_supplier()+"'"};
	}

	@Override
	public SupplierProduct getFromPK(String[] values) throws SQLException {
		return search(new int[]{1}, values).get(0);
	}

	@Override
	public void insert(SupplierProduct object) throws SQLException
	{
		try{
			System.out.println("stage 1");
			_product.insert(object.get_product());
			System.out.println("stage 1 over");
		}catch(SQLException e)
		{}
		//TODO:fix
		
		if(search(new int[]{2,3,4}, new String[]{"'"+object.get_product().get_name()+"'",
				"'"+object.get_product().get_producer().getName()+"'",object.get_supplier()}).size()!=0)
			throw new SQLException("Already exsists");
		int sn = 0;
		
		 insert(getValues(object),true);
		 sn = getLastAutoID();
		 System.out.println("SN:" + sn);
		object.set_serial_number(""+sn);
		
	}
	
	@Override
	public SupplierProduct create(ResultSet rs) throws SQLException {
		SupplierProduct supplierProduct = new SupplierProduct();
		supplierProduct.set_serial_number(rs.getString("SN"));
		supplierProduct.set_supplier(rs.getString("SupplierCN"));
		supplierProduct.set_product(_product.getFromPK(new String[]{
				"'"+rs.getString("ProductName")+"'","'"+rs.getString("ProductProducerName")+"'"}));
		return supplierProduct;
	}

}
