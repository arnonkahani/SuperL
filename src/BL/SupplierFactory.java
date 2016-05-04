package BL;

import BE.Order;
import BE.Supplier;
import BE.SupplierProduct;
import BE.SupplyAgreement;
import DB.DAOFactory;
import DB.DAOOrder;
import DB.DAOSupplier;
import DB.DAOSupplierProduct;
import DB.DAOSupplyAgreement;

public class SupplierFactory {

	OrderManager _om;
	SupplierManager _sm;
	SupplyAgreementManager _sam;
	ProductManager _pm;
	
	public SupplierFactory(DAOFactory factory)
	{
		_sm = new SupplierManager((DAOSupplier) factory.create(Supplier.class));
		_sam = new SupplyAgreementManager((DAOSupplyAgreement) factory.create(SupplyAgreement.class));
		_om = new OrderManager((DAOOrder) factory.create(Order.class));
		_pm = new ProductManager((DAOSupplierProduct) factory.create(SupplierProduct.class));
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
