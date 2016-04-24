package DB;

import java.util.HashMap;

import BE.AgreementProduct;
import BE.Contact;
import BE.Discount;
import BE.Order;
import BE.OrderProduct;
import BE.Producer;
import BE.Product;
import BE.Supplier;
import BE.SupplierProduct;
import BE.SupplyAgreement;

public class DBUtils {
	private HashMap<Class,String[]> search_fields;
	private HashMap<Class,String[]> search_fields_view;
	private HashMap<Class, String> classToTable;
	private HashMap<Class,Class[]> insertType;
	
	public DBUtils()
	{
		createMap();
	}
	
	public HashMap<Class, String[]> getSearch_fields() {
		return search_fields;
	}

	public void setSearch_fields(HashMap<Class, String[]> search_fields) {
		this.search_fields = search_fields;
	}

	public HashMap<Class, String[]> getSearch_fields_view() {
		return search_fields_view;
	}

	public void setSearch_fields_view(HashMap<Class, String[]> search_fields_view) {
		this.search_fields_view = search_fields_view;
	}

	public HashMap<Class, String> getClassToTable() {
		return classToTable;
	}

	public void setClassToTable(HashMap<Class, String> classToTable) {
		this.classToTable = classToTable;
	}

	public String convertToSql(String value,Class type, int i)
	{
		switch(insertType.get(type)[i].getName())
		{
		case "String":
			break;
		case "int":
			break;
			
		}
		return value;
	}
	
	public String genrateInsertSQL(String[] values,String[] columns,String table)
	{
		String sql="INSERT INTO "+ table +" (";
		String values_sql = "";
		String insert_columns = "";
		for (int i = 0; i < values.length; i++) {
			if(i==values.length-1)
				values_sql = values_sql + values[i];
			else
				values_sql = values_sql + values[i] +",";
		}
		for (int i = 0; i < columns.length; i++) {
				if(i== columns.length-1)
					insert_columns = insert_columns + columns[i];
				else
					insert_columns = insert_columns + columns +",";
			
		}
		sql = sql +  " (" + insert_columns + " ) VALUES (" +values_sql + ")";
		return sql;
	}
	private void createMap()
	{
		search_fields = new HashMap<>();
		search_fields_view = new HashMap<>();
		classToTable = new HashMap<>();
		insertType = new HashMap<>();
		
		insertType.put(Supplier.class, new Class[]{});
		insertType.put(Contact.class, new Class[]{});
		insertType.put(Discount.class, new Class[]{});
		insertType.put(Order.class, new Class[]{});
		insertType.put(OrderProduct.class, new Class[]{});
		insertType.put(AgreementProduct.class, new Class[]{});
		insertType.put(Producer.class, new Class[]{});
		insertType.put(Product.class, new Class[]{});
		insertType.put(SupplierProduct.class, new Class[]{});
		insertType.put(SupplyAgreement.class, new Class[]{});
	
		search_fields.put(Product.class,new String[]{"All","PID","PRODUCERNAME","WEIGHT","SHELFLIFE"});
		search_fields_view.put(Product.class,new String[]{"All","Product ID","Producer Name","Weight","Shelf Life"});
		search_fields.put(SupplierProduct.class,new String[]{"All","PRODUCTSUPLLIERSN","PID","PRODUCERNAME","SUPLLIERCN"});
		search_fields_view.put(SupplierProduct.class,new String[]{"All","Product Supllier SN","Product ID","Producer","Company Number"});
		search_fields.put(Supplier.class,new String[]{"All","CN","Name","BANKNUMBER","PAYMENTMETHOD"});
		search_fields_view.put(Supplier.class,new String[]{"All","Company Number","Name","Bank Number","Payment Method"});
		search_fields.put(SupplyAgreement.class,new String[]{"All","SupplyID","SUPLLIERCN","DELEVRYTYPE","SUPPLYTYPE","DAY"});
		search_fields_view.put(SupplyAgreement.class,new String[]{"All","Supply Agreement ID","Company Number","Delevry Type","Supply Type","Days"});
		search_fields.put(Order.class,new String[]{"All","ORDERID","SUPPLYID","SUPPLYTYPE","DAY"});
		search_fields_view.put(Order.class,new String[]{"All","Order ID","Supply Agreement ID","Company Number","Delevry Type","Supply Type","Days"});
		
		classToTable.put(Order.class, "ORDER");
		classToTable.put(Supplier.class, "SUPLLIER");
		classToTable.put(Product.class, "PRODUCT");
	}
}
