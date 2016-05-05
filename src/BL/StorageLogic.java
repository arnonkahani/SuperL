package BL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import BE.*;
import BE.SupplyAgreement.Day;


public class StorageLogic {

	
	DB.storage_controller sc ;
	DB.report_controller rc;
	Connection c ;
	
	public StorageLogic(DB.storage_controller storage_controller, Connection c,DB.report_controller rc){
		this.c=c;
		this.sc=storage_controller;
		this.rc=rc;
	}
	
	
	
	
	
	public int remove_from_storage (String catagory,String sub_catagory,String sub_sub_catagory,String product_name,String producer ,int amount){
		int check=0;
		Producer produc = new Producer(producer);
		int id = rc.product_search_ID(catagory,sub_catagory,sub_sub_catagory,product_name,produc);
		Product prod = new Product(id,catagory,sub_catagory,sub_sub_catagory,product_name,produc);
		for (int i=0; i<amount;i++)
		{
			check = sc.Check_amount(prod, amount);
			if (check!=0){
				int serial = sc.get_INS_serial(prod);
				Date Valid=sc.get_INS_valid(prod);
				int defected =sc.get_INS_defected(prod);
				INS_product ins_prod = new INS_product(prod,Valid,serial,defected);
				issue_certificate issue = new issue_certificate();
				sc.remove_from_storage(ins_prod,issue);}
			if (check==1){
				int order_amount=sc.get_evalute_amount(prod);
				
			}
			
		}
		return check;
	}
	

	public void getSupply (ArrayList<OrderProduct> products){
		ArrayList<INS_product> ins_products = new ArrayList<INS_product>();
		for (int i=0;i<products.size();i++){
			for(int j=0;j<products.get(i).getAmount();j++){
				ins_products.add(new INS_product(products.get(i).get_id(),products.get(i).get_category(),products.get(i).get_sub_category(),products.get(i).get_sub_sub_category(),products.get(i).get_name(),products.get(i).get_producer(),products.get(i).get_weight(),products.get(i).get_price(),1));
				}
		}
		sc.getSupply(ins_products);
		
	}
	
	
	
	public ArrayList<String> cat_search (){
		ArrayList<String> ans;
		ans=rc.cat_search();
		return ans;
		 
	}
	
	
	public ArrayList<String> sub_cat_search (String cat_name){
		ArrayList<String> ans;
		ans=rc.sub_cat_search(cat_name);
		return ans;
	}
	
	public ArrayList<String> sub_sub_cat_search (String cat_name,String sub_cat_name){
		ArrayList<String> ans;
		ans=rc.sub_sub_cat_search(cat_name,sub_cat_name);
		return ans;
	
	}
	
	public ArrayList<String> product_search (String cat_name,String sub_cat_name,String sub_sub_cat_name){
		ArrayList<String> ans;
		ans=rc.product_search(cat_name,sub_cat_name,sub_sub_cat_name);
		return ans;
	}
	
	public ArrayList<ArrayList<String>> get_report (ArrayList<String> parm,int count,String criter){
		ArrayList<ArrayList<String>> ans;
		ans=rc.get_report(parm,count,criter);
		return ans;
	}
	
	public void create_weekly_order(Day day, HashMap<Product,Integer> products){
		WeeklyOrder order = new WeeklyOrder(day,products);
		sc.create_weekly_order(order);
	}
	
	public void remove_weekly_order(Day day){
		sc.remove_weekly_order(day);
	}
	
	public WeeklyOrder get_daily_order(){
		//return WeeklyOrder for specipic day
		WeeklyOrder weekly = new WeeklyOrder();
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); 
		weekly=sc.get_daily_order(day);
		return weekly;
	}
	
	public int get_evalute_amount (Product p){
		int ev_amount;
		ev_amount=sc.get_evalute_amount(p);
		return ev_amount;
	}
}
