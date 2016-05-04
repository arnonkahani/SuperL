package BL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


import BE.*;
import DB.DAO;
import DB.DAOOrder;


public class OrderManager extends LogicManager<DAOOrder>{
	
	
	
	public OrderManager(DAOOrder db) {
		super(db);
	}
	@Override
	public void create(Object[] values) throws SQLException{
		float price = calculateDiscount((ArrayList<OrderProduct>)values[1]);
		Order or = new Order((SupplyAgreement)values[0],(Date)values[2], (ArrayList<OrderProduct>)values[1],price);
		_db.insert(or);
	}
	
	private float calculateDiscount(ArrayList<OrderProduct> orderProducts) throws SQLException {
		float price = 0;
		for (OrderProduct product : orderProducts) {
			product.setPrice(calculateDiscount(product,product.getAmount()));
			 price = price + product.getPrice()*product.getAmount();
		}
		return price;
	}
	
	private float calculateDiscount(OrderProduct product, int amount) {
		float discount_price = product.get_price();
		float original_price = product.get_price();
		for (Discount discount : product.get_discounts()) {
			if((discount.get_quantity() < amount) 
					&& discount_price > original_price*discount.get_precent())
				discount_price=original_price*discount.get_precent();
				
		}
		return discount_price;
	}
	
	public Order getOrderByID(String id) throws SQLException
	{
		return getFromPK(new String[]{id});
	}
	public ArrayList<Order> getAllOrderes() throws SQLException
	{
		return getAll();
	}

	
	
}
