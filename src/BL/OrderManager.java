package BL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


import BE.*;
import DB.DB;

public class OrderManager extends LogicManager{
	
	
	private SupplyAgreementManager _sam;
	
	public OrderManager(DB db,SupplyAgreementManager sam) {
		super(db);
		_sam = sam;
	}
	@Override
	public void create(Object[] values) throws SQLException{
		float price = _sam.calculateDiscount((ArrayList<OrderProduct>)values[1]);
		Order or = new Order((SupplyAgreement)values[0],(Date)values[2], (ArrayList<OrderProduct>)values[1],price);
		_db.insert(or);
	}
	
	

	
	
}
