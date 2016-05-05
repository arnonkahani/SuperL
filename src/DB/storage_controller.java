package DB;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import BE.INS_product;
import BE.OrderProduct;
import BE.Product;
import BE.SupplyAgreement.Day;
import BE.WeeklyOrder;
import BE.issue_certificate;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class storage_controller {
	int index_product;
	int index_ins_product;
	int index_issue_certificate;
	
	
	public storage_controller(int index_product,int index_ins_product,int index_issue_certificate) {
		this.index_product=index_product;
		this.index_ins_product=index_ins_product;
		this.index_issue_certificate=index_issue_certificate;
	}


	public void getSupply(ArrayList<INS_product> products,Connection c){
	    
		try {
	    	
	    	String sql;
	    	Statement stmt;
	    	
	    	for(int j=0; j<products.size(); j++){
				c.setAutoCommit(false);
				 sql = "INSERT INTO INS_PRODUCT (SERIAL_NUM,ID,VALID_DATE,DEFECTED) " +
	                   "VALUES ('"+index_ins_product+"','"+id+ "','" +ins.getValid_date()+"','"+ins.isDefected()+"');"; 
				 stmt = c.createStatement();
			
				stmt.executeUpdate(sql);

				index_ins_product++;
			}
			
			
			sql =  "SELECT AMOUNT FROM PRODUCT WHERE ID="+id+";" ;
			stmt = c.createStatement();
			rs=stmt.executeQuery(sql);
			int curr_amount = rs.getInt("AMOUNT");
			curr_amount=curr_amount+amount;
		     
			
			sql = "UPDATE PRODUCT set AMOUNT ="+curr_amount+" where ID="+id+";";
			stmt = c.createStatement();
		    stmt.executeUpdate(sql);
		    c.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  void remove_from_storage(Product prod, INS_product ins,issue_certificate issue,Connection c){
		try {
	    	String sql;
	    	Statement stmt;
	    	int id=0;
	    	int curr_amount=0;
	    		c.setAutoCommit(false);
				sql = "SELECT * FROM PRODUCT JOIN INS_PRODUCT WHERE CATAGORY="+prod.catagory+" AND SUB_CATAGORY="+prod.s_catagory+" AND SUB_SUB_CATAGORY="+prod.ss_catagory+" AND NAME="+prod.name+" AND INS_PRODUCT.ID = PRODUCT.ID ORDER BY VALID_DATE ASC LIMIT 1;" ;
				stmt = c.createStatement();
				ResultSet rs=stmt.executeQuery(sql);
				issue.setS_id(index_issue_certificate);
				index_issue_certificate++;
				issue.setS_serial_num(rs.getInt("SERIAL_NUM"));
				issue.setS_date(new Date());
				issue.setS_p_id(rs.getInt("ID"));
				issue.setS_cat(rs.getString("CATAGORY"));
				issue.setS_sub_cat(rs.getString("SUB_CATAGORY"));
				issue.setS_sub_sub_cat(rs.getString("SUB_SUB_CATAGORY"));
				issue.setS_name(rs.getString("NAME"));
				issue.setS_producer(rs.getString("PRODUCER"));
				issue.setS_price(rs.getDouble("PRICE"));
				issue.setS_valid_date(rs.getString("VALID_DATE"));
				id =rs.getInt("ID");
				curr_amount = rs.getInt("AMOUNT");
				try{
					sql = "DELETE from INS_PRODUCT where SERIAL_NUM="+issue.getS_serial_num()+";";
					stmt.executeUpdate(sql);
					create_issue(issue, c);
				} catch (SQLException e) {
					 System.err.println("Product is not in the storage" );
				}
	    	curr_amount=curr_amount-1;
	    	sql = "UPDATE PRODUCT set AMOUNT ="+curr_amount+" where ID="+id+";";
			stmt = c.createStatement();
		    stmt.executeUpdate(sql);
		    c.commit();
			
		} catch (SQLException e) {
			 System.err.println("The Amount is less then zero" );
		}
		

	
	}
	
	public void create_issue (issue_certificate issue, Connection c){
		
		try {
			String sql;
	    	Statement stmt;
			sql = "INSERT INTO ISSUE_CERTIFICATE (S_ID,S_DATE,S_P_ID,S_SERIAL_NUM,S_CATAGORY,S_SUB_CATAGORY,S_SUB_SUB_CATAGORY,S_NAME,S_PRODUCER,S_PRICE,S_VALID_DATE) " +
	                "VALUES ('"+issue.getS_id()+"','"+issue.getS_date()+"','"+issue.getS_p_id()+"','"+issue.getS_serial_num()+"','"+issue.getS_cat()+"','"+issue.getS_sub_cat()+"','"+issue.getS_sub_cat()+"','"+issue.getS_name()+"','"+issue.getS_producer()+"','"+issue.getS_price()+"','"+issue.getS_valid_date()+"');"; 
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			c.commit();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

			
		
	}
	
	
	
	public int get_INS_serial (Product prod, Connection c){
		
		int serial = 0;
		try {
			String sql;
			Statement stmt;
			sql = "SELECT SERIAL_NUM FROM INS_PRODUCT WHERE ID="+prod.id+" ORDER BY VALID_DATE ASC LIMIT 1;" ;
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			serial=rs.getInt("SERIAL_NUM");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return serial;

	}
	
	public Date get_INS_valid (Product prod, Connection c){
		
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
	
	public int get_INS_defected (Product prod, Connection c){
		
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
	
	public void add_new_product(String cat, String sub_cat, String sub_sub_cat,String name, String producer,double price ,int min_amount, Connection c){
		
		try {

			String sql;
	    	Statement stmt;
			sql = "INSERT INTO PRODUCT (ID,CATAGORY,SUB_CATAGORY,SUB_SUB_CATAGORY,NAME,AMOUNT,PRODUCER,PRICE,MIN_AMOUNT) " +
	                "VALUES ("+index_product+","+cat+ "," +sub_cat+","+sub_sub_cat+","+name+","+0+","+producer+","+price+","+min_amount+");"; 
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			index_product++;
			c.commit();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public int Check_amount (Product prod, Connection c,int amount){
		
		int ans=2;
		int curr_amount=0;
		int min=0;
		try {
			String sql;
			Statement stmt;
			sql = "SELECT AMOUNT,MIN_AMOUNT FROM PRODUCT WHERE ID="+prod.get_id()+";" ;
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			curr_amount=rs.getInt("AMOUNT");
			min=rs.getInt("MIN_AMOUNT");
			
			
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
	
	public void create_weekly_order(WeeklyOrder order,Connection c){
		
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
				sql = "INSERT INTO WEEKLY_ORDER_PRODUCT (DAY,ID,NAME,PRODUCERNAME,CATAGORY,SUB_CATAGORY,SUB_SUB_CATAGORY,AMOUNT) " +
		                "VALUES ("+order.getDay()+","+ key.get_id()+","+key.get_name()+","+key.get_producer()+","+key.get_category()+","+key.get_sub_categoryname_scat()+","+key.get_sub_sub_category()+","+order.getProducts().get(key)+");"; 
		    	stmt = c.createStatement();
		    	stmt.executeUpdate(sql);
		    	c.commit();        
	    	}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void remove_weekly_order(Day day, Connection c){
		
		try{
			String sql;
	    	Statement stmt;
			sql = "DELETE from WEEKLY_ORDER_PRODUCT where DAY="+day+";";
			stmt = c.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			 System.err.println("" );
		}
		
		try{
			String sql;
	    	Statement stmt;
			sql = "DELETE from WEEKLY_ORDER where DAY="+day+";";
			stmt = c.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			 System.err.println("" );
		}
		
		
	}

}


