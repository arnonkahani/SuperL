package BE;

public class Product{
	
	
	private String _name;
	private float _weight;
	private int _shelf_life;
	private Producer _producer;
	private int _min_amount;
	private String _categoryname_cat;
	private String _sub_categoryname_scat;
	private int _sub_sub_categoryname_sscat;
	private int _price;
	private String _id;
	
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
		this._name = _product._name;
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
	
	public boolean equals(Object o)
	{
		return o.toString().toUpperCase().equals(toString().toUpperCase());
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public int get_price() {
		return _price;
	}
	public void set_price(int _price) {
		this._price = _price;
	}
	public int get_sub_sub_categoryname_sscat() {
		return _sub_sub_categoryname_sscat;
	}
	public void set_sub_sub_categoryname_sscat(int _sub_sub_categoryname_sscat) {
		this._sub_sub_categoryname_sscat = _sub_sub_categoryname_sscat;
	}
	public String get_sub_categoryname_scat() {
		return _sub_categoryname_scat;
	}
	public void set_sub_categoryname_scat(String _sub_categoryname_scat) {
		this._sub_categoryname_scat = _sub_categoryname_scat;
	}
	public String get_categoryname_cat() {
		return _categoryname_cat;
	}
	public void set_categoryname_cat(String _categoryname_cat) {
		this._categoryname_cat = _categoryname_cat;
	}
	public int get_min_amount() {
		return _min_amount;
	}
	public void set_min_amount(int _min_amount) {
		this._min_amount = _min_amount;
	}



	
	
}
