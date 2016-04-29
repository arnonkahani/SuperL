package BE;

public class OrderProduct{
	private String OrderID;
	private AgreementProduct agreementProduct;
	private float price;
	private int amount;
	
	public OrderProduct(AgreementProduct agreementProduct, float price, int amount) {

		this.agreementProduct = agreementProduct;
		this.price = price;
		this.amount = amount;
	}
	public OrderProduct()
	{
		
	}
	public AgreementProduct getAgreementProduct() {
		return agreementProduct;
	}

	public void setAgreementProduct(AgreementProduct agreementProduct) {
		this.agreementProduct = agreementProduct;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String[] getValues() {
		return new String[]{agreementProduct.get_product().get_serial_number(),agreementProduct.get_sp(),""+price,""+amount};
	}
	public String getOrderID() {
		return OrderID;
	}
	public void setOrderID(String orderID) {
		OrderID = orderID;
	}
	
	
}
