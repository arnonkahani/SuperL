package BE;

public class Product{
	
	
	private String _name;
	private float _weight;
	private int _shelf_life;
	private Producer _producer;
	public Product()
	{
		
	}
	public Product(int _weight, int _daysOfvaid, String name,Producer _producer) {
		this._weight = _weight;
		this._shelf_life = _daysOfvaid;
		this._producer = _producer;
		this._name = name;
	}
	public Product(float weight, int _daysOfvaid, String name) {
		this._weight = weight;
		this._shelf_life = _daysOfvaid;
		this._name = name;
	}

	public Product(Product _product) {
		this._weight = _product._weight;
		this._shelf_life = _product._shelf_life;
		this._producer = _product._producer;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public float get_weight() {
		return _weight;
	}

	public void set_weight(float _weight) {
		this._weight = _weight;
	}

	public int get_shelf_life() {
		return _shelf_life;
	}

	public void set_shelf_life(int _daysOfvaid) {
		this._shelf_life = _daysOfvaid;
	}

	public Producer get_producer() {
		return _producer;
	}

	public void set_producer(Producer _producer) {
		this._producer = _producer;
	}

	public String toString()
	{
		return "Name :" + _name + " Weight: " + _weight + " Shelf Life: " + _shelf_life + " " + _producer.toString();
		
	}



	
	
}
