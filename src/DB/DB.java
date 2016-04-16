package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import BE.Order;
import BE.SupplyAgreement;

public class DB {
	private DBquery _query;
	private DBinsert _insert;
	private Connection _c;
	public DB(){
		try {
		      Class.forName("org.sqlite.JDBC");
		      _c = DriverManager.getConnection("jdbc:sqlite:supllier.db");
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Opened supplier database successfully");
		_query = new DBquery(_c);
		_insert = new DBinsert(_c);
	}
	
	public String[] getSearchFieldView(Class object_class){
		return _query.getSearchFieldsView(object_class);
		
	}
	
	public <T> ArrayList<T> search(ArrayList<T> query_result,int[] fileds,String[] query,Class object_class) throws SQLException{
		return _query.search(query_result,fileds,query,object_class);
	}

	public <T> void insert(T or) {
		_insert.insert(or);
		
	}
	
}
