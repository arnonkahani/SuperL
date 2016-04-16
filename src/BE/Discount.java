package BE;

public class Discount {
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


	
}
