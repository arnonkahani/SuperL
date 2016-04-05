package logic;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class Receipt {

	private SupplyAgreement _sAgreement;
	private int _weight;
	private Date _date;
	private HashMap<Product, Integer> _amountProduct = new HashMap<>();
	
	public Receipt(SupplyAgreement _sAgreement, int _weight, Date _date,HashMap<Product, Integer> amountProduct) {
		this._sAgreement = _sAgreement;
		this._weight = _weight;
		this._date = _date;
		for (Map.Entry<Product, Integer> a : amountProduct.entrySet()) {
			_amountProduct.put(a.getKey(), a.getValue());
			
		}
	}

	
}
