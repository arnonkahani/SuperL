package DB;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import BE.INS_product;
import BE.OrderProduct;
import BE.Producer;
import BE.Product;
import BE.SupplyAgreement;
import BE.SupplyAgreement.Day;
import BE.WeeklyOrder;
import BE.issue_certificate;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class storage_controller {
	int index_ins_product;
	int index_issue_certificate;
	Connection c;
	
	
	public storage_controller(int index_ins_product,int index_issue_certificate,Connection c) {
		this.index_ins_product=index_ins_product;
		this.index_issue_certificate=index_issue_certificate;
		this.c=c;
	}


	public void getSupply(ArrayList<INS_product> products){
	    
		try {
	    	
	    	String sql;
	    	Statement stmt;
	    	
	    	for(int j=0; j<products.size(); j++){
				c.setAutoCommit(false);
				 sql = "INSERT INTO INS_PRODUCT (SERIAL_NUM,ID,VALID_DATE,DEFECTED) " +
	                   "VALUES ('"+index_ins_product+"','"+products.get(j).get_id()+ "','"+products.get(j).getValid_date()+"','"+products.get(j).isDefected()+"');"; 
				 stmt = c.createStatement();
			
				stmt.executeUpdate(sql);

				index_ins_product++;
			}
			
		    c.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  void remove_from_storage(INS_product ins,issue_certificate issue){
		try {
	    	String sql;
	    	Statement stmt;
	    	int curr_amount=get_Amount(ins.get_id());
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
				stmt.executeUpdate(sql);
				create_issue(issue);
			} catch (SQLException e) {
				 System.err.println("Product is not in the storage" );
			}
	    curr_amount=curr_amount-1;
	    c.commit();
			
		} catch (SQLException e) {
			 System.err.println("The Amount is less then zero" );
		}
		

	
	}
	
	public void create_issue (issue_certificate issue){
		
		try {
			String sql;
	    	Statement stmt;
			sql = "INSERT INTO ISSUE_CERTIFICATE (S_ID,S_DATE,S_P_ID,S_SERIAL_NUM,S_CATAGORY,S_SUB_CATAGORY,S_SUB_SUB_CATAGORY,S_NAME,S_PRODUCER,S_VALID_DATE) " +
	                "VALUES ('"+issue.getS_id()+"','"+issue.getS_date()+"','"+issue.getS_p_id()+"','"+issue.getS_serial_num()+"','"+issue.getS_cat()+"','"+issue.getS_sub_cat()+"','"+issue.getS_sub_cat()+"','"+issue.getS_name()+"','"+issue.getS_producer()+"','"+issue.getS_valid_date()+"');"; 
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			c.commit();
			
			
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
	
	public void create_weekly_order(WeeklyOrder order){
		
		try {
			String sql;
	    	Statement stmt;
			sql = "INSERT INTO WEEKLY_ORDER (DAY) " +
	                "VALUES ('"+order.getDay()+"');"; 
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			c.commit();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String sql;
	    	Statement stmt;
	    	for ( Product key : order.getProducts().keySet() ) {
				sql = "INSERT INTO WEEKLY_ORDER_PRODUCT (DAY,ID,AMOUNT) " +
		                "VALUES ("+order.getDay()+","+ key.get_id()+","+order.getProducts().get(key)+");"; 
		    	stmt = c.createStatement();
		    	stmt.executeUpdate(sql);
		    	c.commit();        
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
			sql = "DELETE from WEEKLY_ORDER_PRODUCT where DAY="+day+";";
			stmt = c.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			 System.err.println("");
		}
		
		try{
			String sql;
	    	Statement stmt;
			sql = "DELETE from WEEKLY_ORDER where DAY="+day+";";
			stmt = c.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			 System.err.println("");
		}
		
		
	}
	
	public int get_Amount (int id){
		int curr_amount=0;
		try {
			String sql;
			Statement stmt;
			sql = "SELECT COUNT() FROM INS_PRODUCT WHERE ID="+id+";" ;
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			curr_amount=rs.getInt(0);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	  return curr_amount;	
	}
	
	public WeeklyOrder get_daily_order (int curr_day){
		WeeklyOrder weekly=new WeeklyOrder();
		HashMap<Product,Integer> products= new HashMap();
		Product p=new Product();
		int amount=0;
		try {			
			String sql;
			Statement stmt;
			sql = "SELECT * FROM WEEKLY_ORDER_PRODUCT WHERE DAY="+curr_day+";" ;
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				p.set_id(rs.getInt("ID"));
				amount = rs.getInt("AMOUNT");
				products.put(p, amount);
			}
			weekly.setDay(SupplyAgreement.Day.values()[curr_day-1]);
			weekly.setProducts(products);
		
		} catch (SQLException e) {
			
		}
		return weekly;
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

}


