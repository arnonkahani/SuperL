package BL;

public class SupplierLogic {

	StorageLogic _storage_logic;
	OrderManager _om;
	public SupplierLogic()
	{
		WeeaklyOrder wo = _storage_logic.getDailyOrder();
		_storage_logic.acceptSupply(_om.makeWeekelyOrder(wo));
	}
}
