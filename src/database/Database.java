package database;

import java.sql.*;

import logic.Supplier;

public class Database {

	private Connection c = null;
    

	public Database() {
		try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:supllier.db");
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Opened supplier database successfully");
		//drop();
		create();
	}

	public void insert(Supplier sp) {
		// TODO Auto-generated method stub
		
	}
	private void drop(){
		String [] tables = {"PRODUCT","PRODUCER","PRODUCT_SUPPLIER","SUPPLIER","SUPPLY_AGREEMENT","CONTACT","SUPPLY_AGREEMENT_PRODUCT","ORDERS","ORDER_PRODUCT","DISCOUNT"};
		Statement stmt = null;
	    try{
	    stmt = c.createStatement();
	    for (int i = 0; i < tables.length; i++) {
	    	String sql = "DROP TABLE "+ tables[i]; 
		     stmt.executeUpdate(sql);
		}
	    
	     stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Tables were dropped successfully");
	  
	}
	
	private void create(){
	    Statement stmt = null;
	    try{
	    stmt = c.createStatement();
	    String sql = "CREATE TABLE PRODUCER " +
	                   "(NAME VARCHAR(20) PRIMARY KEY  NOT NULL)"; 
	    stmt.executeUpdate(sql);
	      sql = "CREATE TABLE PRODUCT " +
                  "(PID INTEGER PRIMARY KEY AUTOINCREMENT," +
                  " NAME           VARCHAR(15)    NOT NULL, " + 
                  " VALIDDATE      INT     NOT NULL, " + 
                  " WEIGHT         INT, " + 
                  "PRODUCERNAME VARCHAR(20) NOT NULL,"
                  +"FOREIGN KEY (PRODUCERNAME) REFERENCES PRODUCER(NAME)) "; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE PRODUCT_SUPPLIER " +
                  "(PIDSUPPLIER INTEGER PRIMARY KEY AUTOINCREMENT," +
                  " PID           VARCHAR(20)    NOT NULL, " + 
                  " SUPPLIERCN      VARCHAR(20)     NOT NULL, " + 
                  " FOREIGN KEY (PID) REFERENCES PRODUCT(PID), "
                  + "FOREIGN KEY (SUPPLIERCN) REFERENCES SUPPLIER(CN))"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE SUPPLIER " +
                  "(CN VARCHAR(20) PRIMARY KEY     NOT NULL," +
                  " NAME           VARCHAR(20)    NOT NULL, " + 
                  " PAYMANETMETHOD INT     NOT NULL, " + 
                  " BANKNUMBER        VACRHAR(20))"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE CONTACT " +
                  "(TEL VARCHAR(10) PRIMARY KEY     NOT NULL," +
                  " NAME           VARCHAR(10)    NOT NULL, " + 
                  " EMAIL          VARCHAR(20)     NOT NULL, "
                  + "SUPPLIERCN VARCHAR(20) NOT NULL, " + 
                  "FOREIGN KEY (SUPPLIERCN) REFERENCES SUPPLIER(CN))"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE SUPPLY_AGREEMENT " +
                  "(SUPPLYID INTEGER PRIMARY KEY AUTOINCREMENT," +
                  "SUPPLYTYPE      VARCHAR(5)    NOT NULL, " + 
                  "DELEVERYTYPE    VARCHAR(5)     NOT NULL, "
                  +"DAY VARCHAR(7), " + 
                  "SUPPLIERCN VARCHAR(20) NOT NULL, " + 
                  "FOREIGN KEY (SUPPLIERCN) REFERENCES SUPPLIER(CN))";
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE SUPPLY_AGREEMENT_PRODUCT " +
                  "(PIDSUPPLIER INTEGER  NOT NULL," +
                  "SUPPLYID      INTEGER   NOT NULL, " + 
                  "PRICE    FLOAT    NOT NULL, "+ 
                  "FOREIGN KEY (SUPPLYID) REFERENCES SUPPLY_AGREEMENT(SUPPLYID), " + 
                  "FOREIGN KEY (PIDSUPPLIER) REFERENCES PRODUCT_SUPPLIER(PIDSUPPLIER), "
                  + "PRIMARY KEY (PIDSUPPLIER,SUPPLYID))";
	      stmt.executeUpdate(sql);
	    sql = "CREATE TABLE ORDERS " +
                  "(ORDERID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                  "SUPPLYID   INTEGER   NOT NULL," +
                  "WEIGHT    FLOAT     NOT NULL, "
                  +"DATE DATETIME NOT NULL, " + 
                  "PRICE FLOAT NOT NULL, "+
                  "FOREIGN KEY (SUPPLYID) REFERENCES SUPPLY_AGREEMENT(SUPPLYID))";
	      stmt.executeUpdate(sql);
	     sql = "CREATE TABLE ORDER_PRODUCT " +
                  "(ORDERID INTEGER NOT NULL," + 
                  "SUPPLYAGREEMENTID    INTEGER  NOT NULL, "
                  +"PIDSUPPLIER DATETIME NOT NULL, " + 
                  "PRICE FLOAT NOT NULL, "
                  + "AMOUNT INTEGER NOT NULL, "
                  + "FOREIGN KEY (ORDERID) REFERENCES ORDERS(ORDERID),"
                  + "FOREIGN KEY (SUPPLYAGREEMENTID) REFERENCES SUPPLY_AGREEMENT_PRODUCT(SUPPLYID), " + 
                  "FOREIGN KEY (PIDSUPPLIER) REFERENCES SUPPLY_AGREEMENT_PRODUCT(PIDSUPPLIER),"
                  + "PRIMARY KEY(ORDERID,SUPPLYAGREEMENTID,PIDSUPPLIER))";
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE DISCOUNT " +
                  "(PIDSUPPLIER INTEGER  NOT NULL," +
                  "SUPPLYAGREEMENTID   INTEGER  NOT NULL, " + 
                  "AMOUNT    INT   NOT NULL, "
                  +"PRECENT FLOAT NOT NULL, "
                  + "FOREIGN KEY (PIDSUPPLIER) REFERENCES SUPPLY_AGREEMENT_PRODUCT(PIDSUPPLIER), " +  
                  "FOREIGN KEY (SUPPLYAGREEMENTID) REFERENCES SUPPLY_AGREEMENT(SUPPLYID)"
                  + "PRIMARY KEY(PIDSUPPLIER,SUPPLYAGREEMENTID,AMOUNT))";
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Tables were created successfully");
	  }
	

	
}
