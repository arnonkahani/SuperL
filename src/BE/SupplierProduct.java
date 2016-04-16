package BE;

public class SupplierProduct{
	private Product _product;
	private String _serial_number;
	
	public SupplierProduct(Product product) {
		_product = product;
	}

	public String get_serial_number() {
		return _serial_number;
	}
	public void set_serial_number(String _serial_number) {
		this._serial_number = _serial_number;
	}

	public Product get_product() {
		return _product;
	}
	public Product set_product() {
		return _product;
	}
	
	
}
