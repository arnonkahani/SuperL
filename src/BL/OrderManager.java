package BL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


import BE.*;
import DB.DB;

public class OrderManager {
	
	private DB _db;
	private SupplyAgreementManager _sam;
	
	public OrderManager(DB db,SupplyAgreementManager sam) {
		_db = db;
		_sam = sam;
	}

	public void createOrder(SupplyAgreement supllyagreement,ArrayList<OrderProduct> product_table,Date date) throws SQLException{
		float price = _sam.calculateDiscount(product_table);
		Order or = new Order(supllyagreement, date, product_table,price);
		_db.insert(or);
	}
	
	
	public ArrayList<Order> search(int[] search_field,String[] query) throws SQLException
	{
	
			return _db.search(search_field,query,Order.class);
	}

	public String[] getFileds() {
		return _db.getSearchFieldView(Order.class);
	}
	
}
