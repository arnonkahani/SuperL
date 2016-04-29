package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import BE.AgreementProduct;
import BE.Discount;

public class DAOSupplyAgreementProduct extends DAO<AgreementProduct> {

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
	protected String[] getValues(AgreementProduct object) {
		return new String[]{""+object.get_sp(),""+object.get_product().get_serial_number(),""+object.get_price()};
	}

	@Override
	public void insert(AgreementProduct object) throws SQLException {
		insert(getValues(object), false);
		
		for (Discount discount : object.get_discounts()) {
			discount.setAgreementProductSN(object.get_product().get_serial_number());
			discount.setSupplyid(object.get_sp());
			_discount.insert(discount);
		}
		
	}
	@Override
	public AgreementProduct getFromPK(String[] values) throws SQLException {
		return search(new int[]{1,2},values).get(0);
	}

	@Override
	public AgreementProduct create(ResultSet rs) throws SQLException {
		AgreementProduct agreementProduct = new AgreementProduct();
		agreementProduct.set_price(rs.getFloat("price"));
		agreementProduct.set_product(_product.getFromPK(new String[]{rs.getString("Supplier_Product_SN")}));
		agreementProduct.set_sp(rs.getString("SUPPLY_AGREEMENT_ID"));
		ArrayList<Discount> discounts = _discount.search(new int[]{2,3},new String[]{
				agreementProduct.get_product().get_serial_number(),agreementProduct.get_sp()});
		agreementProduct.set_discounts(discounts);
		return agreementProduct;
		
	}

}
