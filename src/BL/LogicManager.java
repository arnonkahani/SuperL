package BL;

import java.sql.SQLException;
import java.util.ArrayList;

import DB.DB;

public abstract class LogicManager {

	protected DB _db;
	
	public LogicManager(DB db)
	{
		_db =db;
	}
	
	public abstract void create(Object[] values) throws SQLException;
	
	public <T> ArrayList<T> search(int[] search_field,String[] query,Class object_class) throws SQLException
	{
			return _db.search(search_field,query,object_class);
	}
	
	public String[] getFileds(Class object_class) {
		return _db.getSearchFieldView(object_class);
	}
}
