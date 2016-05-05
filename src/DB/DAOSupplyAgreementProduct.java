package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import BE.SupplyAgreementProduct;
import BE.Discount;
import BE.Product;
import BE.SupplierProduct;

public class DAOSupplyAgreementProduct extends DAO<SupplyAgreementProduct> {

	DAOSupplierProduct _product;
	DAODiscount _discount;
	
	public DAOSupplyAgreementProduct(Connection c) {
		super(c);
		_product = new DAOSupplierProduct(c);
		_discount = new DAODiscount(c);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","SUPPLY_AGREEMENT_ID","Supplier_Product_SN","Price"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","Supply Agreement ID","Supplier Product SN","Price"};
	}

	@Override
	public String getTable() {
		return "Supply_Agreement_Product";
	}

	@Override
	protected String[] getValues(SupplyAgreementProduct object) {
		return new String[]{""+object.get_sp(),""+object.get_serial_number(),""+object.get_price()};
	}

	@Override
	public void insert(SupplyAgreementProduct object) throws SQLException {
		insert(getValues(object), false);
		
		for (Discount discount : object.get_discounts()) {
			discount.setAgreementProductSN(object.get_serial_number());
			discount.setSupplyid(object.get_sp());
			_discount.insert(discount);
		}
		
	}
	@Override
	public SupplyAgreementProduct getFromPK(String[] values) throws SQLException {
		return search(new int[]{1,2},values).get(0);
	}

	@Override
	public SupplyAgreementProduct create(ResultSet rs) throws SQLException {
		SupplyAgreementProduct agreementProduct = new SupplyAgreementProduct(_product.getFromPK(new String[]{rs.getString("Supplier_Product_SN")}));
		agreementProduct.set_price(rs.getFloat("PRICE"));
		agreementProduct.set_sp(rs.getString("SUPPLY_AGREEMENT_ID"));
		ArrayList<Discount> discounts = _discount.search(new int[]{2,3},new String[]{
				agreementProduct.get_serial_number(),agreementProduct.get_sp()});
		agreementProduct.set_discounts(discounts);
		return agreementProduct;
		
	}
	
	public ArrayList<SupplyAgreementProduct> getProductByNameProducer(String producer,String name) throws SQLException
	{
		ArrayList<SupplyAgreementProduct> products = new ArrayList<>();
		String sql = "SELECT * FROM SUPPLY_AGREEMENT_PRODUCT WHERE SUPPLIER_PRODUCT_SN IN (SELECT SN FROM SUPPLIER_PRODUCT WHERE PRODUCTPRODUCERNAME="+
				producer + "PRODUCTNAME = " + name +")";
		ResultSet rs = _stm.executeQuery(sql);
		while(rs.next())
		{
			products.add(create(rs));
		}
		return products;
	}

	public ArrayList<SupplyAgreementProduct> getProductByDay(Product product,int day) throws SQLException {
		ArrayList<SupplyAgreementProduct> products = new ArrayList<>();
		String sql = "SELECT * FROM SUPPLY_AGREEMENT_PRODUCT WHERE (SUPPLY_AGREEMENT_ID IN (SELECT ID "
				+ "FROM SUPPLY_AGREEMENT WHERE DAY LIKE '%" + day + "%')) AND Supplier_Product_SN IN (SELECT SN FROM SUPPLIER_PRODUCT WHERE "
						+ "AND PRODUCT_ID = " + product.get_id() + ")";
		ResultSet rs = _stm.executeQuery(sql);
		while(rs.next())
		{
			products.add(create(rs));
		}
		return products;
	}

}
