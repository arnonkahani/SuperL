package BE;

public class SupplierProduct extends Product{
	private String _supplier ="";
	
	private String _serial_number ="";
	
	public SupplierProduct(Product product) {
		super(product);
	}
	public SupplierProduct(SupplierProduct product){
		super(product);
		_supplier = product._supplier;
		_serial_number = product._serial_number;
	}

	public String get_serial_number() {
		return _serial_number;
	}
	public void set_serial_number(String _serial_number) {
		this._serial_number = _serial_number;
	}


	




	public String get_supplier() {
		return _supplier;
	}

	public void set_supplier(String _supplier) {
		this._supplier = _supplier;
	}
	
	public String toString(){
		return "Supplier: " + _supplier + " " + super.toString() + " SN: " + _serial_number;
	}
	
	public boolean equals(Object o)
	{
		return super.equals(o) && _supplier.toUpperCase().equals(((SupplierProduct)o)._supplier) && _serial_number.toUpperCase().equals(((SupplierProduct)o)._serial_number);
	}
	
	
}
