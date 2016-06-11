package com.SupplierStorage.BE;

public class Product{
	
	
	protected String _name;
	protected float _weight;
	private int _shelf_life;
	protected Producer _producer;
	private int _min_amount;
	protected String _catagory;
	protected String _sub_catagory;
	protected String _sub_sub_category;
	protected float _price;
	protected int _id;
	
	public Product()
	{
		
	}
	
	public Product(String _name, float _weight, int _shelf_life, Producer _producer, int _min_amount, String _catagory,
			String _sub_catagory, String _sub_sub_category, float _price, int _id) {
		super();
		this._name = _name;
		this._weight = _weight;
		this._shelf_life = _shelf_life;
		this._producer = _producer;
		this._min_amount = _min_amount;
		this._catagory = _catagory;
		this._sub_catagory = _sub_catagory;
		this._sub_sub_category = _sub_sub_category;
		this._price = _price;
		this._id = _id;
	}

	public Product(int _id,String _catagory, String _sub_catagory, String _sub_sub_category , String _name, Producer _producer){
		this._id=_id;
		this._catagory=_catagory;
		this._sub_catagory=_sub_catagory;
		this._sub_sub_category=_sub_sub_category;
		this._name=_name;
		this._producer=_producer;
		
	}
	
	public Product(String _catagory, String _sub_catagory, String _sub_sub_category , String _name, Producer _producer){
		this._catagory=_catagory;
		this._sub_catagory=_sub_catagory;
		this._sub_sub_category=_sub_sub_category;
		this._name=_name;
		this._producer=_producer;
		
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
		this._catagory = _product._catagory;
		this._sub_catagory = _product._sub_catagory;
		this._sub_sub_category = _product._sub_sub_category;
		this._price = _product._price;
		this._min_amount = _product._min_amount;
		this._id = _product._id;
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
		return "Name :" + _name + " Weight: " + _weight + " Shelf Life: " + _shelf_life + " " + _producer.toString()
		+" Min Amount: " + _min_amount + " Category: " + _catagory + " Sub Category: " + _sub_catagory + "S ub Sub Category: " 
		+ _sub_sub_category + " Price: " + _price + " Id: " + _id;
	}
	
	public boolean equals(Object o)
	{
		return ((Product)o).get_id() == get_id();
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public float get_price() {
		return _price;
	}
	public void set_price(float _price) {
		this._price = _price;
	}
	public String get_sub_sub_category() {
		return _sub_sub_category;
	}
	public void set_sub_sub_category(String _sub_sub_categoryname_sscat) {
		this._sub_sub_category = _sub_sub_categoryname_sscat;
	}
	public String get_sub_category() {
		return _sub_catagory;
	}
	public void set_sub_category(String _sub_categoryname_scat) {
		this._sub_catagory = _sub_categoryname_scat;
	}
	public String get_category() {
		return _catagory;
	}
	public void set_category(String _categoryname_cat) {
		this._catagory = _categoryname_cat;
	}
	public int get_min_amount() {
		return _min_amount;
	}
	public void set_min_amount(int _min_amount) {
		this._min_amount = _min_amount;
	}



	
	
}
