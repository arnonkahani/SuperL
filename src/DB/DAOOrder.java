package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import BE.Order;
import BE.OrderProduct;

public class DAOOrder extends DAO<Order> {

	DAOOrderProduct _product;
	DAOSupplyAgreement _agreement;
	public DAOOrder(Connection c) {
		super(c);
		_product = new DAOOrderProduct(c);
		_agreement = new DAOSupplyAgreement(c);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","ID","SUPPLY_AGREEMENT_ID","WEIGHT","DATE","PRICE"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","ID","SUPPLY AGREEMENT ID","WEIGHT","DATE","PRICE"};
	}

	@Override
	public String getTable() {
		return "ORDERS";
	}

	@Override
	protected String[] getValues(Order object) {
		return new String[]{object.getOrderID(),object.get_samID().get_supplyID(),
				""+object.get_weight(),dateConvert(object.get_date()),""+object.get_price()};
	}

	@Override
	public Order getFromPK(String[] values) throws SQLException {
		return search(new int[]{1},values).get(0);
	}
	
	@Override
	public void insert(Order object) throws SQLException {
		 insert(getValues(object),true);
		 int pk = getLastAutoID();
		 object.setOrderID(""+pk);
		for (OrderProduct orderProduct : object.get_amountProduct()) {
			orderProduct.setOrderID(""+pk);
			_product.insert(orderProduct);
		}
	}
	
	@Override
	public Order create(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.set_date(stringConverDate(rs.getString("DATE")));
		order.setOrderID(""+rs.getInt("ID"));
		order.set_samID(_agreement.getFromPK(new String[]{""+rs.getInt("SUPPLY_AGREEMENT_ID")}));
		order.set_weight(rs.getFloat("WEIGHT"));
		order.set_price(rs.getFloat("PRICE"));
		ArrayList<OrderProduct> products = _product.search(new int[]{1}, new String[]{order.getOrderID()});
		order.set_amountProduct(products);
		return order;
	}
	
	private Date stringConverDate(String date)
	{
		Date date1 = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date1 = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return date1;
	}
	private String dateConvert(Date date)
	{
		String date1 = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date1 = df.format(date);
	return "'"+date1+"'";
	}

}
