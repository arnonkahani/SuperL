package BE;

public class OrderProduct {
	private AgreementProduct agreementProduct;
	private float price;
	private int amount;
	
	public OrderProduct(AgreementProduct agreementProduct, float price, int amount) {

		this.agreementProduct = agreementProduct;
		this.price = price;
		this.amount = amount;
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
	
	
}
