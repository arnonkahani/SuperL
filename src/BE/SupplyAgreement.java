package BE;

import java.util.ArrayList;


public class SupplyAgreement {
	public enum SupplyType {
		SetDay,OnDemand;
	}
	public enum DelevryType {
		comeTake,deliver;
	}
	public enum Day {
		sunday,monday,tuesday,wednesday,thursday,friday,saturday;
	}
	public enum Search{
		SuppID("sup");
		
		public final String columnName;       

	    private Search(String s) {
	    	columnName = s;
	    }
	    
	}
	private Supplier _sup;
	private SupplyType _sType;
	private Day _day;
	private DelevryType _dType;
	private ArrayList<Discount> _discounts;
	private ArrayList<ProductPrice> _prices;
	
	public SupplyAgreement(Supplier _sup, SupplyType _sType, Day _day, DelevryType _dType,
			ArrayList<Discount> discounts, ArrayList<ProductPrice> products) {
		this._sup = _sup;
		this._sType = _sType;
		this._day = _day;
		this._dType = _dType;
		this._discounts = discounts;
		this._prices = products;
	}

	public Supplier get_sup() {
		return _sup;
	}

	public void set_sup(Supplier _sup) {
		this._sup = _sup;
	}

	public SupplyType get_sType() {
		return _sType;
	}

	public void set_sType(SupplyType _sType) {
		this._sType = _sType;
	}

	public Day get_day() {
		return _day;
	}

	public void set_day(Day _day) {
		this._day = _day;
	}

	public DelevryType get_dType() {
		return _dType;
	}

	public void set_dType(DelevryType _dType) {
		this._dType = _dType;
	}

	public ArrayList<Discount> get_discounts() {
		return _discounts;
	}

	public void set_discounts(ArrayList<Discount> _discounts) {
		this._discounts = _discounts;
	}

	public ArrayList<ProductPrice> get_prices() {
		return _prices;
	}

	public void set_prices(ArrayList<ProductPrice> _prices) {
		this._prices = _prices;
	}

	

	
	
}
