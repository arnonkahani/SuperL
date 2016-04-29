package BE;

public class SupplierProduct {
	private String _supplier ="";
	private Product _product;
	private String _serial_number ="";
	
	public SupplierProduct(Product product) {
		_product = product;
	}
	public SupplierProduct(){
		
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
	public void set_product(Product product) {
		_product = product;
	}
	




	public String get_supplier() {
		return _supplier;
	}

	public void set_supplier(String _supplier) {
		this._supplier = _supplier;
	}
	
	public String toString(){
		return "Supplier: " + _supplier + " " + _product + " SN: " + _serial_number;
	}
	
	
}
