package BE;

public class SupplierProduct implements DBEntity{
	private Supplier _supplier;
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
	
	public String toString()
	{
		return "SN: " + _serial_number +" " + _product.toString();	
	}

	public String[] getValues() {
		return new String[]{"'"+_supplier.get_CN()+"'",""+_product.get_pid()};
	}

	public Supplier get_supplier() {
		return _supplier;
	}

	public void set_supplier(Supplier _supplier) {
		this._supplier = _supplier;
	}
	
	
}
