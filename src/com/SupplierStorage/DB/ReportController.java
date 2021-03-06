package com.SupplierStorage.DB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.SupplierStorage.BE.Producer;

public class ReportController {
	
	Connection c;
	
	public ReportController(Connection c){
		this.c=c;
	}
	
	
	
	public ArrayList<String> cat_search (){
		ArrayList<String> s_arr = new ArrayList<String>();
		try {
			String sql;
	    	Statement stmt;
			sql = "SELECT NAME_CAT FROM CATAGORY "; 
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				s_arr.add(rs.getString ("NAME_CAT")); 
			}
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s_arr;
		 	
	}
	
	
	
	public ArrayList<String> sub_cat_search (String cat_name){
		ArrayList<String> s_arr = new ArrayList<String>();
		try {
			String sql;
	    	Statement stmt;
			sql = "SELECT NAME_SCAT FROM SUB_CATAGORY JOIN CATAGORY WHERE SUB_CATAGORY.NAME_CAT=CATAGORY.NAME_CAT AND SUB_CATAGORY.NAME_CAT="+cat_name+ ";"; 
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				s_arr.add(rs.getString ("NAME_SCAT")); 
			}
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s_arr;
		 	
	}
	
	public ArrayList<String> sub_sub_cat_search (String cat_name,String sub_cat_name){
		ArrayList<String> s_arr = new ArrayList<String>();
		try {
			String sql;
	    	Statement stmt;
			sql = "SELECT NAME_SSCAT FROM SUB_SUB_CATAGORY JOIN SUB_CATAGORY JOIN CATAGORY WHERE SUB_CATAGORY.NAME_CAT=CATAGORY.NAME_CAT AND SUB_CATAGORY.NAME_CAT="+cat_name+"AND SUB_SUB_CATAGORY.NAME_SCAT="+sub_cat_name+"AND SUB_SUB_CATAGORY.NAME_SCAT=SUB_CATAGORY.NAME_SCAT ;"; 
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				s_arr.add(rs.getString ("NAME_SSCAT")); 
			}
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s_arr;
		 	
	}
	
	public ArrayList<String> product_search (String cat_name,String sub_cat_name,String sub_sub_cat_name){
		ArrayList<String> s_arr = new ArrayList<String>();
		try {
			String sql;
	    	Statement stmt;
			sql = "SELECT NAME FROM PRODUCT WHERE PRODUCT.CATAGORY="+cat_name+"AND PRODUCT.SUB_CATAGORY="+sub_cat_name+"AND PRODUCT.SUB_SUB_CATAGORY="+sub_sub_cat_name+";"; 
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				s_arr.add(rs.getString ("NAME")); 
			}
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s_arr;
		 	
	}
	
	
	public ArrayList<String> producer_product_search (String cat_name,String sub_cat_name,String sub_sub_cat_name,String name){
		ArrayList<String> s_arr = new ArrayList<String>();
		try {
			String sql;
	    	Statement stmt;
			sql = "SELECT PRODUCERNAME FROM PRODUCT WHERE PRODUCT.CATAGORY="+cat_name+"AND PRODUCT.SUB_CATAGORY="+sub_cat_name+"AND PRODUCT.SUB_SUB_CATAGORY="+sub_sub_cat_name+" AND PRODUCT.NAME="+name+";"; 
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				s_arr.add(rs.getString ("PRODUCERNAME")); 
			}
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s_arr;
		 	
	}
	
	
	
