package BL;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import BE.*;
import BE.SupplyAgreement.*;
import DB.DB;

public class SupplyAgreementManager {
	private DB _db;
	private SupplierManager _sm;
	public SupplyAgreementManager(DB db,SupplierManager sm){
		_db=db;
		_sm = sm;
	}

	public void createSupplyAgreement(String supplierID, int supplyType, ArrayList<Day> supplyDays,
			int delevryType, ArrayList<AgreementProduct> products) throws Exception{
	
	
	
	Supplier sp = _sm.getSupplier(supplierID);
	
	
	System.out.println("size:" + products.size());
	
	SupplyAgreement sa = new SupplyAgreement(sp, SupplyType.values()[supplyType], supplyDays, DelevryType.values()[delevryType],products);
	_db.insert(sa);
	
	}
	
	public float calculateDiscount(ArrayList<OrderProduct> orderProducts) throws SQLException {
		float price = 0;
		for (OrderProduct product : orderProducts) {
			product.setPrice(calculateDiscount(product,product.getAmount()));
			 price = price + product.getPrice()*product.getAmount();
		}
		return price;
	}
	
	private float calculateDiscount(OrderProduct product, int amount) {
		float discount_price = product.getAgreementProduct().get_price();
		float original_price = product.getAgreementProduct().get_price();
		for (Discount discount : product.getAgreementProduct().get_discounts()) {
			if((discount.get_quantity() < amount) 
					&& discount_price > original_price*discount.get_precent())
				discount_price=original_price*discount.get_precent();
				
		}
		return discount_price;
	}
	
	public SupplyAgreement getSupplyAgreement(String samID) throws SQLException {
		SupplyAgreement sa = searchSupplyAgreement(new int[]{1}, new String[]{samID}).get(0);
		return sa;
	}
	
	public ArrayList<AgreementProduct> getSubsetProducts(String supplyAgreementID, Set<String> set) throws SQLException {
		SupplyAgreement sa = getSupplyAgreement(supplyAgreementID);
		ArrayList<AgreementProduct> list = sa.get_prices();
		list.removeIf(p -> !set.contains(p.get_product().get_serial_number()));
		return list;
	}
	
	public ArrayList<AgreementProduct> getAllProducts() throws SQLException
	{
		return searchAgreementProduct(new int[]{0},new String[]{"All"});
	}
	
	public ArrayList<AgreementProduct> searchAgreementProduct(int[] search_field,String[] query) throws SQLException
	{
			return _db.search(search_field,query,AgreementProduct.class);
	}
	
	public ArrayList<SupplyAgreement> searchSupplyAgreement(int[] search_field,String[] query) throws SQLException
	{
			return _db.search(search_field,query,SupplyAgreement.class);
	}
	
	public Discount createDiscount(String productSN, int amount, Float precent) {
		return new Discount(amount,precent);
		
	}

	public String[] getAllProductsNames(String supllyagreement) throws SQLException {
		SupplyAgreement sa = getSupplyAgreement(supllyagreement);
		ArrayList<AgreementProduct> list = sa.get_prices();
		if(list.size()==0)
			return null;
		String [] return_list = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			return_list[i] = list.get(i).get_product().get_product().get_name();
			
		}
		return return_list;
	}
	public String[] getAllProductsSN(String supllyagreement) throws SQLException {
		SupplyAgreement sa = getSupplyAgreement(supllyagreement);
		ArrayList<AgreementProduct> list = sa.get_prices();
		if(list.size()==0)
			return null;
		String [] return_list = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			return_list[i] = list.get(i).get_product().get_serial_number();
			
		}
		return return_list;
	}

	public String[] getSearchFields(Class be_class) {
		return _db.getSearchFieldView(be_class);
	}

	public ArrayList<SupplyAgreement> getAllSupllyAgreemnt() throws SQLException{
		return _db.search(new int[]{0}, new String[]{""}, SupplyAgreement.class);
	}
	
	
}
