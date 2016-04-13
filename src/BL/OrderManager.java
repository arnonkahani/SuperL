package BL;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import BE.*;
import DB.Database;

public class OrderManager {
	
	private Database _db;
	private SupplyAgreementManager _sam;
	
	public OrderManager(Database db,SupplyAgreementManager sam) {
		_db = db;
		_sam = sam;
	}

	public void createOrder(String supplyAgreementID,HashMap<String, Integer> amounts,Date date){
		ArrayList<ProductPrice> product_prices = new ArrayList<>();
		product_prices = _sam.getProducts(supplyAgreementID,amounts.keySet());
		for (ProductPrice product : product_prices )
		{
			product.set_amount(amounts.get(product.get_product().get_name()));
		}
		float price = _sam.calculateDiscount(supplyAgreementID, product_prices);
		Order or = new Order(_sam.getSupplyAgreement(supplyAgreementID), date, product_prices,price);
		_db.add(or);
	}
	
	
	public ArrayList<Order> search(int search_field,String query)
	{
			return _db.searchOrder(Order.Search.values()[search_field].columnName,query);
	}
	
}
