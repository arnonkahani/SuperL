package BE;

public class Discount implements DBEntity{
	private AgreementProduct agreementProduct;
	private int _quantity;
	private float _precent;
	public Discount(int amount, Float precent) {
		_quantity=amount;
		
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
	public String[] getValues() {
		return new String[]{""+_quantity,""+_precent,""+agreementProduct.get_product().get_serial_number(),""+agreementProduct.get_sp().get_supplyID()};
	}
	public AgreementProduct getAgreementProduct() {
		return agreementProduct;
	}
	public void setAgreementProduct(AgreementProduct agreementProduct) {
		this.agreementProduct = agreementProduct;
	}


	
}
