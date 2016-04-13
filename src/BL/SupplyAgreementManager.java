package BL;


import java.util.ArrayList;

import BE.*;
import DB.Database;

public class SupplyAgreementManager {
	private Database _db;
	
	public SupplyAgreementManager(Database db)
	{
		_db=db;
	}
	public void createSupllyAgreement(String supId,SupplyType supplyType,
			Day day,DelevryType delevryType,ArrayList<Discount> discounts,ArrayList<ProductPrice> products){
		Supplier sp = _db.getSupplier(supId);
		SupplyAgreement sa = new SupplyAgreement(sp, supplyType, day, delevryType,discounts,products);
		_db.insertSupplyAgreement(sa);
		
		
	}
	
	
	public float calculateDiscount(String samID,ArrayList<ProductPrice> amounts) {
		float price = 0;
		SupplyAgreement sam = _db.getSupplyAgreement(samID);
		for (ProductPrice product : amounts) {
			product.set_price(calculateDiscount(product,product.get_amount(),sam.get_discounts()));
			 price = price + product.get_price()*product.get_amount();
		}
		return price;
	}
	
	private float calculateDiscount(ProductPrice pp, int amount,ArrayList<Discount> discounts) {
		float discount_price = pp.get_price();
		float original_price = pp.get_price();
		for (Discount discount : discounts) {
			if(discount.getProduct().equals(pp.get_product()) 
					&& (discount.getAmount() < amount) 
					&& discount_price < original_price*discount.getPrecent())
				discount_price=original_price*discount.getPrecent();
				
		}
		return discount_price;
	}
	
	public SupplyAgreement getSupplyAgreement(String samID) {
		return _db.getSupplyAgreement(samID);
	}

}
