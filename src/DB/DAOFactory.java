package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOFactory {

	private Connection _c;
	public DAOFactory(boolean first_time)
	{
			try {
			      Class.forName("org.sqlite.JDBC");
			      _c = DriverManager.getConnection("jdbc:sqlite:supllier.db");
			      Statement stmt = _c.createStatement();
				    String sql;
				    sql = "PRAGMA foreign_keys = ON";
				    stmt.execute(sql);
			    } catch ( Exception e ) {
			      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			      System.exit(0);
			    }
			    System.out.println("Opened supplier database successfully");
			
			if(first_time){
			drop();
			createTables();
			}
		
	}
	public DAO create(Class object_class)
	{
		DAO dao = null;
		switch(object_class.getName())
		{
		case "BE.Order":
			dao = new DAOOrder(_c);
			break;
		case "BE.Supplier":
			dao = new DAOSupplier(_c);
			break;
		case "BE.SupplierProduct":
			dao = new DAOSupplierProduct(_c);
			break;
		case "BE.SupplyAgreement":
			dao = new DAOSupplyAgreement(_c);
			break;
		case "BE.Product":
			dao = new DAOProduct(_c);
			break;
		case "BE.AgreementProduct":
			dao = new DAOSupplyAgreementProduct(_c);
			break;
		}
		if(dao == null)
			System.err.println("Error in factory");
		return dao;
	}
	
	
	
	private void drop() {
		
		Statement stmt = null;
	    try{
	    stmt = _c.createStatement();
	    String sql = "DROP TABLE IF EXISTS PRODUCER";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS PRODUCT";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS SUPPLIER_PRODUCT";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS SUPPLIER";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS CONTACT";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS SUPPLY_AGREEMENT";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS SUPPLY_AGREEMENT_PRODUCT";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS SUPPLY_AGREEMENT_PRODUCT_DISCOUNT";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS ORDERS";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS DISCOUNT";
	    stmt.executeUpdate(sql);
	    
	    sql = "DROP TABLE IF EXISTS ORDER_PRODUCT";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS CATAGORY";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS SUB_CATAGORY";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS SUB_SUB_CATAGORY";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS WEEKLY_ORDER";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS WEEKLY_ORDER_PRODUCT";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS ISSUE_CERTIFICATE";
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS INS_PRODUCT";
	    stmt.executeUpdate(sql);
	    
	    }catch(Exception e)
	    {
	    	System.out.println(e.getMessage());
	    }
		
	}

	
	private void createTables(){
	    Statement stmt = null;
	    try{
	    stmt = _c.createStatement();
	    String sql;
	    sql = "PRAGMA foreign_keys = ON";
	    stmt.execute(sql);
	    sql = "CREATE TABLE PRODUCER " +
	                   "(NAME VARCHAR(15) PRIMARY KEY  NOT NULL)"; 
	    stmt.executeUpdate(sql);
	      sql = "CREATE TABLE PRODUCT " +
                  "(ID INTEGER  PRIMARY KEY AUTOINCREMENT ,"+
	    		  "NAME VARCHAR(15)  NOT NULL, " + 
                  " SHELFLIFE      INT NOT NULL, " + 
                  " WEIGHT         REAL NOT NULL, " + 
                  " PRODUCERNAME VARCHAR(15),"+
                  " CATAGORY           CHARACTER(50)    NOT NULL, " + 
                  " SUB_CATAGORY           CHAR(50)    NOT NULL, " + 
                  " SUB_SUB_CATAGORY           CHAR(50)    NOT NULL, " + 
                  " PRICE         REAL    NOT NULL, "+
                  " MIN_AMOUNT      INT  NOT NULL,"+
                  "FOREIGN KEY (CATAGORY) REFERENCES CATAGORY(NAME_CAT),"+
                  "FOREIGN KEY (SUB_CATAGORY,CATAGORY) REFERENCES SUB_CATAGORY(NAME_SCAT,NAME_CAT),"+
                  "FOREIGN KEY (SUB_SUB_CATAGORY,SUB_CATAGORY) REFERENCES SUB_SUB_CATAGORY(NAME_SSCAT,NAME_SCAT),"+
                  "FOREIGN KEY (PRODUCERNAME) REFERENCES PRODUCER(NAME),"
                  + "UNIQUE (ID,NAME,PRODUCERNAME,CATAGORY,SUB_CATAGORY,SUB_SUB_CATAGORY))"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE SUPPLIER_PRODUCT " +
                  "(SN INTEGER PRIMARY KEY AUTOINCREMENT," +
                  "PRODUCT_ID INTEGER NOT NULL,"
                  + "SUPPLIERCN VARCHAR(15) NOT NULL,"+
                  " FOREIGN KEY (PRODUCT_ID) "
                  + "REFERENCES PRODUCT(ID),"
                  + "FOREIGN KEY (SUPPLIERCN) REFERENCES SUPPLIER(CN))"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE SUPPLIER " +
                  "(CN VARCHAR(15) PRIMARY KEY     NOT NULL," +
                  " NAME           VARCHAR(15)    NOT NULL, " + 
                  " PAYMANETMETHOD INT     NOT NULL, " + 
                  " BANKNUMBER        VACRHAR(15) NOT NULL,"+
                  "ADDRESS VARCHAR(20) NOT NULL)"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE CONTACT " +
                  "(TEL VARCHAR(10) PRIMARY KEY     NOT NULL," +
                  " NAME           VARCHAR(20)    NOT NULL, " + 
                  " EMAIL          VARCHAR(20)     NOT NULL, "
                  + "SUPPLIERCN VARCHAR(15) NOT NULL, " + 
                  "FOREIGN KEY (SUPPLIERCN) REFERENCES SUPPLIER(CN))"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE SUPPLY_AGREEMENT " +
                  "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                  "SUPPLYTYPE      VARCHAR(5)    NOT NULL, " + 
                  "DELEVERYTYPE    VARCHAR(5)     NOT NULL, "
                  +"DAY VARCHAR(7), " + 
                  "SUPPLIERCN VARCHAR(20) NOT NULL, " + 
                  "FOREIGN KEY (SUPPLIERCN) REFERENCES SUPPLIER(CN))";
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE SUPPLY_AGREEMENT_PRODUCT " +
                  "(SUPPLY_AGREEMENT_ID INTEGER  NOT NULL," +
                  "SUPPLIER_PRODUCT_SN  INTEGER   NOT NULL, " + 
                  "PRICE    REAL    NOT NULL, "+ 
                  "FOREIGN KEY (SUPPLY_AGREEMENT_ID) REFERENCES SUPPLY_AGREEMENT(ID), " + 
                  "FOREIGN KEY (SUPPLIER_PRODUCT_SN) REFERENCES SUPPLIER_PRODUCT(SN), "
                  + "PRIMARY KEY (SUPPLY_AGREEMENT_ID,SUPPLIER_PRODUCT_SN))";
	      stmt.executeUpdate(sql);
	    sql = "CREATE TABLE ORDERS " +
	    		"(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                  "SUPPLIER_CN VARCHAR(15)  NOT NULL, " +
                  "WEIGHT REAL NOT NULL, "+
                  "DATE DATE NOT NULL, " + 
                  "PRICE REAL NOT NULL, "+
                  "FOREIGN KEY (SUPPLIER_CN) REFERENCES SUPPLIER(CN))";
	      stmt.executeUpdate(sql);
	     sql = "CREATE TABLE ORDER_PRODUCT " +
                  "(ORDERID INTEGER NOT NULL," + 
                  "SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID INTEGER  NOT NULL, "
                  +"SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN INTEGER NOT NULL," +
                  "PRICE REAL NOT NULL, "
                  + "AMOUNT INTEGER NOT NULL, "
                  + "FOREIGN KEY (ORDERID) REFERENCES ORDERS(ID),"
                  + "FOREIGN KEY (SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID,SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN) REFERENCES SUPPLY_AGREEMENT_PRODUCT(SUPPLY_AGREEMENT_ID,SUPPLIER_PRODUCT_SN),"
                  + "PRIMARY KEY(ORDERID,SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID,SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN))";
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE SUPPLY_AGREEMENT_PRODUCT_DISCOUNT " +
                  "(SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID INTEGER  NOT NULL," +
                  "SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN   INTEGER  NOT NULL, " + 
                  "AMOUNT    INT   NOT NULL, "
                  +"PRECENT REAL NOT NULL, "+  
                  "FOREIGN KEY (SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID,SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN) REFERENCES SUPPLY_AGREEMENT_PRODUCT(SUPPLY_AGREEMENT_ID,SUPPLIER_PRODUCT_SN)"
                  + "PRIMARY KEY(SUPPLY_AGREEMENT_PRODUCT_AGREEMENT_ID,SUPPLY_AGREEMENT_PRODUCT_PRODUCT_SN,AMOUNT))";
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
	      
	      sql = "CREATE TABLE WEEKLY_ORDER " +
	              "(DAY CHAR(50)      NOT NULL,"
	              + "PRIMARY KEY (DAY))"; 
	      
	      
	      stmt.executeUpdate(sql);  
	      
	      sql = "CREATE TABLE WEEKLY_ORDER_PRODUCT " +
	              "(DAY CHAR(50)      NOT NULL,"+
	              "ID INTEGER      NOT NULL,"+
	              "NAME CHAR(50)      NOT NULL,"+
	              "PRODUCERNAME CHAR(50)      NOT NULL,"+
	              "CATAGORY CHAR(50)      NOT NULL,"+
	              "SUB_CATAGORY CHAR(50)      NOT NULL,"+
	              "SUB_SUB_CATAGORY CHAR(50)      NOT NULL,"+
	              "AMOUNT INT     NOT NULL,"+
	              "FOREIGN KEY (DAY) REFERENCES WEEKLY_ORDER(DAY),"+
	              "FOREIGN KEY (ID) REFERENCES PRODUCT(ID),"+
	              "PRIMARY KEY (DAY ,ID))"; 
	      
	      stmt.executeUpdate(sql); 
	      
	      sql = "CREATE TABLE INS_PRODUCT " +
	    		  "(SERIAL_NUM INTEGER  NOT NULL,"+
                  "ID INTEGER  NOT NULL,"+
                  " DEFECTED   INTEGER     NOT NULL, " + 
                  " VALID_DATE   TEXT     NOT NULL, " + 
                  "FOREIGN KEY (ID) REFERENCES PRODUCT(ID),"+
                  "PRIMARY KEY (SERIAL_NUM))"; 
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
	              " S_VALID_DATE   TEXT     NOT NULL, " + 
	              "FOREIGN KEY(S_SERIAL_NUM) REFERENCES INS_PRODUCT(SERIAL_NUM))"; 
	      
	      
	      stmt.executeUpdate(sql);
	      
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Tables were created successfully");
	  }

	
}
