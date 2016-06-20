package com.SupplierStorage.BL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import com.Common.Models.Order;
import com.SupplierStorage.BE.*;
import com.SupplierStorage.BE.SupplyAgreement.Day;
import com.SupplierStorage.PL.ViewController;


public class StorageLogic {

	
	com.SupplierStorage.DB.StorageController sc ;
	com.SupplierStorage.DB.ReportController rc;
	SupplierLogic sl;
	
	public StorageLogic(com.SupplierStorage.DB.StorageController storage_controller, com.SupplierStorage.DB.ReportController rc){

		this.sc=storage_controller;
		this.rc=rc;
	}

	public void run(SupplierLogic sl){
		//TODO: delete
		System.out.println("running");
		this.sl = sl;

	}

	
	public int remove_from_storage (String catagory,String sub_catagory,String sub_sub_catagory,String product_name,String producer ,int amount) throws SQLException{
		int check=0;
		HashMap<Product,Integer> products = new HashMap<Product,Integer>();
		Producer produc = new Producer(producer);
		int id = rc.product_search_ID(catagory,sub_catagory,sub_sub_catagory,product_name,produc);
		Product prod = new Product(id,catagory,sub_catagory,sub_sub_catagory,product_name,produc);
		check = sc.Check_amount(prod, amount);
		if (check!=0){
			for (int i=0; i<amount;i++)
			{
					int serial = sc.get_INS_serial(prod);
					Date Valid=sc.get_INS_valid(prod);
					int defected =sc.get_INS_defected(prod);
					INS_product ins_prod = new INS_product(prod,Valid,serial,defected);
					IssueCertificate issue = new IssueCertificate();
					sc.remove_from_storage(ins_prod,issue);
			}
			if (check==1){
				int order_amount=sc.get_evalute_amount(prod);
				products.put(prod, order_amount);
				sl.supplyOnDemand(products);
			} 
		}
		return check;
	}
	

	public void getSupply (ArrayList<OrderProduct> products){
		//TODO: Delete
				if(ViewController.debug && products!=null && products.size() > 0)
		{System.out.println(products.get(0).getAmount());};
		if (products!=null && products.size() > 0){
			System.err.println("Order Arrived - Press Enter To Recive");
			Scanner scn = new Scanner(System.in);
			scn.nextLine();
			ArrayList<INS_product> ins_products = new ArrayList<INS_product>();
		for (int i=0;i<products.size();i++){
			for(int j=0;j<products.get(i).getAmount();j++){
				INS_product ins = new INS_product(products.get(i).get_id(),products.get(i).get_category(),products.get(i).get_sub_category(),products.get(i).get_sub_sub_category(),products.get(i).get_name(),products.get(i).get_producer(),products.get(i).get_weight(),products.get(i).get_price(),0,products.get(i).get_shelf_life());
				ins_products.add(ins);
				}
		}
		sc.getSupply(ins_products);
		}
		
	}


	public ArrayList<Order> getOrders(){
		ArrayList<Order> orders;
		orders=sl.getOrders();
		return orders;

	}

	public void deleteOrder(String id){
		sl.deleteOrder(id);
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
	
	public ArrayList<String> producer_product_search (String cat_name,String sub_cat_name,String sub_sub_cat_name,String name){
		ArrayList<String> ans;
		ans=rc.producer_product_search(cat_name,sub_cat_name,sub_sub_cat_name,name);
		return ans;
	}
	
	public ArrayList<INS_product> ins_product_search (String cat_name,String sub_cat_name,String sub_sub_cat_name,String name,String producer){
		ArrayList<INS_product> ans;
		Product prod;
		prod=sc.product_search(cat_name,sub_cat_name,sub_sub_cat_name,name,producer);
		ans=sc.ins_product_search(prod);
		return ans;
	}
	
	public ArrayList<ArrayList<String>> get_report (ArrayList<String> parm,int count,String criter){
		ArrayList<ArrayList<String>> ans;
		ans=rc.get_report(parm,count,criter);
		return ans;
	}
	
	public void create_weekly_order(Day day, HashMap<Product,Integer> products){
		
	}
	
	

	
	public int get_evalute_amount (Product p){
		int ev_amount;
		ev_amount=sc.get_evalute_amount(p);
		return ev_amount;
	}
	
	public int defected (){
		int answer=0;
		if(Math.random() < 0.2) {
		    answer = 1; 
		}
		return answer;
	}
	
	public void update_defected(INS_product p,int defected){
		sc.update_defected(p,defected);
	}
}
