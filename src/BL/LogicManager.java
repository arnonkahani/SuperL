package BL;

import java.sql.SQLException;
import java.util.ArrayList;


import DB.DAO;


public abstract class LogicManager<T extends DAO,K> {

	protected T _db;
	
	public LogicManager(T db)
	{
		_db =db;
	}
	
	public abstract void create(K value) throws SQLException;
	
	public ArrayList<K> search(int[] search_field,String[] query) throws SQLException
	{
			return _db.search(search_field,query);
	}
	
	public String[] getFileds() {
		return _db.getSearchFieldsView();
	}
	
	protected K getFromPK(String[] values) throws SQLException{
		return (K)_db.getFromPK(values);
	}
	
	protected  ArrayList<K> getAll() throws SQLException{
		return (ArrayList<K>)_db.getAll();
	}
}
