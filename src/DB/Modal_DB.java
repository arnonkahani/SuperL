package DB;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
	

public class Modal_DB {

     public Connection initialize_connection ()throws SQLException{    
    	 Connection c=null;
    	 try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
         c = DriverManager.getConnection("jdbc:sqlite:Storage_Modle.db");
		return c;
     }
     
     public int[] initialize_val(Connection c) throws SQLException{
    	 int [] initialize_values=new int [4];
    	 Statement stmt = null;
    	 ResultSet rs;
	     String sql;
		 sql = "SELECT ID FROM PRODUCT ORDER BY ID DESC LIMIT 1;" ;
	 	 stmt = c.createStatement();
		 rs=stmt.executeQuery(sql);
		 if(rs.next()){
			 initialize_values[0] = rs.getInt("ID")+1;
		 }
		 else{
			 initialize_values[0]=1;
		 }
		 sql = "SELECT SERIAL_NUM FROM INS_PRODUCT ORDER BY SERIAL_NUM DESC LIMIT 1;" ;
	 	 stmt = c.createStatement();
		 rs=stmt.executeQuery(sql);
		 if(rs.next()){
			 initialize_values[1] = rs.getInt("SERIAL_NUM")+1;
		 }
		 else{
			 initialize_values[1]=1;
		 }
		 sql = "SELECT S_ID FROM ISSUE_CERTIFICATE ORDER BY S_ID DESC LIMIT 1;" ;
	 	 stmt = c.createStatement();
		 rs=stmt.executeQuery(sql);
		 if(rs.next()){
			 initialize_values[2] = rs.getInt("S_ID")+1;
		 }
		 else{
			 initialize_values[2]=1;
		 }
		return initialize_values;
     }
     
     
     public Connection create_DB(){
    	    Connection c = null;
    	    Statement stmt = null;
    	    try {
    	      Class.forName("org.sqlite.JDBC");
    	      c = DriverManager.getConnection("jdbc:sqlite:Storage_Modle.db");
    	      System.out.println("Opened database successfully");

    	     stmt = c.createStatement();
    	     String sql = "CREATE TABLE PRODUCT " +
    	                   "(ID INT PRIMARY KEY     NOT NULL," +
    	                   " CATAGORY           CHARACTER(50)    NOT NULL, " + 
    	                   " SUB_CATAGORY           CHAR(50)    NOT NULL, " + 
    	                   " SUB_SUB_CATAGORY           CHAR(50)    NOT NULL, " + 
    	                   " NAME           CHAR(50)    NOT NULL, " + 
    	                   " AMOUNT            INT     NOT NULL, " + 
    	                   " PRODUCER        CHAR(50)   NOT NULL, " + 
    	                   " PRICE         REAL    NOT NULL, "+
    	                   " MIN_AMOUNT      INT  NOT NULL)"; 
    	      stmt.executeUpdate(sql);
    	      
    	       sql = "CREATE TABLE INS_PRODUCT " +
    	              "(SERIAL_NUM INT PRIMARY KEY     NOT NULL," +
    	              " ID         INT     NOT NULL, " + 
    	              " VALID_DATE   TEXT     NOT NULL, " + 
    	              " DEFECTED        BOOLEAN, " +
    	              "FOREIGN KEY(ID) REFERENCES PRODUCT(ID))"; 
    	      
    	      
    	      stmt.executeUpdate(sql);
    	      
    	      sql = "CREATE TABLE ISSUE_CERTIFICATE " +
    	              "(S_ID INT PRIMARY KEY     NOT NULL," +
    	              " S_P_ID         INT     NOT NULL, " +
    	              " S_DATE   TEXT      NOT NULL, " + 
    	              " S_SERIAL_NUM INT     NOT NULL, " +
    	              " S_CATAGORY           CHARACTER(50)    NOT NULL, " + 
    	              " S_SUB_CATAGORY           CHAR(50)    NOT NULL, " + 
    	              " S_SUB_SUB_CATAGORY           CHAR(50)    NOT NULL, " + 
    	              " S_NAME           CHAR(50)    NOT NULL, " + 
    	              " S_PRODUCER        CHAR(50)   NOT NULL, " + 
    	              " S_PRICE         REAL    NOT NULL, "+
    	              " S_VALID_DATE   TEXT     NOT NULL, " + 
    	              "FOREIGN KEY(S_SERIAL_NUM) REFERENCES INS_PRODUCT(SERIAL_NUM))"; 
    	      
    	      
    	      stmt.executeUpdate(sql);
    	      
    	      
    	      sql = "CREATE TABLE CATAGORY " +
    	              "(NAME_CAT CHAR(50) PRIMARY KEY     NOT NULL)"; 
    	      
    	      
    	      stmt.executeUpdate(sql);
    	      
    	      sql = "CREATE TABLE SUB_CATAGORY " +
    	              "(NAME_SCAT CHAR(50)      NOT NULL, " +
    	    		  "NAME_CAT CHAR(50)      NOT NULL,"+
    	              "PRIMARY KEY (NAME_SCAT, NAME_CAT)"+
    	    		  "FOREIGN KEY(NAME_CAT) REFERENCES CATAGORY(NAME_CAT))"; 
    	    		  
    	    stmt.executeUpdate(sql);
    	      
    	      sql = "CREATE TABLE SUB_SUB_CATAGORY " +
    	              "(NAME_SSCAT CHAR(50)      NOT NULL, " +
    	    		  "NAME_SCAT CHAR(50)      NOT NULL,"+
    	    		  "PRIMARY KEY (NAME_SSCAT, NAME_SCAT)"+
    	    		  "FOREIGN KEY(NAME_SCAT) REFERENCES SUB_CATAGORY(NAME_SCAT))"; 
    	      
    	      
    	      stmt.executeUpdate(sql);      
    	      stmt.close();
    	      
    	 
       } catch ( Exception e ) {
    	 System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
       }
			return c;
}
	
     
   
	    
}


