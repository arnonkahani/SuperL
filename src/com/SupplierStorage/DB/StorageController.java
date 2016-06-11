package com.SupplierStorage.DB;
import java.util.ArrayList;
import java.util.Date;

import com.SupplierStorage.BE.INS_product;
import com.SupplierStorage.BE.Producer;
import com.SupplierStorage.BE.Product;
import com.SupplierStorage.BE.IssueCertificate;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StorageController {
	int index_ins_product;
	int index_issue_certificate;
	Connection c;
	
	
	public StorageController(int index_ins_product,int index_issue_certificate,Connection c) {
		this.index_ins_product=index_ins_product;
		this.index_issue_certificate=index_issue_certificate;
		this.c=c;
	}


	public void getSupply(ArrayList<INS_product> products){
	    
		try {
	    	
	    	String sql;
	    	Statement stmt;
	    	
	    	for(int j=0; j<products.size(); j++){
				//TODO: change: c.setAutoCommit(false);
				String date= products.get(j).dateConvert(products.get(j).getValid_date());
	    		sql = "INSERT INTO INS_PRODUCT (SERIAL_NUM,ID,VALID_DATE,DEFECTED) " +
	                   "VALUES ('"+index_ins_product+"','"+products.get(j).get_id()+ "','"+date+"','"+products.get(j).isDefected()+"');"; 
				 stmt = c.createStatement();
			
				stmt.executeUpdate(sql);

				index_ins_product++;
			}
			
		    
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  void remove_from_storage(INS_product ins,IssueCertificate issue){
		try {
	    	String sql;
	    	Statement stmt;
			issue.setS_id(index_issue_certificate);
			index_issue_certificate++;
			issue.setS_serial_num(ins.getSerial_num());
			issue.setS_date(new Date());
			issue.setS_p_id(ins.get_id());
			issue.setS_cat(ins.get_category());
			issue.setS_sub_cat(ins.get_sub_category());
			issue.setS_sub_sub_cat(ins.get_sub_sub_category());
			issue.setS_name(ins.get_name());
			issue.setS_producer(ins.get_producer().getName());
			issue.setS_valid_date(ins.getValid_date().toString());
			try{
				
				sql = "DELETE from INS_PRODUCT where SERIAL_NUM="+issue.getS_serial_num()+";";
				stmt = c.createStatement();
				stmt.executeUpdate("PRAGMA foreign_keys = OFF");
				stmt.executeUpdate(sql);
				
				create_issue(issue);
				stmt.executeUpdate("PRAGMA foreign_keys = ON");
			} catch (SQLException e) {
	
			}
	    c.commit();
			
		} catch (SQLException e) {
			
		}
		

	
	}
	
	public void create_issue (IssueCertificate issue){
		
		try {
			String sql;
	    	Statement stmt;
			sql = "INSERT INTO ISSUE_CERTIFICATE (S_ID,S_DATE,S_P_ID,S_SERIAL_NUM,S_CATAGORY,S_SUB_CATAGORY,S_SUB_SUB_CATAGORY,S_NAME,S_PRODUCER,S_VALID_DATE) " +
	                "VALUES ('"+issue.getS_id()+"','"+issue.getS_date()+"','"+issue.getS_p_id()+"','"+issue.getS_serial_num()+"',"+issue.getS_cat()+","+issue.getS_sub_cat()+","+issue.getS_sub_cat()+","+issue.getS_name()+","+issue.getS_producer()+",'"+issue.getS_valid_date()+"');"; 
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

			
		
	}
	
	
	
	public int get_INS_serial (Product prod){
		
		int serial = 0;
		try {
			String sql;
			Statement stmt;
			sql = "SELECT SERIAL_NUM FROM INS_PRODUCT WHERE ID="+prod.get_id()+" ORDER BY VALID_DATE ASC LIMIT 1;" ;
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			serial=rs.getInt("SERIAL_NUM");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return serial;

	}
	
	public Date get_INS_valid (Product prod){
		
		Date valid =new Date();	
		String date="";
		try {
			String sql;
			Statement stmt;
			sql = "SELECT VALID_DATE FROM INS_PRODUCT WHERE ID="+prod.get_id()+" ORDER BY VALID_DATE ASC LIMIT 1;" ;
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			date=rs.getString("VALID_DATE");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valid;
		

			
		
	}
	
	public int get_INS_defected (Product prod){
		
		int defected = 0;
		try {
			String sql;
			Statement stmt;
			sql = "SELECT DEFECTED FROM INS_PRODUCT WHERE ID="+prod.get_id()+" ORDER BY VALID_DATE ASC LIMIT 1;" ;
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			defected=rs.getInt("DEFECTED");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return defected;

	}
	
		
	
	
	
	public int Check_amount (Product prod,int amount){
		
		int ans=2;
		int curr_amount=0;
		int min=0;
		try {
			String sql;
			Statement stmt;
			sql = "SELECT MIN_AMOUNT FROM PRODUCT WHERE ID="+prod.get_id()+";" ;
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			min=rs.getInt("MIN_AMOUNT");
			curr_amount=get_Amount(prod.get_id());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (curr_amount-amount<0){
			ans=0;
		}
		else if(curr_amount-amount<min){
			ans=1;
		}
		return ans ;
		

			
		
	}
	

	
	public int get_Amount (int id){
		int curr_amount=0;
		try {
			String sql;
			Statement stmt;
			sql = "SELECT COUNT() AS AMOUNT FROM INS_PRODUCT WHERE ID="+id+";" ;
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			curr_amount=rs.getInt("AMOUNT");
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	  return curr_amount;	
	}
	
	
	
	public int get_evalute_amount (Product p){
		int ev_amount=0;
		try {
			String sql;
			Statement stmt;
			sql = "SELECT MIN_AMOUNT FROM PRODUCT WHERE ID="+p.get_id()+";" ;
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			ev_amount=rs.getInt("MIN_AMOUNT");
			ev_amount=ev_amount*2;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ev_amount;
	}
	
	
	public void update_defected(INS_product p,int defected){
		try {
			String sql;
			Statement stmt;
			sql = "UPDATE INS_PRODUCT SET DEFECTED="+defected+" WHERE SERIAL_NUM="+p.getSerial_num()+";" ;
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<INS_product> ins_product_search(Product prod){
		ArrayList<INS_product> ins_products = new ArrayList<INS_product>();
		int serial;
		int defected;
		String d;
		INS_product ins;
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date startDate=new Date();
		try {
			String sql;
	    	Statement stmt;
			sql = "SELECT SERIAL_NUM, ID, DEFECTED, VALID_DATE FROM INS_PRODUCT WHERE ID="+prod.get_id()+";"; 
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				serial=rs.getInt("SERIAL_NUM");
				defected=rs.getInt("DEFECTED");
				d=rs.getString("VALID_DATE");
			    try {
			        startDate = df.parse(d);
			    } catch (ParseException e) {
			        e.printStackTrace();
			    } 
				ins=new INS_product(prod,startDate,serial,defected);
				ins_products.add(ins);
			}
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ins_products;
		
	}
	
	public Product product_search(String cat_name,String sub_cat_name,String sub_sub_cat_name,String name,String producer){
		
		Product prod=new Product();
		String producer_name;
		try {
			String sql;
	    	Statement stmt;
			sql = "SELECT * FROM PRODUCT WHERE CATAGORY="+cat_name+"AND SUB_CATAGORY="+sub_cat_name+"AND SUB_SUB_CATAGORY="+sub_sub_cat_name+"AND NAME="+name+" AND PRODUCERNAME="+producer+";"; 
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			prod.set_id(rs.getInt ("ID"));
			prod.set_category(rs.getString("CATAGORY"));
			prod.set_sub_category(rs.getString("SUB_CATAGORY"));
			prod.set_sub_sub_category(rs.getString("SUB_SUB_CATAGORY"));
			prod.set_name(rs.getString("NAME"));
			producer_name=rs.getString("PRODUCERNAME");
			prod.set_producer(new Producer(producer_name));
			prod.set_price(rs.getFloat("PRICE"));
			prod.set_shelf_life(rs.getInt("SHELFLIFE"));
			prod.set_weight(rs.getFloat("WEIGHT"));
			prod.set_min_amount(rs.getInt("MIN_AMOUNT"));
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prod;
		 	
	}
	
	
	

}


