package BE;

public class OrderProduct extends SupplyAgreementProduct{
	private String OrderID;
	private float actual_price;
	private int amount;
	
	public OrderProduct(SupplyAgreementProduct agreementProduct, float price, int amount) {
		super(agreementProduct);
		this.actual_price = price;
		this.amount = amount;
	}




	public OrderProduct(SupplyAgreementProduct ap) {
		super(ap);
	}
	public float getPrice() {
		return actual_price;
	}

	public void setPrice(float price) {
		this.actual_price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}


	public String getOrderID() {
		return OrderID;
	}
	public void setOrderID(String orderID) {
		OrderID = orderID;
	}
	
	public boolean equals(Object o)
	{
		return o.toString().toUpperCase().equals(toString().toUpperCase());
	}
	
}
