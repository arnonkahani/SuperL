package BE;

public class Discount{
	private String supplyid;
	private String supplier_product_SN;
	private int _quantity;
	private float _precent;
	public Discount(int amount, Float precent) {
		_quantity=amount;
		
	}
	public Discount() {
		// TODO Auto-generated constructor stub
	}
	public int get_quantity() {
		return _quantity;
	}
	public void set_quantity(int _quantity) {
		this._quantity = _quantity;
	}
	public float get_precent() {
		return _precent;
	}
	public void set_precent(float _precent) {
		this._precent = _precent;
	}
	
	public String getAgreementProductSN() {
		return supplier_product_SN;
	}
	public void setAgreementProductSN(String supplier_product_SN) {
		this.supplier_product_SN = supplier_product_SN;
	}
	public String getSupplyid() {
		return supplyid;
	}
	public void setSupplyid(String supplyid) {
		this.supplyid = supplyid;
	}


	
}
