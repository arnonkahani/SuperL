package com.SupplierStorage.BL;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

import com.Common.Models.Order;
import com.SupplierStorage.BE.Product;
import com.SupplierStorage.BE.Supplier;
import com.SupplierStorage.BE.SupplierProduct;
import com.SupplierStorage.BE.SupplyAgreement;
import com.SupplierStorage.BE.SupplyAgreementProduct;
import com.SupplierStorage.BE.WeeklyOrder;
import com.SupplierStorage.DB.DAOFactory;
import com.SupplierStorage.DB.DAOOrder;
import com.SupplierStorage.DB.DAOProduct;
import com.SupplierStorage.DB.DAOSupplier;
import com.SupplierStorage.DB.DAOSupplierProduct;
import com.SupplierStorage.DB.DAOSupplyAgreement;
import com.SupplierStorage.DB.DAOSupplyAgreementProduct;
import com.SupplierStorage.PL.ViewController;

public class SupplierLogic {
	private StorageLogic _storage_logic;
	private OrderManager _om;
	private SupplierManager _sm;
	private SupplyAgreementManager _sam;
	private ProductManager _pm;
	
	public SupplierLogic(DAOFactory db,StorageLogic storage_logic) throws SQLException
	{
		
		_storage_logic = storage_logic;
		
		
		_sm = new SupplierManager((DAOSupplier) db.create(Supplier.class));
		_sam = new SupplyAgreementManager((DAOSupplyAgreement) db.create(SupplyAgreement.class));
		_om = new OrderManager((DAOOrder) db.create(Order.class),_storage_logic);
		_om.setSupplyAgreementManger(_sam);
		_om.setSupplierManger(_sm);
		_pm = new ProductManager((DAOProduct) db.create(Product.class));
		_pm.setSupplierProductManager((DAOSupplierProduct) db.create(SupplierProduct.class));
		_sam.setAgreementProductManager((DAOSupplyAgreementProduct) db.create(SupplyAgreementProduct.class));
		
		_om.setStorageLogic(_storage_logic);
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); 
		WeeklyOrder wo = db.get_daily_order(day+1);
		_om.makeWeekelyOrder(wo);
		
		_storage_logic.getSupply(_om.getWeeklyOrder(day));
		sendDailyOrders();
	}
	
	
	private void sendDailyOrders() {
		try {
			_storage_logic.getSupply(_om.getOnDemand());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	public void supplyOnDemand(HashMap<Product, Integer> products) throws SQLException{
		//TODO: Delete
		if(ViewController.debug)
			System.out.println("Called supply on demand supplier logic");
		if(products.size()==0)
			return;
		_om.makeOnDemand(products);
	}
	
	public void supplyWeekly(WeeklyOrder wo) throws SQLException{
		//TODO: Delete
		if(ViewController.debug){
			System.out.println("Called supply weekly supplier logic");
			if(wo.getProducts() != null && wo.getProducts().size()>0)
				System.out.println(wo.getProducts().size());
			else
				System.out.println("no products");
		}
		if(wo.getProducts().size()==0)
			return;
		_om.makeWeekelyOrder(wo);
	}
	
	public LogicManager getManager(Class view_object)
	{

			LogicManager managar = null;
			switch(view_object.getName())
			{
			case "com.Common.Models.Order":
				managar = _om;
				break;
			case "com.SupplierStorage.BE.Supplier":
				managar = _sm;
				break;
			case "com.SupplierStorage.BE.SupplyAgreement":
				managar = _sam;
				break;
			case "com.SupplierStorage.BE.Product":
				managar = _pm;
				break;
			}
			if(managar == null)
				System.err.println("Error in factory");
			return managar;
	}
}
