package com.SupplierStorage.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.SupplierStorage.BE.OrderProduct;
import com.SupplierStorage.PL.ViewController;

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
		return new String[]{object.getOrderID(),object.get_sp(),
				object.get_serial_number(),""+object.getAmount(),""+object.getPrice()};
	}

	@Override
	public OrderProduct getFromPK(String[] values) throws SQLException {
		return search(new int[]{1,2,3},values).get(0);
	}

	@Override
	public OrderProduct create(ResultSet rs) throws SQLException {
		OrderProduct orderProduct = new OrderProduct(_prodcut.getFromPK(new String[]{rs.getString("SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID"),
				rs.getString("SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN")}));
		orderProduct.setOrderID(""+rs.getInt("orderid"));
		orderProduct.setPrice(rs.getFloat("price"));
		orderProduct.setAmount(rs.getInt("amount"));
		
		return orderProduct;
	}
	
	public ArrayList<OrderProduct> getWeeklyOrder(int day) throws SQLException{
		ArrayList<OrderProduct> products = new ArrayList<>();
		
		String sql = "SELECT * FROM ORDER_PRODUCT WHERE ORDERID IN (SELECT ID FROM ORDERS WHERE DELEVRYDATE = " + getDate() +") "
				+ "AND SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID IN (SELECT ID FROM SUPPLY_AGREEMENT WHERE DAY LIKE '%" + day + "%')";
		_stm = _c.createStatement();
		ResultSet rs = _stm.executeQuery(sql);
		//TODO: Delete
				if(ViewController.debug)
				System.out.println("DAOOrderPRoduct: QUERY: " + sql);
		while(rs.next())
		{
			products.add(create(rs));
		}
		return products;
	}
	
	
	private String getDate()
	{
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		String date1 = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		date1 = df.format(cl.getTime());
	return "'"+date1+"'";
	}

	public ArrayList<OrderProduct> getOnDemand() throws SQLException {
		ArrayList<OrderProduct> products = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		int d = calendar.get(Calendar.DAY_OF_WEEK); 
		String sql = "SELECT * FROM ORDER_PRODUCT WHERE ORDERID IN (SELECT ID FROM ORDERS WHERE DELEVRYDATE = " + getDate() +") "
				+ "AND SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID IN (SELECT ID FROM SUPPLY_AGREEMENT WHERE DAY = 0)";
		//TODO: Delete
		if(ViewController.debug)
		System.out.println("DAOOrderPRoduct: QUERY: " + sql);
		_stm = _c.createStatement();
		ResultSet rs = _stm.executeQuery(sql);
		while(rs.next())
		{
			products.add(create(rs));
		}
		return products;
	}

	public void delete(OrderProduct pr) {
		try{
			String sql;
			Statement stmt;
			sql = "DELETE from ORDERID,SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID,SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN WHERE" +
					" ORDERID =" + pr.getOrderID() +
					" AND SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID =" + pr.get_sp() +
					" AND SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN = "+ pr.get_serial_number() + ";";
			stmt = _c.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println("");
		}
	}
}
