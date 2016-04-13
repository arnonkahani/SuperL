package BE;

public class Product {
	

	public enum Search{
		ProductName("Name"),ProducerName("ProducerName"),ProducerId("pId");
		
		public final String columnName;       

	    private Search(String s) {
	    	columnName = s;
	    }
	}
	
	private String _name;
	private int _weight;
	private int _daysOfvaid;
	private Producer _producer;
	
	public Product(int _weight, int _daysOfvaid, String name,Producer _producer) {
		this._weight = _weight;
		this._daysOfvaid = _daysOfvaid;
		this._producer = _producer;
		this._name = name;
	}
	public Product(int _weight, int _daysOfvaid, String name) {
		this._weight = _weight;
		this._daysOfvaid = _daysOfvaid;
		this._name = name;
	}

	public Product(Product _product) {
		this._weight = _product._weight;
		this._daysOfvaid = _product._daysOfvaid;
		this._producer = _product._producer;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public int get_weight() {
		return _weight;
	}

	public void set_weight(int _weight) {
		this._weight = _weight;
	}

	public int get_daysOfvaid() {
		return _daysOfvaid;
	}

	public void set_daysOfvaid(int _daysOfvaid) {
		this._daysOfvaid = _daysOfvaid;
	}

	public Producer get_producer() {
		return _producer;
	}

	public void set_producer(Producer _producer) {
		this._producer = _producer;
	}



	
	
}
