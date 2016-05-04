package BL;


import DB.DAOFactory;


public class BLFactory {

	DAOFactory _db;
	SupplierFactory _sf;
	
	public BLFactory(boolean first_time)
	{
		_db = new DAOFactory(first_time);
		_sf = new SupplierFactory(_db);
	}

	
	
	
}
