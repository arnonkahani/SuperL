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

import com.Common.ITransportation;
import com.Common.IWorkers;
import com.Common.Models.Order;
import com.SupplierStorage.BE.*;
import com.SupplierStorage.BE.SupplyAgreement.Day;
import com.SupplierStorage.DB.DAOOrder;
import com.Transpotation.Models.ValidationException;
import com.Transpotation.Transportation;
import com.Workers.Workers;


public class OrderManager extends LogicManager<DAOOrder,Order>{
	
	SupplierManager _sm;
	SupplyAgreementManager _sam;
	StorageLogic _storage_logic;
	ITransportation itransportation = Transportation.getInstance();
    IWorkers iworker = Workers.getInstance();

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
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = cal.getTime();
		return makeOrder(products,tomorrow);
	}
	
	
	
	
	private ArrayList<OrderProduct> makeOrder(ArrayList<OrderProduct> products,Date driver_date) throws SQLException
	{
        ArrayList<Order> orders = new ArrayList<>();
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
			for (int i = 0; i < 2; i++) {
				ArrayList<OrderProduct> temp_products = new ArrayList<>();
				if(i==0)
				{
					cn.getValue().forEach((p)-> {
						try {
							if(_sam.getDelevryType(p.get_sp()).equals(SupplyAgreement.DelevryType.cometake)) {
                                temp_products.add(p);
                            }
						} catch (SQLException e) {
							e.printStackTrace();
						}
					});
				}
				else if(i==1)
				{
					cn.getValue().forEach((p)-> {
						try {
							if(_sam.getDelevryType(p.get_sp()).equals(SupplyAgreement.DelevryType.deliver)) {
								temp_products.add(p);
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					});
				}
				float price = calculateTotalPrice(temp_products);
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				Order or = null;
				try {
					or = new Order(_sm.getSupplierByCN(cn.getKey()),format.parse(format.format(date)),temp_products,price);
					Calendar cl = Calendar.getInstance();
					cl.set(Calendar.HOUR_OF_DAY, 0);
					cl.set(Calendar.MINUTE, 0);
					cl.set(Calendar.SECOND, 0);
					cl.add(Calendar.DAY_OF_MONTH, 1);
					or.set_delevery_date(format.parse(format.format(cl.getTime())));
				} catch (ParseException e) {

				}


				create(or);
				if(i==0){
                    orders.add(or);

				}
			}

		}
        if(!orders.isEmpty()) {
			try {
				itransportation.makeTransportation(driver_date, orders);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (ValidationException e) {
				e.printStackTrace();
			} catch (Transportation.NoTrucksAvailable noTrucksAvailable) {
				noTrucksAvailable.printStackTrace();
			} catch (Transportation.NoDriversAvailable noDriversAvailable) {
				noDriversAvailable.printStackTrace();
			}
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
		return makeOrder(products,iworker.getEarliestDeleveryDate(new Date()));
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
        ArrayList<OrderProduct> products = _db.getWeeklyOrder(day);
        if(products == null || products.size() == 0)
            return products;
        ArrayList<OrderProduct> delevry_products = new ArrayList<>();
        for (OrderProduct p:products) {
            if(_sam.getDelevryType(p.get_sp()).equals(SupplyAgreement.DelevryType.deliver))
                delevry_products.add(p);
        }
        return delevry_products;

	}

	public ArrayList<OrderProduct> getOnDemand() throws SQLException {
        ArrayList<OrderProduct> products = _db.getOnDemand();
        if(products == null || products.size() == 0)
            return products;
        ArrayList<OrderProduct> delevry_products = new ArrayList<>();
        for (OrderProduct p:products) {
            if(_sam.getDelevryType(p.get_sp()).equals(SupplyAgreement.DelevryType.deliver))
                delevry_products.add(p);
        }
        return delevry_products;
	}

	
	
}
