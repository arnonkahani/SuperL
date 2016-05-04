package BL;


import java.sql.SQLException;
import java.util.ArrayList;

import BE.*;
import BE.SupplyAgreement.*;
import DB.DAOSupplyAgreement;
import DB.DAOSupplyAgreementProduct;


public class SupplyAgreementManager extends LogicManager<DAOSupplyAgreement>{
	private AgreementProductManager _apm;
	public SupplyAgreementManager(DAOSupplyAgreement db){
		super(db);

	}
	
	@Override
	public void create(Object[] values) throws SQLException{
	
	Supplier sp = new Supplier();
	sp.set_CN((String)values[0]);
	SupplyAgreement sa = new SupplyAgreement(sp,SupplyType.values()[(int)values[1]], 
			(ArrayList<Day>)values[2], DelevryType.values()[(int)values[3]],(ArrayList<SupplyAgreementProduct>)values[4]);
	_db.insert(sa);
	
	}
	
	public SupplyAgreement getSupplyAgreementByID(String id) throws SQLException{
		return getFromPK(new String[]{id});
	}
	
	public ArrayList<SupplyAgreement> getAllSupplyAgreement() throws SQLException{
		return getAll();
	}
	public SupplyAgreementProduct getSupplyAgreementPrdouctByID_SN(String id,String sn) throws SQLException{
		return _apm.getSupplyAgreementPrdouctByID_SN(id, sn);
	}
	
	public ArrayList<SupplyAgreementProduct> getAllSupplyAgreementProduct() throws SQLException{
		return _apm.getAll();
	}

	public String[] getAllProductsID(String supllyagreement) throws SQLException {
		return _apm.getAllProductsSupplyAgreement(supllyagreement);
	}
	public String[] getAllProductsSN(String SN) throws SQLException {
		return _apm.getAllProductsSN(SN);
	}

	
	public SupplierProduct getCheapestProduct(String producer,String product,int amount) throws SQLException{
		return _apm.getCheapestProduct(producer,product,amount);
	}
	
	
	
	
	class AgreementProductManager extends LogicManager<DAOSupplyAgreementProduct>
	{

		public AgreementProductManager(DAOSupplyAgreementProduct db) {
			super(db);
		}

		public SupplyAgreementProduct getCheapestProduct(String producer, String name, int amount) throws SQLException {
			ArrayList<SupplyAgreementProduct> products = _db.getProductByNameProducer(producer, name);
			if(products.size() == 0)
				return null;
			float min_price = products.get(0).get_price();
			SupplyAgreementProduct min_product = products.get(0);
			for (SupplyAgreementProduct product : products) {
				float discount_price = product.get_price();
				float original_price = product.get_price();
				for (Discount discount : product.get_discounts()) {
					if((discount.get_quantity() < amount) 
							&& discount_price > original_price*discount.get_precent())
						discount_price=original_price*discount.get_precent();
						
				}
				if(discount_price<min_price)
				{
					min_price = discount_price;
					min_product = product;
				}
			}
			return min_product;
			
		}

		@Override
		public void create(Object[] values) throws SQLException {
		}
		
		
		public String[] getAllProductsSN(String SN) throws SQLException {
			ArrayList<SupplyAgreementProduct> products = search(new int[]{2}, new String[]{SN});
			if(products == null || products.size() == 0)
				return new String[]{};
			String [] return_list = new String[products.size()];
			for (int i = 0; i < products.size(); i++) {
				return_list[i] = products.get(i).get_serial_number();
				
			}
			return return_list;
		}
		
		public String[] getAllProductsSupplyAgreement(String supllyagreement) throws SQLException {
			ArrayList<SupplyAgreementProduct> products = search(new int[]{1}, new String[]{supllyagreement});
			if(products == null || products.size() == 0)
				return new String[]{};
			String [] return_list = new String[products.size()];
			for (int i = 0; i < products.size(); i++) {
				return_list[i] = products.get(i).get_serial_number();
				
			}
			return return_list;
		}
		
		public SupplyAgreementProduct getSupplyAgreementPrdouctByID_SN(String id,String sn) throws SQLException{
			return getFromPK(new String[]{id,sn});
		}

		
	}
}
