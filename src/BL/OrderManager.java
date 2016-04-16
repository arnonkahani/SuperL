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

	public void createOrder(String supplyAgreementID,HashMap<String, Integer> amounts,Date date) throws SQLException{
		ArrayList<AgreementProduct> agreementProducts = new ArrayList<>();
		ArrayList<OrderProduct> orderProducts = new ArrayList<>();
		agreementProducts = _sam.getSubsetProducts(supplyAgreementID, amounts.keySet());
		for (AgreementProduct product : agreementProducts )
		{
			orderProducts.add(new OrderProduct(product, product.get_price(), amounts.get(product.get_product().get_product().get_name())));
		}
		float price = _sam.calculateDiscount(supplyAgreementID, orderProducts);
		Order or = new Order(_sam.getSupplyAgreement(supplyAgreementID), date, orderProducts,price);
		_db.insert(or);
	}
	
	
	public ArrayList<Order> search(int[] search_field,String[] query) throws SQLException
	{
		ArrayList<Order> orderSearch = new ArrayList<>();
			return _db.search(orderSearch,search_field,query,Order.class);
	}

	public String[] getFileds() {
		return _db.getSearchFieldView(Order.class);
	}
	
}
