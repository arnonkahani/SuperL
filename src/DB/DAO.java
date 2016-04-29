package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;




public abstract class DAO <T>{

	protected Connection _c;
	protected Statement _stm;
	
	public DAO(Connection c)
	{
		_c = c;
	}
	
	public ArrayList<T> search(int [] columns,String[] values) throws SQLException{
		ArrayList<T> result = new ArrayList<>();
		ResultSet rs = genral_search(columns, values);
		if(rs == null)
			return result;
		while(rs.next())
		{
			result.add(create(rs));
		}
		return result;
	}
	
	public void insert(T object) throws SQLException{
		insert(getValues(object),false);
	}
	
	public abstract  String[] getSearchFields();
	
	public abstract  String[] getSearchFieldsView();
	
	public abstract String getTable();
	
	public  ArrayList<T> getAll() throws SQLException{
		return search(new int[]{0}, new String[]{});
	}
	
	protected abstract String[] getValues(T object);
	
	public abstract T getFromPK(String[] values) throws SQLException;
	
	public abstract T create(ResultSet rs) throws SQLException;
	
	protected ResultSet genral_search(int[] search_feild,String[] query){
		ResultSet rs = null;
		try{
			String sql;
			_stm=_c.createStatement();
			if(search_feild[0] == 0)
				sql = "SELECT * FROM " + getTable();
			else{	
			sql="SELECT * FROM "+ getTable();
				sql = sql + " WHERE " + genrateQuery(search_feild,query);}
			sql = sql.toUpperCase();
			rs = _stm.executeQuery(sql);
			
		}
		catch(Exception e){
		}
		return rs;
	}

	private String genrateQuery(int[] columns, String[] values) {
		String sql = "";
		for (int i = 0; i < columns.length - 1; i++) {
			sql = sql + getSearchFields()[columns[i]] + " = " + values[i] + " AND ";
			
		}
		sql = sql + getSearchFields()[columns[columns.length-1]] + " = " + values[columns.length-1];
		return sql;
	}
	
	protected void insert(String[] values,boolean auto_flag) throws SQLException
	{
		_stm = _c.createStatement();
		String sql="INSERT INTO "+getTable() +" (";
		String columns = "";
		String insert_columns = "";
		for (int i = 0; i < values.length; i++) {
			if(auto_flag == true && i == 0)
				continue;
			if(i==values.length-1)
				columns = columns + values[i];
			else
				columns = columns + values[i] +",";
		}
		for (int i = 1; i < getSearchFields().length; i++) {
			if(auto_flag == true && i == 1)
				continue;
				if(i==getSearchFields().length-1)
					insert_columns = insert_columns +getSearchFields()[i];
				else
					insert_columns = insert_columns + getSearchFields()[i] +",";
			
		}
		sql = sql + insert_columns + ") VALUES (" +columns + ")";
		sql = sql.toUpperCase();
		
		
		_stm.executeUpdate(sql);
		System.out.println("Added to: " + getTable());
		
	}
	
	protected int getLastAutoID() throws SQLException{
		ResultSet rs = _stm.getGeneratedKeys();
	    rs.next();
	    return rs.getInt(1);
	}
	
}
