package BL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import BE.*;

public interface SupplierLogic {

	public void makeOnDemandOrder(HashMap<Product,Integer> products) throws SQLException;
	
	public void makeWeeklyOrder(HashMap<Product,Integer> products,SupplyAgreement.Day day) throws SQLException;
	
	public void checkOrders();
	
}
