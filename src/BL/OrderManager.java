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

	public void createOrder(String samID,HashMap<String, Integer> amounts){
		ArrayList<ProductPrice> product_prices = new ArrayList<>();
		Date date = new Date();
		for (Map.Entry<String, Integer> a : amounts.entrySet()) {
			product_prices.add(_db.getProductFromAgreement(samID,a.getKey()));
		}
		float price = _sam.calculateDiscount(samID, product_prices);
		Order or = new Order(_sam.getSupplyAgreement(samID), date, product_prices,price);
		_db.insertOrder(or);
	}
	
	
	public Order getOrder(String fields,String orderId){
		Order or = _db.getOrder(orderId);
		return or;
	}
	
	public String printOrder(String orderId){
		Order or = _db.getOrder(orderId);
		String str;
		str = "Supplier CN: " + or.get_samID().get_sup().get_CN() + "\n"
				+ "Date: " + or.get_date().toString() + "\n"
				+ "Weight: " +  or.get_weight() + "\n"
				+ "Price: " + or.get_price() +"\n"
				+ "Product    Amount     Price\n"
				+ "---------------------------- \n";
		String productlist = "";
		for (ProductPrice product : or.get_amountProduct()) {
			productlist = productlist + product.get_product().get_name() + " | " + product.get_amount() + " | " + product.get_price() +"\n";
		}
		return str + productlist;
		
	}
}
