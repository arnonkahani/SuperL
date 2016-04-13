package BE;

public class ProductPrice {

	private Product _product;
	private float _price;
	private int _amount;
	
	
	public ProductPrice(Product product,float price,int amount)
	{
		_product = product;
		_price = price;
		_amount = amount;
	}


	public ProductPrice(ProductPrice product) {
		_price = product._price;
		_amount = product._amount;
		_product = new Product(product._product);
	}


	public Product get_product() {
		return _product;
	}


	public void set_product(Product _product) {
		this._product = _product;
	}


	public float get_price() {
		return _price;
	}


	public void set_price(float _price) {
		this._price = _price;
	}


	public int get_amount() {
		return _amount;
	}


	public void set_amount(int _amount) {
		this._amount = _amount;
	}
}
