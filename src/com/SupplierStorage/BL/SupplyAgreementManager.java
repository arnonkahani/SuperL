package com.SupplierStorage.BL;


import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.Common.IWorkers;
import com.SupplierStorage.BE.*;
import com.SupplierStorage.BE.SupplyAgreement.*;
import com.SupplierStorage.DB.DAOSupplyAgreement;
import com.SupplierStorage.DB.DAOSupplyAgreementProduct;
import com.SupplierStorage.PL.ViewController;
import com.Workers.Workers;


public class SupplyAgreementManager extends LogicManager<DAOSupplyAgreement,SupplyAgreement>{
	private AgreementProductManager _apm;
	IWorkers iworker = Workers.getInstance();
	public SupplyAgreementManager(DAOSupplyAgreement db){
		super(db);

		

	}
	
	@Override
	public void create(SupplyAgreement value) throws SQLException{
		if(value.get_dType().equals(DelevryType.cometake) && value.get_sType().equals(SupplyType.setday)) {
			ArrayList<DayOfWeek> days = new ArrayList<>();
			for (Day d:value.get_day()) {
				if(d.getValue()==1)
					days.add(DayOfWeek.of(7));
				else
					days.add(DayOfWeek.of(d.getValue()-1));
			}
			iworker.setWeeklyDeleveryShifts(days);
		}
	_db.insert(value);
	
	}

    public DelevryType getDelevryType(String id) throws SQLException {
       return  _db.getDelevryType(id);
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
	
	public ArrayList<OrderProduct> getCheapstProductsPerDay(SupplyAgreement.Day day,HashMap<Product,Integer> products) throws SQLException{
		return _apm.getCheapestProductPerDay(products, day);
	}
	public ArrayList<OrderProduct> getCheapestProductOnDemand(HashMap<Product,Integer> products) throws SQLException{
		return _apm.getCheapestProductOnDemand(products);
	}
	
	public ArrayList<Product> getAllOnDemandProducts() throws SQLException{
		return _apm.getAllOnDemandProducts();
	}
	public void setAgreementProductManager(DAOSupplyAgreementProduct db){
		_apm = new AgreementProductManager(db);
		_apm.set_supplyagreement(_db);

	}
	
	
	class AgreementProductManager extends LogicManager<DAOSupplyAgreementProduct,SupplyAgreementProduct>
	{


		public AgreementProductManager(DAOSupplyAgreementProduct db) {
			super(db);
		}

		public void set_supplyagreement(DAOSupplyAgreement db){
			_db.set_supplyagreemnt(db);
		}

		public ArrayList<Product> getAllOnDemandProducts() throws SQLException {
			
			ArrayList<SupplyAgreementProduct> supl_products = _db.getAllOnDemand();
			ArrayList<Product> products = new ArrayList<>();
			for (Product product : supl_products) {
				if(!products.contains(product))
					products.add(product);
			}
			return products;
			
		}

		public ArrayList<OrderProduct> getCheapestProductPerDay(HashMap<Product,Integer> products, Day day) throws SQLException {
			ArrayList<OrderProduct> to_order = new ArrayList<OrderProduct>();
			
			for (Product product : products.keySet()) {
				to_order.add(getCheapstProduct(_db.getProductByDay(product,day.getValue()),products.get(product).intValue()));
			}
			return to_order;
		}
		public ArrayList<OrderProduct> getCheapestProductOnDemand(HashMap<Product,Integer> products) throws SQLException {
			ArrayList<OrderProduct> to_order = new ArrayList<>();
			
			for (Product product : products.keySet()) {
				to_order.add(getCheapstProduct(_apm.getEarliestOnDemend(product),products.get(product).intValue()));
			}
			return to_order;
		}

		private OrderProduct getCheapstProduct(ArrayList<SupplyAgreementProduct> products,int amount)
		{
			float min_price = products.get(0).get_price();
			SupplyAgreementProduct min_product = products.get(0);
			for (SupplyAgreementProduct product : products) {
				float discount_price = product.get_price();
				float original_price = product.get_price();
				for (Discount discount : product.get_discounts()) {
					if((discount.get_quantity() < amount) 
							&& discount_price > original_price*discount.get_precent())
						discount_price=original_price*discount.get_precent();
					//TODO: Delete
					if(ViewController.debug)
					System.out.println("discount : " + discount);
				}
				//TODO: Delete
				if(ViewController.debug)
				System.out.println("discount price: " + discount_price);
				if(discount_price<min_price)
				{
					min_price = discount_price;
					min_product = product;
				}
			}
			OrderProduct orderProduct = new OrderProduct(min_product, min_price, amount);
			orderProduct.set_supplyAgreement(min_product.get_supplyAgreement());
			return orderProduct;
		}
		public SupplyAgreementProduct getCheapestProductBy(String producer, String name, int amount) throws SQLException {
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
        public ArrayList<SupplyAgreementProduct> getEarliestOnDemend(Product product) throws SQLException {
            return _db.getDeliveryType(product);
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

		@Override
		public void create(SupplyAgreementProduct value) throws SQLException {
			// TODO Auto-generated method stub
			
		}

		public ArrayList<SupplyAgreementProduct> getAllDayProducts(int d) throws SQLException {
			return _db.getAllDay(d);
		}
		

		
	}


	public ArrayList<SupplyAgreementProduct> getAllDayProducts(int d) throws SQLException {
		return _apm.getAllDayProducts(d);
	}
}
