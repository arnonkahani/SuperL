package BL;

import DB.DB;

public class BLFactory {

	DB _db;
	OrderManager _om;
	SupplierManager _sm;
	SupplyAgreementManager _sam;
	ProductManager _pm;
	
	public BLFactory(boolean first_time)
	{
		_db = new DB(first_time);
		_sm = new SupplierManager(_db);
		_sam = new SupplyAgreementManager(_db,_sm);
		_om = new OrderManager(_db, _sam);
		_pm = new ProductManager(_db);
	}

	public OrderManager get_om() {
		return _om;
	}

	public void set_om(OrderManager _om) {
		this._om = _om;
	}

	public SupplierManager get_sm() {
		return _sm;
	}

	public void set_sm(SupplierManager _sm) {
		this._sm = _sm;
	}

	public SupplyAgreementManager get_sam() {
		return _sam;
	}

	public void set_sam(SupplyAgreementManager _sam) {
		this._sam = _sam;
	}

	public ProductManager get_pm() {
		return _pm;
	}
	
	
}
