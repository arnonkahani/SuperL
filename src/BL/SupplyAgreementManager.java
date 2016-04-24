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
	public SupplyAgreementManager(DB db){
		_db=db;
	}

	public void createSupplyAgreement(String supplierID, SupplyType supplyType, ArrayList<Day> supplyDays,
			DelevryType delevryType, ArrayList<Discount> discount, HashMap<String, Float> productMap) throws Exception{

	Supplier sp = _sm.getSupplier(supplierID);
	ArrayList<AgreementProduct> products = new ArrayList<>();
	ArrayList<SupplierProduct> supplierProducts = sp.get_products();
	supplierProducts.removeIf(p -> !productMap.keySet().contains(p.get_serial_number()));
	for (SupplierProduct supplierProduct : supplierProducts) {
		products.add(new AgreementProduct(supplierProduct, productMap.get(supplierProduct.get_serial_number())));
	}
	if(productMap.size() != products.size())
		throw new Exception();
	
	SupplyAgreement sa = new SupplyAgreement(sp, supplyType, supplyDays, delevryType,products);
	_db.insert(sa);
	
	}
	
	public float calculateDiscount(String samID,ArrayList<OrderProduct> orderProducts) throws SQLException {
		float price = 0;
		SupplyAgreement sam = getSupplyAgreement(samID);
		for (OrderProduct product : orderProducts) {
			product.setPrice(calculateDiscount(product,product.getAmount()));
			 price = price + product.getPrice()*product.getAmount();
		}
		return price;
	}
	
	private float calculateDiscount(OrderProduct product, int amount) {
		float discount_price = product.getPrice();
		float original_price = product.getPrice();
		for (Discount discount : product.getAgreementProduct().get_discounts()) {
			if((discount.get_quantity() < amount) 
					&& discount_price < original_price*discount.get_precent())
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
			ArrayList<AgreementProduct> sa = new ArrayList<>();
			return _db.search(sa,search_field,query,AgreementProduct.class);
	}
	
	public ArrayList<SupplyAgreement> searchSupplyAgreement(int[] search_field,String[] query) throws SQLException
	{
			ArrayList<SupplyAgreement> sa = new ArrayList<>();
			return _db.search(sa,search_field,query,SupplyAgreement.class);
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
	
	
}
