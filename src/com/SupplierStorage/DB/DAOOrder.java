package com.SupplierStorage.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.Common.Models.Order;
import com.SupplierStorage.BE.OrderProduct;
import com.SupplierStorage.BE.Product;
import com.SupplierStorage.BE.WeeklyOrder;
import com.SupplierStorage.BE.SupplyAgreement.Day;
import com.SupplierStorage.PL.ViewController;

public class DAOOrder extends DAO<Order> {

	DAOOrderProduct _product;
	DAOSupplier _supplier;
	public DAOOrder(Connection c) {
		super(c);
		_product = new DAOOrderProduct(c);
		_supplier = new DAOSupplier(c);
	}

	@Override
	public String[] getSearchFields() {
		return new String[]{"All","ID","SUPPLIER_CN","WEIGHT","ORDERDATE","PRICE","DELEVRYDATE"};
	}

	@Override
	public String[] getSearchFieldsView() {
		return new String[]{"All","ID","SUPPLIER CN","Weight","Date","Price","Delevery Date"};
	}

	@Override
	public String getTable() {
		return "ORDERS";
	}

	@Override
	protected String[] getValues(Order object) {
		return new String[]{object.getOrderID(),"'"+object.get_supplier().get_CN()+"'",
				""+object.get_weight(),dateConvert(object.get_order_date()),""+object.get_price(),dateConvert(object.get_delevery_date())};
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
		order.set_order_date(stringConverDate(rs.getString("ORDERDATE")));
		order.set_delevery_date(stringConverDate(rs.getString("DELEVRYDATE")));
		order.setOrderID(""+rs.getInt("ID"));
		order.set_supplier(_supplier.getFromPK(new String[]{""+rs.getInt("SUPPLIER_CN")}));
		order.set_weight(rs.getFloat("WEIGHT"));
		order.set_price(rs.getFloat("PRICE"));
		
		ArrayList<OrderProduct> products = _product.search(new int[]{1}, new String[]{order.getOrderID()});
		order.set_amountProduct(products);
		return order;
	}
	
	private Date stringConverDate(String date)
	{
		Date date1 = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		date1 = df.format(date);
	return "'"+date1+"'";
	}

	public ArrayList<OrderProduct> getWeeklyOrder(int day) throws SQLException {
		return _product.getWeeklyOrder(day);
	}

	public void create_weekly_order(WeeklyOrder order){
		
		try {
			remove_weekly_order(order.getDay());
			String sql;
	    	Statement stmt;
			sql = "INSERT OR IGNORE INTO WEEKLY_ORDER (DAY) " +
	                "VALUES ("+order.getDay().getValue() +");"; 
			stmt = _c.createStatement();
			stmt.executeUpdate(sql);
			// TODO  c.commit();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String sql;
	    	Statement stmt;
	    	for ( Product key : order.getProducts().keySet() ) {
				sql = "INSERT INTO WEEKLY_ORDER_PRODUCT (DAY,ID,AMOUNT) " +
		                "VALUES ("+order.getDay().getValue()+","+ key.get_id()+","+order.getProducts().get(key)+");"; 
		    	stmt = _c.createStatement();
		    	//TODO: Delete
				if(ViewController.debug)
					System.out.println(sql);
		    	stmt.executeUpdate(sql);
		       
	    	}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void remove_weekly_order(Day day){
		
		try{
			String sql;
	    	Statement stmt;
			sql = "DELETE from WEEKLY_ORDER_PRODUCT where DAY="+day.getValue()+";";
			stmt = _c.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			 System.err.println("");
		}
		
		try{
			String sql;
	    	Statement stmt;
			sql = "DELETE from WEEKLY_ORDER where DAY="+day+";";
			stmt = _c.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			 System.err.println("");
		}
		
		
	}

	public ArrayList<OrderProduct> getOnDemand() throws SQLException {
		return _product.getOnDemand();
	}
	
}
