package DB;

import java.sql.Connection;

public class DBinsert {
	private Connection _c;
	public DBinsert(Connection c) {
		_c = c;
	}
	public <T> void insert(T or) {
		
		
		
		
		
	}
	/*
	public void insert(String[] values,Class object_class)
	{
		for(;;)
		{
			
		}
		String sql="INSERT INTO "+table +" (";
		String columns = "";
		for (int i = 0; i < class_columns.length; i++) {
			if(i==class_columns.length-1)
				columns = columns + class_columns[i];
			else
				columns = columns + class_columns[i] +",";
			
		}
		sql = sql + columns + " ) VALUES (";
		(NAME) VALUES(" + value_str + ")";
		
		s.executeUpdate(sql);
	}*/
	
	

}
