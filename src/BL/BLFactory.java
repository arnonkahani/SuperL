package BL;


import java.sql.SQLException;

import DB.DAOFactory;


public class BLFactory {

	DAOFactory _db;
	SupplierLogic _supplierLogic;
	StorageLogic _storageLogic;
	
	public BLFactory(boolean first_time) throws SQLException
	{
		_db = new DAOFactory(first_time);
		_storageLogic = new StorageLogic(_db.createStorageController(), _db.createReportController());
		_supplierLogic = new SupplierLogic(_db, _storageLogic);
		_storageLogic.run(_supplierLogic);
		
	}

	public SupplierLogic getSupplierLogic() {
		return _supplierLogic;
	}

	public StorageLogic getStorageLogic() {
		return _storageLogic;
	}
	
	


	
	
	
}
