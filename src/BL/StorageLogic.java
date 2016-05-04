package BL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import BE.*;
import BE.SupplyAgreement.Day;
import Modle.INS_product;
import Modle.Product;
import Modle.issue_certificate;
import Modle.report_controller;
import Modle.storage_controller;

public class StorageLogic {

	
	public void acceptSupply(ArrayList<OrderProduct> products);
	
	DB.storage_controller sc ;
	DB.report_controller rc;
	Connection c ;
	
	public Logic(storage_controller storage_controller, Connection c,report_controller rc){
		this.c=c;
		this.sc=storage_controller;
		this.rc=rc;
	}
	
	
	
	
	public int remove_from_storage (String catagory,String sub_catagory,String sub_sub_catagory,String product_name,int amount){
		int check=0;
		int id = rc.product_search_ID(catagory,sub_catagory,sub_sub_catagory,product_name,c);
		Product prod = new Product(id,catagory,sub_catagory,sub_sub_catagory,product_name);
		for (int i=0; i<amount;i++)
		{
			check = sc.Check_amount(prod, c, amount);
			if (check!=0){
				int serial = sc.get_INS_serial(prod,c);
				Date Valid=sc.get_INS_valid(prod,c);
				int defected =sc.get_INS_defected(prod,c);
				INS_product ins_prod = new INS_product(serial,prod.getId(),Valid,defected);
				issue_certificate issue = new issue_certificate();
				sc.remove_from_storage(prod,ins_prod,issue,c);}
			
		}
		return check;
	}

	public void getSupply (String catagory,String sub_catagory,String sub_sub_catagory,String product_name,int amount,Date date,int is_defected){
		Product prod = new Product(catagory,sub_catagory,sub_sub_catagory,product_name);
		INS_product ins_prod = new INS_product (prod.getId(),date,is_defected);
		sc.getSupply(prod,ins_prod,amount,c);
	}
	
	
	public ArrayList<String> cat_search (){
		ArrayList<String> ans;
		ans=rc.cat_search(c);
		return ans;
		 
	}
	
	
	public ArrayList<String> sub_cat_search (String cat_name){
		ArrayList<String> ans;
		ans=rc.sub_cat_search(cat_name,c);
		return ans;
	}
	
	public ArrayList<String> sub_sub_cat_search (String cat_name,String sub_cat_name){
		ArrayList<String> ans;
		ans=rc.sub_sub_cat_search(cat_name,sub_cat_name,c);
		return ans;
	
	}
	
	public ArrayList<String> product_search (String cat_name,String sub_cat_name,String sub_sub_cat_name){
		ArrayList<String> ans;
		ans=rc.product_search(cat_name,sub_cat_name,sub_sub_cat_name,c);
		return ans;
	}
	
	public ArrayList<ArrayList<String>> get_report (ArrayList<String> parm,int count,String criter){
		ArrayList<ArrayList<String>> ans;
		ans=rc.get_report(parm,count,criter,c);
		return ans;
	}
	
	public WeeklyOrder create_weekly_order(Day day, HashMap<Product,Integer> products){
		WeeklyOrder order = new WeeklyOrder(day,products);
		sc.create_weekly_order(order);
	}
	
	
}
