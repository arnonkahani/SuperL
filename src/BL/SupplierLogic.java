package BL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import BE.OrderProduct;
import BE.Product;
import BE.WeeklyOrder;

public class SupplierLogic {

	StorageLogic _storage_logic;
	OrderManager _om;
	public SupplierLogic() throws SQLException
	{
		WeeklyOrder wo = _storage_logic.get_daily_order();
		_storage_logic.getSupply(_om.makeWeekelyOrder(wo));
	}
	
	
	public void supplyOnDemand(HashMap<Product, Integer> products) throws SQLException{
		_storage_logic.getSupply(_om.makeOnDemand(products));
	}
}
