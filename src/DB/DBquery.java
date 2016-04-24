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
private DBUtils _utils;

	public DBquery(Connection _c,DBUtils utils)
	{
		_objectMaker = new ObjectMaker(this);
		_utils = utils;
		c = _c;
	
		
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
			String sql="SELECT * FROM "+ _utils.getClassToTable().get(object_class);
			if(search_feild[0]!=0)
				sql = sql + " WHERE " + _utils.getSearch_fields().get(object_class)[search_feild[0]] + " = " + query;
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
		String [] m = _utils.getSearch_fields_view().get(object_class);
		return m;
	}

	public ResultSet search(int i, String string, Class<Producer> class1) {
		int[] intArr = {i};
		String[] strArr = {string};
		return search(intArr, strArr, class1);
	}

	
	
}
