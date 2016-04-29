package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import BE.OrderProduct;

public class DAOOrderProduct extends DAO<OrderProduct>{

	DAOSupplyAgreementProduct  _prodcut;
	
	public DAOOrderProduct(Connection c) {
		super(c);
		_prodcut = new DAOSupplyAgreementProduct(c);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","ORDERID","SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID","SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN","AMOUNT","PRICE"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","ORDERID","SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID","SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN","AMOUNT","PRICE"};
	}

	@Override
	public String getTable() {
		return "ORDER_PRODUCT";
	}

	@Override
	protected String[] getValues(OrderProduct object) {
		return new String[]{object.getOrderID(),object.getAgreementProduct().get_sp(),
				object.getAgreementProduct().get_product().get_serial_number(),""+object.getAmount(),""+object.getPrice()};
	}

	@Override
	public OrderProduct getFromPK(String[] values) throws SQLException {
		return search(new int[]{1,2,3},values).get(0);
	}

	@Override
	public OrderProduct create(ResultSet rs) throws SQLException {
		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setOrderID(""+rs.getInt("orderid"));
		orderProduct.setPrice(rs.getFloat("price"));
		orderProduct.setAmount(rs.getInt("amount"));
		orderProduct.setAgreementProduct(_prodcut.getFromPK(new String[]{rs.getString("SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID"),
				rs.getString("SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN")}));
		return orderProduct;
	}

}
