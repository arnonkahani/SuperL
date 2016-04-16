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

	private String _supplyID;
	private Supplier _sup;
	private SupplyType _sType;
	private ArrayList<Day> _day;
	private DelevryType _dType;
	
	private ArrayList<AgreementProduct> _prices;
	
	public SupplyAgreement(Supplier _sup, SupplyType _sType, ArrayList<Day> day, DelevryType _dType, ArrayList<AgreementProduct> products) {
		this._sup = _sup;
		this._sType = _sType;
		this._day = day;
		this._dType = _dType;
		this._prices = products;
	}

	public SupplyAgreement() {
		// TODO Auto-generated constructor stub
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

	public ArrayList<Day> get_day() {
		return _day;
	}

	public void set_day(ArrayList<Day> _day) {
		this._day = _day;
	}

	public DelevryType get_dType() {
		return _dType;
	}

	public void set_dType(DelevryType _dType) {
		this._dType = _dType;
	}


	public ArrayList<AgreementProduct> get_prices() {
		return _prices;
	}

	public void set_prices(ArrayList<AgreementProduct> _prices) {
		this._prices = _prices;
	}

	public void set_day(String string) {
		_day = new ArrayList<>();
		for (int i = 0; i < string.length(); i++) {
			_day.add(Day.values()[Integer.parseInt(String.valueOf(string.charAt(i)))]);
		}		
	}

	public void set_dType(String string) {
		_dType = DelevryType.valueOf(string);
		
	}

	public void set_sType(String string) {
		_sType = SupplyType.valueOf(string);
		
	}

	public String get_supplyID() {
		return _supplyID;
	}

	public void set_supplyID(String _supplyID) {
		this._supplyID = _supplyID;
	}

	

	
	
}
