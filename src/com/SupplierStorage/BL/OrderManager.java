package com.SupplierStorage.BL;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import com.Common.Models.Order;
import com.SupplierStorage.BE.*;
import com.SupplierStorage.BE.SupplyAgreement.Day;
import com.SupplierStorage.DB.DAOOrder;


public class OrderManager extends LogicManager<DAOOrder,Order>{
	
	SupplierManager _sm;
	SupplyAgreementManager _sam;
	StorageLogic _storage_logic;
	public OrderManager(DAOOrder db,StorageLogic storage_logic) {
		super(db);
		_storage_logic = storage_logic;
	}
	
	@Override
	public void create(Order value) throws SQLException{
		_db.insert(value);
		//TODO : DELETE
		//_storage_logic.getSupply(value.get_amountProduct());
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
		if(wo.getProducts().size()==0)
			return null;
		ArrayList<OrderProduct> products = _sam.getCheapstProductsPerDay(wo.getDay(), wo.getProducts());
		return makeOrder(products);
	}
	
	
	
	
	private ArrayList<OrderProduct> makeOrder(ArrayList<OrderProduct> products) throws SQLException
	{
		ArrayList<OrderProduct> product_list = new ArrayList<>();
		HashMap<String,ArrayList<OrderProduct>> cn_map = new HashMap();
		for (OrderProduct product : products) {
			if(cn_map.containsKey(product.get_supplier()))
			{
				cn_map.get(product.get_supplier()).add(product);
		}
			else{
				ArrayList<OrderProduct> cn_pro = new ArrayList<>();
				cn_pro.add(product);
				cn_map.put(product.get_supplier(), cn_pro);
			}
				
		}
		for (Entry<String, ArrayList<OrderProduct>> cn : cn_map.entrySet()) {
			float price = calculateTotalPrice(cn.getValue());
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			Order or = null;
			try {
				or = new Order(_sm.getSupplierByCN(cn.getKey()),format.parse(format.format(date)),cn.getValue(),price);
				Calendar cl = Calendar.getInstance();
				cl.set(Calendar.HOUR_OF_DAY, 0);
				cl.set(Calendar.MINUTE, 0);
				cl.set(Calendar.SECOND, 0);
				cl.add(Calendar.DAY_OF_MONTH, 1);
				or.set_delevery_date(format.parse(format.format(cl.getTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
				

			create(or);
		}
		return product_list;
	
	}
	
	private float calculateTotalPrice(ArrayList<OrderProduct> value) {

		float price =0;
		for (OrderProduct orderProduct : value) {
			price = price + orderProduct.getPrice()*orderProduct.getAmount();
		}
		return price;
	}
	
	public ArrayList<OrderProduct> makeOnDemand(HashMap<Product, Integer> products_to_order) throws SQLException {
		ArrayList<OrderProduct> products = _sam.getCheapestProductOnDemand(products_to_order);
		return makeOrder(products);
	}
	
	
	public void setSupplyAgreementManger(SupplyAgreementManager sam) {
		_sam = sam;
		
	}
	public void setSupplierManger(SupplierManager sm) {
		_sm = sm;
		
	}
	public void setStorageLogic(StorageLogic _storage_logic2) {
		_storage_logic = _storage_logic2;
		
	}

	public void makeWeeklyOrder(HashMap<Product, Integer> product_table, Day day) {
		WeeklyOrder order = new WeeklyOrder(day,product_table);
		_db.create_weekly_order(order);
		
		
	}
	
	public ArrayList<OrderProduct> getWeeklyOrder(int day) throws SQLException
	{
		return _db.getWeeklyOrder(day);
	}

	public ArrayList<OrderProduct> getOnDemand() throws SQLException {
		return _db.getOnDemand();
	}

	
	
}