	public int product_search_ID (String cat_name,String sub_cat_name,String sub_sub_cat_name,String name,Producer p){
		int id=0;
		try {
			String sql;
	    	Statement stmt;
			sql = "SELECT ID FROM PRODUCT JOIN SUB_SUB_CATAGORY JOIN SUB_CATAGORY WHERE SUB_CATAGORY.NAME_CAT="+cat_name+"AND SUB_SUB_CATAGORY.NAME_SCAT="+sub_cat_name+"AND SUB_SUB_CATAGORY.NAME_SSCAT="+sub_sub_cat_name+"AND SUB_SUB_CATAGORY.NAME_SSCAT=PRODUCT.SUB_SUB_CATAGORY AND SUB_SUB_CATAGORY.NAME_SCAT= SUB_CATAGORY.NAME_SCAT AND PRODUCT.NAME="+name+" AND PRODUCERNAME="+p.getName()+";";; 
			stmt = c.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			id=rs.getInt ("ID"); 
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
		 	
	}
	
	
	public ArrayList<ArrayList<String>> get_report (ArrayList<String> parm,int count,String criter){
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		try {
			String sql;
	    	Statement stmt;
	    	ResultSet rs;
	    	if (criter.equals("ISSUE_CERTIFICATE")){
				sql = "SELECT * FROM ISSUE_CERTIFICATE;"; 
				stmt = c.createStatement();
				rs=stmt.executeQuery(sql);
				while(rs.next()) {
					ArrayList<String> line=new ArrayList<String>();
					line.add(rs.getString ("S_ID"));
					line.add(rs.getString ("S_DATE"));
					line.add(rs.getString ("S_P_ID"));
					line.add(rs.getString ("S_SERIAL_NUM"));
					line.add(rs.getString ("S_CATAGORY"));
					line.add(rs.getString ("S_SUB_CATAGORY"));
					line.add(rs.getString ("S_SUB_SUB_CATAGORY"));
					line.add(rs.getString ("S_NAME"));
					line.add(rs.getString ("S_PRODUCER"));
					line.add(rs.getString ("S_VALID_DATE"));
					arr.add(line);
				}
				stmt.executeUpdate(sql);
	    		
	    	}
	    	else if (criter.equals("DEFECTED")){
				sql = "SELECT * FROM INS_PRODUCT JOIN PRODUCT WHERE DEFECTED=1 AND INS_PRODUCT.ID= PRODUCT.ID ;"; 
				stmt = c.createStatement();
				rs=stmt.executeQuery(sql);
				while(rs.next()) {
					ArrayList<String> line=new ArrayList<String>();
					line.add(rs.getString ("ID"));
					line.add(rs.getString ("SERIAL_NUM"));
					line.add(rs.getString ("CATAGORY"));
					line.add(rs.getString ("SUB_CATAGORY"));
					line.add(rs.getString ("SUB_SUB_CATAGORY"));
					line.add(rs.getString ("NAME"));
					line.add(rs.getString ("PRODUCERNAME"));
					line.add(rs.getString ("PRICE"));
					line.add(rs.getString ("VALID_DATE"));
					line.add(rs.getString ("DEFECTED"));
					arr.add(line);
				}
	    		
	    	}
	    	else if(criter.equals("MIN_AMOUNT")){
				sql = "SELECT PRODUCT.ID,CATAGORY,SUB_CATAGORY,SUB_SUB_CATAGORY,NAME,PRODUCERNAME,MIN_AMOUNT, COUNT(PRODUCT.ID) AS AMOUNT FROM(INS_PRODUCT JOIN PRODUCT ON PRODUCT.ID=INS_PRODUCT.ID) GROUP BY PRODUCT.ID HAVING COUNT(PRODUCT.ID) < PRODUCT.MIN_AMOUNT;"; 
				stmt = c.createStatement();
				rs=stmt.executeQuery(sql);
				while(rs.next()) {
					ArrayList<String> line=new ArrayList<String>();
					line.add(rs.getString ("ID"));
					line.add(rs.getString ("CATAGORY"));
					line.add(rs.getString ("SUB_CATAGORY"));
					line.add(rs.getString ("SUB_SUB_CATAGORY"));
					line.add(rs.getString ("NAME"));
					line.add(rs.getString ("PRODUCERNAME"));
					line.add(rs.getString ("AMOUNT"));
					line.add(rs.getString ("MIN_AMOUNT"));
					arr.add(line);
				}	
				
	    	}
	    	else if(criter.equals("CATAGORY")){
	    		if(count==1){
					sql = "SELECT * FROM PRODUCT JOIN INS_PRODUCT WHERE CATAGORY="+parm.get(0)+" AND PRODUCT.ID=INS_PRODUCT.ID ;"; 
					stmt = c.createStatement();
					rs=stmt.executeQuery(sql);
					while(rs.next()) {
						ArrayList<String> line=new ArrayList<String>();
						line.add(rs.getString ("ID"));
						line.add(rs.getString ("SERIAL_NUM"));
						line.add(rs.getString ("CATAGORY"));
						line.add(rs.getString ("SUB_CATAGORY"));
						line.add(rs.getString ("SUB_SUB_CATAGORY"));
						line.add(rs.getString ("NAME"));
						line.add(rs.getString ("PRODUCERNAME"));
						line.add(rs.getString ("WEIGHT"));
						line.add(rs.getString ("MIN_AMOUNT"));
						line.add(rs.getString ("PRICE"));
						line.add(rs.getString ("VALID_DATE"));
						line.add(rs.getString ("DEFECTED"));
						arr.add(line);
					}
	    			
	    		}
	    		else if (count==2){
					sql = "SELECT * FROM PRODUCT JOIN INS_PRODUCT WHERE CATAGORY="+parm.get(0)+" AND SUB_CATAGORY="+parm.get(1)+" AND PRODUCT.ID=INS_PRODUCT.ID ;"; 
					stmt = c.createStatement();
					rs=stmt.executeQuery(sql);
					while(rs.next()) {
						ArrayList<String> line=new ArrayList<String>();
						line.add(rs.getString ("ID"));
						line.add(rs.getString ("SERIAL_NUM"));
						line.add(rs.getString ("CATAGORY"));
						line.add(rs.getString ("SUB_CATAGORY"));
						line.add(rs.getString ("SUB_SUB_CATAGORY"));
						line.add(rs.getString ("NAME"));
						line.add(rs.getString ("PRODUCERNAME"));
						line.add(rs.getString ("WEIGHT"));
						line.add(rs.getString ("MIN_AMOUNT"));
						line.add(rs.getString ("PRICE"));
						line.add(rs.getString ("VALID_DATE"));
						line.add(rs.getString ("DEFECTED"));
						arr.add(line);
					}
	    			
	    		}
	    		else if (count==3){
					sql = "SELECT * FROM PRODUCT JOIN INS_PRODUCT WHERE CATAGORY="+parm.get(0)+" AND SUB_CATAGORY="+parm.get(1)+" AND SUB_SUB_CATAGORY="+parm.get(2)+" AND PRODUCT.ID=INS_PRODUCT.ID ;"; 
					stmt = c.createStatement();
					rs=stmt.executeQuery(sql);
					while(rs.next()) {
						ArrayList<String> line=new ArrayList<String>();
						line.add(rs.getString ("ID"));
						line.add(rs.getString ("SERIAL_NUM"));
						line.add(rs.getString ("CATAGORY"));
						line.add(rs.getString ("SUB_CATAGORY"));
						line.add(rs.getString ("SUB_SUB_CATAGORY"));
						line.add(rs.getString ("NAME"));
						line.add(rs.getString ("PRODUCERNAME"));
						line.add(rs.getString ("WEIGHT"));
						line.add(rs.getString ("MIN_AMOUNT"));
						line.add(rs.getString ("PRICE"));
						line.add(rs.getString ("VALID_DATE"));
						line.add(rs.getString ("DEFECTED"));
						arr.add(line);
					}
	    			
	    		}
	    		
	    		else if (count==4){
					sql = "SELECT * FROM PRODUCT JOIN INS_PRODUCT WHERE CATAGORY="+parm.get(0)+" AND SUB_CATAGORY="+parm.get(1)+" AND SUB_SUB_CATAGORY="+parm.get(2)+" AND NAME="+parm.get(3)+" AND PRODUCT.ID=INS_PRODUCT.ID ;"; 
					stmt = c.createStatement();
					rs=stmt.executeQuery(sql);
					while(rs.next()) {
						ArrayList<String> line=new ArrayList<String>();
						line.add(rs.getString ("ID"));
						line.add(rs.getString ("SERIAL_NUM"));
						line.add(rs.getString ("CATAGORY"));
						line.add(rs.getString ("SUB_CATAGORY"));
						line.add(rs.getString ("SUB_SUB_CATAGORY"));
						line.add(rs.getString ("NAME"));
						line.add(rs.getString ("PRODUCERNAME"));
						line.add(rs.getString ("WEIGHT"));
						line.add(rs.getString ("MIN_AMOUNT"));
						line.add(rs.getString ("PRICE"));
						line.add(rs.getString ("VALID_DATE"));
						line.add(rs.getString ("DEFECTED"));
						arr.add(line);
					}
	    			
	    		}
	    		else if (count==5){
					sql = "SELECT * FROM PRODUCT JOIN INS_PRODUCT WHERE CATAGORY="+parm.get(0)+" AND SUB_CATAGORY="+parm.get(1)+" AND SUB_SUB_CATAGORY="+parm.get(2)+" AND NAME="+parm.get(3)+" AND PRODUCERNAME ="+parm.get(4)+"AND PRODUCT.ID=INS_PRODUCT.ID ;"; 
					stmt = c.createStatement();
					rs=stmt.executeQuery(sql);
					while(rs.next()) {
						ArrayList<String> line=new ArrayList<String>();
						line.add(rs.getString ("ID"));
						line.add(rs.getString ("SERIAL_NUM"));
						line.add(rs.getString ("CATAGORY"));
						line.add(rs.getString ("SUB_CATAGORY"));
						line.add(rs.getString ("SUB_SUB_CATAGORY"));
						line.add(rs.getString ("NAME"));
						line.add(rs.getString ("PRODUCERNAME"));
						line.add(rs.getString ("WEIGHT"));
						line.add(rs.getString ("PRICE"));
						line.add(rs.getString ("VALID_DATE"));
						line.add(rs.getString ("DEFECTED"));
						arr.add(line);
					}
	    			
	    		}
	    	}
	    	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
		 	
	}
	
	
	

}
