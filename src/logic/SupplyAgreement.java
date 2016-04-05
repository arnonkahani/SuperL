package logic;

import java.util.ArrayList;

public class SupplyAgreement {
	private Supplier _sup;
	private SupplyType _sType;
	private Day _day;
	private DelevryType _dType;
	private ArrayList<Discount> _discounts;
	
	public SupplyAgreement(Supplier _sup, SupplyType _sType, Day _day, DelevryType _dType) {
		this._sup = _sup;
		this._sType = _sType;
		this._day = _day;
		this._dType = _dType;
	}
	
	
}
