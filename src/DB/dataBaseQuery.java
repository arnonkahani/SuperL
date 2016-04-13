package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class dataBaseQuery {

	private Connection c = null;
	
	
	/*public boolean producer(String [] value){
		Statement s = null;
		try{
			s=c.createStatement();
			//all of the query comes to this string
			String sql="SELECT NAME FROM PEODUCER WHERE NAME =" + value[0];
			sql=insert
			//quary
			ResultSet rs = s.executeQuery(sql);
			if(rs.getString(0)==null);
			//maybe need to getstring 1
			rs.moveToInsertRow();
			//insert return 0 if failed else 
			s.executeUpdate(sql);
			אם 0 לא הכניס ואחרת איזה שורה מכניס
			System.out.println("the producer was added to the table");
			s.close();
			
			
		}
		catch(Exception e){
			if(e.getMessage().startsWith("UNIQUE"))
	        {
	           String msg = e.getMessage();
	           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	           System.out.println("There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase());
	        }
	        else if(e.getMessage().startsWith("FOREIGN"))
	        {
	                  String msg = e.getMessage();
	                  String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	                  System.out.println("There is a problem with relations");
	        }
	        else{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	        }
		}
	}*/
	
	public boolean insertProducer(String [] value){
		Statement s = null;
		try{
			s=c.createStatement();
			String value_str="";
				value_str=value[0];
			String sql="INSERT INTO PRODUCER (NAME) VALUES(" + value_str + ")";
			s.executeUpdate(sql);
			System.out.println("the producer was added to the table");
		}
		catch(Exception e){
			if(e.getMessage().startsWith("UNIQUE"))
	        {
	           String msg = e.getMessage();
	           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	           System.out.println("There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase());
	        }
	        else if(e.getMessage().startsWith("FOREIGN"))
	        {
	                  String msg = e.getMessage();
	                  String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	                  System.out.println("There is a problem with relations");
	        }
	        else{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	        }
		}
		return true;
	}
	
	public boolean insertProduct(String [] value){
		Statement s = null;
		try{
			s=c.createStatement();
			String value_str="";
			for(int i=0;i<value_str.length();i++){
				value_str=value_str + value[i] + ", ";
			}
			String sql="INSERT INTO PRODUCT (NAME,VALIDDATE,WEIGHT,PRODUCERNAME) VALUES(" + value_str + ")";
			s.executeUpdate(sql);
			System.out.println("the product was added to the table");
		}
		catch(Exception e){
			if(e.getMessage().startsWith("UNIQUE"))
	        {
	           String msg = e.getMessage();
	           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	           System.out.println("There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase());
	        }
	        else if(e.getMessage().startsWith("FOREIGN"))
	        {
	                  String msg = e.getMessage();
	                  String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	                  System.out.println("There is a problem with relations");
	        }
	        else{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	        }
		}
		return true;
	}
	
	public boolean insertProductSupplier(String [] value){
		Statement s = null;
		try{
			s=c.createStatement();
			String value_str="";
			for(int i=0;i<value_str.length();i++){
				value_str=value_str + value[i] + ", ";
			}
			String sql="INSERT INTO PRODUCT_SUPPLIER (PID,SUPPLIERCN) VALUES(" + value_str + ")";
			s.executeUpdate(sql);
			System.out.println("the product was added to supplier ");
		}
		catch(Exception e){
			if(e.getMessage().startsWith("UNIQUE"))
	        {
	           String msg = e.getMessage();
	           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	           System.out.println("There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase());
	        }
	        else if(e.getMessage().startsWith("FOREIGN"))
	        {
	                  String msg = e.getMessage();
	                  String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	                  System.out.println("There is a problem with relations");
	        }
	        else{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	        }
		}
		return true;
	}
	
	
	public boolean insertContact(String [] value){
		Statement s = null;
		try{
			s=c.createStatement();
			String value_str="";
			for(int i=0;i<value_str.length();i++){
				value_str=value_str + value[i] + ", ";
			}
			String sql="INSERT INTO CONTACTS (TEL,NAME,EMAIL,SUPPLIERCN) VALUES(" + value_str + ")";
			s.executeUpdate(sql);
			System.out.println("the CONTACT was added to the table ");
		}
		catch(Exception e){
			if(e.getMessage().startsWith("UNIQUE"))
	        {
	           String msg = e.getMessage();
	           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	           System.out.println("There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase());
	        }
	        else if(e.getMessage().startsWith("FOREIGN"))
	        {
	                  String msg = e.getMessage();
	                  String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	                  System.out.println("There is a problem with relations");
	        }
	        else{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	        }
		}
		return true;
	}
	
	public boolean insertSupplier(String [] value){
		Statement s = null;
		try{
			s=c.createStatement();
			String value_str="";
			for(int i=0;i<value_str.length();i++){
				value_str=value_str + value[i] + ", ";
			}
			String sql="INSERT INTO SUPPLIER (CN,NAME,PAYMENTMETHOD,BANKNUMBER) VALUES(" + value_str + ")";
			s.executeUpdate(sql);
			System.out.println("the supplier was added to the table ");
		}
		catch(Exception e){
			if(e.getMessage().startsWith("UNIQUE"))
	        {
	           String msg = e.getMessage();
	           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	           System.out.println("There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase());
	        }
	        else if(e.getMessage().startsWith("FOREIGN"))
	        {
	                  String msg = e.getMessage();
	                  String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	                  System.out.println("There is a problem with relations");
	        }
	        else{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	        }
		}
		return true;
	}
	
	public boolean insertOrders(String [] value){
		Statement s = null;
		try{
			s=c.createStatement();
			String value_str="";
			for(int i=0;i<value_str.length();i++){
				value_str=value_str + value[i] + ", ";
			}
			String sql="INSERT INTO ORDERS (SUPPLYID,WEIGHT,DATE,PRICE) VALUES(" + value_str + ")";
			s.executeUpdate(sql);
			System.out.println("the order was added to the table ");
		}
		catch(Exception e){
			if(e.getMessage().startsWith("UNIQUE"))
	        {
	           String msg = e.getMessage();
	           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	           System.out.println("There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase());
	        }
	        else if(e.getMessage().startsWith("FOREIGN"))
	        {
	                  String msg = e.getMessage();
	                  String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	                  System.out.println("There is a problem with relations");
	        }
	        else{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	        }
		}
		return true;
	}
	public boolean insertSupplyAgreementProduct(String [] value){
		Statement s = null;
		try{
			s=c.createStatement();
			String value_str="";
			for(int i=0;i<value_str.length();i++){
				value_str=value_str + value[i] + ", ";
			}
			String sql="INSERT INTO SUPPLY_AGREEMENTS_PRODUCT (PIDSUPPLIER,SUPPLYID,PRICE) VALUES(" + value_str + ")";
			s.executeUpdate(sql);
			System.out.println("the supply agreement to product was added to the table ");
		}
		catch(Exception e){
			if(e.getMessage().startsWith("UNIQUE"))
	        {
	           String msg = e.getMessage();
	           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	           System.out.println("There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase());
	        }
	        else if(e.getMessage().startsWith("FOREIGN"))
	        {
	                  String msg = e.getMessage();
	                  String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	                  System.out.println("There is a problem with relations");
	        }
	        else{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	        }
		}
		return true;
	}
	
	public boolean insertSupplyAgreements(String [] value){
		Statement s = null;
		try{
			s=c.createStatement();
			String value_str="";
			for(int i=0;i<value_str.length();i++){
				value_str=value_str + value[i] + ", ";
			}
			String sql="INSERT INTO SUPPLY_AGREEMENTS (SUPPLYTYPE,DELIVERYTYPE,DAY,SUPPLIERCN) VALUES(" + value_str + ")";
			s.executeUpdate(sql);
			System.out.println("the supply agreement was added to the table ");
		}
		catch(Exception e){
			if(e.getMessage().startsWith("UNIQUE"))
	        {
	           String msg = e.getMessage();
	           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	           System.out.println("There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase());
	        }
	        else if(e.getMessage().startsWith("FOREIGN"))
	        {
	                  String msg = e.getMessage();
	                  String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	                  System.out.println("There is a problem with relations");
	        }
	        else{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	        }
		}
		return true;
	}
	public boolean insertReceiptProductsSupplier(String [] value){
		Statement s = null;
		try{
			s=c.createStatement();
			String value_str="";
			for(int i=0;i<value_str.length();i++){
				value_str=value_str + value[i] + ", ";
			}
			String sql="INSERT INTO RECEIPT_PRODUCTS_SUPPLIER (ORDERID,SUPPLYID,PIDSUPPLIER,PRICE,AMOUNT) VALUES(" + value_str + ")";
			s.executeUpdate(sql);
			System.out.println("the receipt was added to the table ");
		}
		catch(Exception e){
			if(e.getMessage().startsWith("UNIQUE"))
	        {
	           String msg = e.getMessage();
	           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	           System.out.println("There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase());
	        }
	        else if(e.getMessage().startsWith("FOREIGN"))
	        {
	                  String msg = e.getMessage();
	                  String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	                  System.out.println("There is a problem with relations");
	        }
	        else{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	        }
		}
		return true;
	}
	public boolean insertDiscountProductsSupplier(String [] value){
		Statement s = null;
		try{
			s=c.createStatement();
			String value_str="";
			for(int i=0;i<value_str.length();i++){
				value_str=value_str + value[i] + ", ";
			}
			String sql="INSERT INTO DISCOUNT_PRODUCTS_SUPPLIER (PIDSUPPLIER,SUPPLYID,AMOUNT,PRECENT) VALUES(" + value_str + ")";
			s.executeUpdate(sql);
			System.out.println("the discount of product was added to the table ");
		}
		catch(Exception e){
			if(e.getMessage().startsWith("UNIQUE"))
	        {
	           String msg = e.getMessage();
	           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	           System.out.println("There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase());
	        }
	        else if(e.getMessage().startsWith("FOREIGN"))
	        {
	                  String msg = e.getMessage();
	                  String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
	                  System.out.println("There is a problem with relations");
	        }
	        else{
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	        }
		}
		return true;
	}
}
