package BE;

import java.util.Date;
import java.util.ArrayList;



public class Order {

	public enum Search{
		SuppID("sup");
		
		public final String columnName;       

	    private Search(String s) {
	    	columnName = s;
	    }
	    
	}
	
	private SupplyAgreement _samID;
	private int _weight;
	private Date _date;
	private ArrayList<ProductPrice> _amountProduct = new ArrayList<>();
	private float _price = 0;
	
	public Order(SupplyAgreement samID,Date date,ArrayList<ProductPrice> amountProduct,float price) {
		this._samID = samID;
		this._date = date;
		for (ProductPrice product : amountProduct) {
			_amountProduct.add(new ProductPrice(product));
			_weight = _weight + (product.get_product().get_weight()*product.get_amount());
		}
	}
	public SupplyAgreement get_samID() {
		return _samID;
	}
	public void set_samID(SupplyAgreement _samID) {
		this._samID = _samID;
	}
	public int get_weight() {
		return _weight;
	}
	public void set_weight(int _weight) {
		this._weight = _weight;
	}
	public Date get_date() {
		return _date;
	}
	public void set_date(Date _date) {
		this._date = _date;
	}
	public ArrayList<ProductPrice> get_amountProduct() {
		return _amountProduct;
	}
	public void set_amountProduct(ArrayList<ProductPrice> _amountProduct) {
		this._amountProduct = _amountProduct;
	}
	public float get_price() {
		return _price;
	}
	public void set_price(float _price) {
		this._price = _price;
	}
	
	public String toString(){
		String str;
		str = "Supplier CN: " + get_samID().get_sup().get_CN() + "\n"
				+ "Date: " + get_date().toString() + "\n"
				+ "Weight: " +  get_weight() + "\n"
				+ "Price: " + get_price() +"\n"
				+ "Product    Amount     Price\n"
				+ "---------------------------- \n";
		String productlist = "";
		for (ProductPrice product : get_amountProduct()) {
			productlist = productlist + product.get_product().get_name() + " | " + product.get_amount() + " | " + product.get_price() +"\n";
		}
		return str + productlist;
	}
	
}
