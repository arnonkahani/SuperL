package DB;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import BE.*;

public class ObjectMaker {

	private DBquery _query;
	
	public ObjectMaker(DBquery query)
	{
		_query = query;
	}
	
	private Product createProduct(ResultSet rs) throws SQLException
	{
		Product	product = new Product(rs.getInt("weight"),
					rs.getInt("shelfLife"),
					rs.getString("Name"),
					createProducer(_query.search(1,rs.getString("ProducerName"),Producer.class)));

		return product;
	}
	

	public Producer createProducer(ResultSet rs) throws SQLException
	{
		Producer producer = new Producer(rs.getString("Name"));
		return producer;
	}
	
	public Supplier createSupplier(ResultSet rs) throws SQLException
	{
			Supplier supplier = new Supplier(rs.getString("name"), rs.getInt("paymentMethod"), rs.getString("CN"));
			ResultSet supplierProductsRS = _query.search(new int[]{1},new String[]{rs.getString("CN")},SupplierProduct.class);
			ArrayList<SupplierProduct> products = new ArrayList<>();
			while(supplierProductsRS.next())
			{
				products.add(createSupplierProduct(supplierProductsRS));
			}
			supplier.set_products(products);
			ResultSet contacts_RS = _query.search(new int[]{1},new String[]{rs.getString("CN")},Contact.class);
			ArrayList<Contact> contacts = new ArrayList<>();
			while(contacts_RS.next())
			{
				contacts.add(createContact(contacts_RS));
			}
			supplier.set_contacts(contacts);
			return supplier;
		
		
	}


	private Contact createContact(ResultSet contacts_RS) throws SQLException {
		Contact contact = new Contact(contacts_RS.getString("tel"), contacts_RS.getString("email"),
					contacts_RS.getString("email"));
		return contact;
	}
	
	public Order createOrder(ResultSet rs) throws SQLException{
		Order order = new Order(); //(samID, date, amountProduct, price);
		order.setOrderID(""+rs.getInt("orderID"));
		order.set_date(rs.getDate("Date"));
		order.set_price(rs.getFloat("Price"));
		SupplyAgreement supplyAgreement = createSupplyAgreement(_query.search(new int []{1},
				new String[]{""+rs.getInt("SupplyID")}, SupplyAgreement.class));
		order.set_samID(supplyAgreement);
		ResultSet orderProductsRS = _query.search(new int[]{1}, new String[]{""+rs.getInt("SupplyID")}, OrderProduct.class);
		ArrayList<OrderProduct> products = new ArrayList<>();
		while(orderProductsRS.next())
		{
			products.add(createOrderProduct(orderProductsRS));
		}
		return order;
		
	}
	
	private OrderProduct createOrderProduct(ResultSet orderProductsRS) throws SQLException {
		AgreementProduct agreementProduct = createAgreementProduct(_query.search(new int[]{1,2},
				new String[]{""+orderProductsRS.getInt("PIDSUPPLIER"),""+orderProductsRS.getInt("SUPPLYID")}, AgreementProduct.class));
		OrderProduct product = new OrderProduct(agreementProduct,
				orderProductsRS.getFloat("Price"), orderProductsRS.getInt("amount"));
		return product;
	}

	public SupplyAgreement createSupplyAgreement(ResultSet rs) throws SQLException
	{
		SupplyAgreement supplyAgreement = null;
			supplyAgreement = new SupplyAgreement();
			supplyAgreement.set_supplyID(rs.getString("supplyID"));
			supplyAgreement.set_sup(createSupplier(_query.search(new int[]{1}, new String[]{rs.getString("supplierCN")},Supplier.class)));
			supplyAgreement.set_day(rs.getString("Days"));
			supplyAgreement.set_dType(rs.getString("dType"));
			supplyAgreement.set_sType(rs.getString("sType"));
			
			ArrayList<AgreementProduct> agreementProduct = new ArrayList<>();
			ResultSet agreementProductRS = _query.search(new int[]{1,2},new String[]{supplyAgreement.get_sup().get_CN(),supplyAgreement.get_supplyID()},AgreementProduct.class);
			while(agreementProductRS.next())
			{
				agreementProduct.add(createAgreementProduct(agreementProductRS));
			}
			return supplyAgreement;
		
		
	}

	public SupplierProduct createSupplierProduct(ResultSet supplierProductRS) throws SQLException
	{
		Product product = createProduct(_query.search(new int[]{1}, new String[]{supplierProductRS.getString("PID")}, Product.class));
		SupplierProduct supplierProduct =  new SupplierProduct(product);
		supplierProduct.set_serial_number(""+supplierProductRS.getInt("SN"));
		return supplierProduct;
	}
	
	private AgreementProduct createAgreementProduct(ResultSet productPriceRS) throws SQLException {
		SupplierProduct supplierProduct = createSupplierProduct(_query.search(new int[]{1}, new String[]{""+productPriceRS.getInt("SN")}, SupplierProduct.class));
		AgreementProduct agreementProduct = new AgreementProduct(supplierProduct, productPriceRS.getFloat("Price"));
		ResultSet discountRS = _query.search(new int[]{1,2},new String[]{productPriceRS.getString("PIDsupplier"),productPriceRS.getString("supplyID")},Discount.class);
		ArrayList<Discount> discounts = new ArrayList<>();
		while(discountRS.next())
		{
			discounts.add(createDiscount(discountRS));
		}
		return agreementProduct;
	}

	private Discount createDiscount(ResultSet discountRS) throws SQLException {
		Discount discount = new Discount(discountRS.getInt("amount"), discountRS.getFloat("precent"));
		return discount;
	}
	

}
