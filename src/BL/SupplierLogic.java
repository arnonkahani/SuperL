package BL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import BE.Order;
import BE.OrderProduct;
import BE.Product;
import BE.Supplier;
import BE.SupplierProduct;
import BE.SupplyAgreement;
import BE.SupplyAgreementProduct;
import BE.WeeklyOrder;
import DB.DAOFactory;
import DB.DAOOrder;
import DB.DAOProduct;
import DB.DAOSupplier;
import DB.DAOSupplierProduct;
import DB.DAOSupplyAgreement;
import DB.DAOSupplyAgreementProduct;
import PL.ViewController;

public class SupplierLogic {
	private StorageLogic _storage_logic;
	private OrderManager _om;
	private SupplierManager _sm;
	private SupplyAgreementManager _sam;
	private ProductManager _pm;
	
	public SupplierLogic(DAOFactory db,StorageLogic storage_logic) throws SQLException
	{
		
		_storage_logic = storage_logic;
		WeeklyOrder wo = _storage_logic.get_daily_order();
		
		_sm = new SupplierManager((DAOSupplier) db.create(Supplier.class));
		_sam = new SupplyAgreementManager((DAOSupplyAgreement) db.create(SupplyAgreement.class));
		_om = new OrderManager((DAOOrder) db.create(Order.class),_storage_logic);
		_om.setSupplyAgreementManger(_sam);
		_om.setSupplierManger(_sm);
		_pm = new ProductManager((DAOProduct) db.create(Product.class));
		_pm.setSupplierProductManager((DAOSupplierProduct) db.create(SupplierProduct.class));
		_sam.setAgreementProductManager((DAOSupplyAgreementProduct) db.create(SupplyAgreementProduct.class));
		_storage_logic.getSupply(_om.makeWeekelyOrder(wo));
		_om.setStorageLogic(_storage_logic);
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
			case "BE.Order":
				managar = _om;
				break;
			case "BE.Supplier":
				managar = _sm;
				break;
			case "BE.SupplyAgreement":
				managar = _sam;
				break;
			case "BE.Product":
				managar = _pm;
				break;
			}
			if(managar == null)
				System.err.println("Error in factory");
			return managar;
	}
}
