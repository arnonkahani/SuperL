package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import BE.*;

public class DBquery {

private Connection c = null;
private ObjectMaker _objectMaker;
private HashMap<Class,String[]> search_fields;
private HashMap<Class,String[]> search_fields_view;
private HashMap<Class, String> classToTable;


	public DBquery(Connection _c)
	{
		_objectMaker = new ObjectMaker(this);
		c = _c;
		createMap();
		
	}
	public <T> ArrayList<T> search(ArrayList<T> query_result, int[] fileds, String[] query, Class object_class) throws SQLException {
		ResultSet rs = search(fileds,query, object_class);
		while(rs.next()){
			T o = null;
			switch(object_class.getName())
			{
			case "Producer":
				o = (T) _objectMaker.createProducer(rs);
			case "Order":
				o = (T) _objectMaker.createOrder(rs);
			case "Supplier":
				o = (T) _objectMaker.createSupplier(rs);
			}
			query_result.add(o);
		}
		return query_result;
	}
	
	public ResultSet search(int[] search_feild,String[] query,Class object_class){
		Statement s = null;
		ResultSet rs = null;
		try{
			s=c.createStatement();
			String sql="SELECT * FROM "+ classToTable.get(object_class);
			if(search_feild[0]!=0)
				sql = sql + " WHERE " + search_fields.get(object_class)[search_feild[0]] + " = " + query;
			rs = s.executeQuery(sql);
			
		}
		catch(Exception e){
		}
		return rs;
	}
	
	public ResultSet search(String search_feild,String query,String table){
		Statement s = null;
		ResultSet rs = null;
		try{
			s=c.createStatement();
			String sql="SELECT * FROM "+ table;
				sql = sql + " WHERE " + search_feild + " = " + query;
			rs = s.executeQuery(sql);
			
		}
		catch(Exception e){
		}
		return rs;
	}
	
	
	public boolean exist(int[] search_feild,String[] query,Class object_class)
	{
		try {
			return !(search(search_feild,query,object_class)).isBeforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public String[] getSearchFieldsView(Class object_class)
	{
		String [] m = search_fields_view.get(object_class);
		return m;
	}
	private void createMap()
	{
		search_fields = new HashMap<>();
		search_fields_view = new HashMap<>();
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
		classToTable = new HashMap<>();
		classToTable.put(Order.class, "ORDER");
		classToTable.put(Supplier.class, "SUPLLIER");
		classToTable.put(Product.class, "PRODUCT");
	}
	public ResultSet search(int i, String string, Class<Producer> class1) {
		int[] intArr = {i};
		String[] strArr = {string};
		return search(intArr, strArr, class1);
	}

	
	
}
