package BL;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.print.attribute.standard.DateTimeAtCompleted;

import BE.*;
import DB.DAO;
import DB.DAOOrder;
import PL.orderView;


public class OrderManager extends LogicManager<DAOOrder>{
	
	SupplierManager _sm;
	SupplyAgreementManager _sam;
	
	public OrderManager(DAOOrder db) {
		super(db);
	}
	@Override
	public void create(Object[] values) throws SQLException{
		ArrayList<SupplyAgreementProduct> products = _sam.getCheapestProductOnDemand((HashMap<Product,Integer>)values[1]);
		makeOrderProducts(products);
	}
	

	public Order getOrderByID(String id) throws SQLException
	{
		return getFromPK(new String[]{id});
	}
	
	public ArrayList<Order> getAllOrderes() throws SQLException
	{
		return getAll();
	}
	
	public ArrayList<OrderProduct> makeWeekelyOrder(WeeklyOrder wo) throws SQLException {
		ArrayList<SupplyAgreementProduct> products = _sam.getCheapstProductsPerDay(wo.getDay(), wo.getProducts());
		return makeOrderProducts(products);
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
	private ArrayList<OrderProduct> makeOrderProducts(ArrayList<SupplyAgreementProduct> products) throws SQLException
	{
		ArrayList<OrderProduct> product_list = new ArrayList<>();
		HashMap<String,ArrayList<OrderProduct>> cn_map = new HashMap();
		for (SupplyAgreementProduct product : products) {
			if(cn_map.containsKey(product.get_supplier()))
			{
				OrderProduct o = new OrderProduct(product);
				product_list.add(o);
				cn_map.get(product.get_supplier()).add(new OrderProduct(product));
		}
			else{
				OrderProduct o = new OrderProduct(product);
				ArrayList<OrderProduct> cn_pro = new ArrayList<>();
				product_list.add(o);
				cn_pro.add(o);
				cn_map.put(product.get_supplier(), cn_pro);
			}
				
		}
		for (Entry<String, ArrayList<OrderProduct>> cn : cn_map.entrySet()) {
			float price = calculateDiscount(cn.getValue());
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			Order or = null;
			try {
				or = new Order(_sm.getSupplierByCN(cn.getKey()),format.parse(format.format(date)),cn.getValue(),price);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_db.insert(or);
		}
		return product_list;
	
	}

	
	
}
