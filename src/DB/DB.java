package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class DB {
	
	private DAOFactory _factory;
	private Connection _c;
	public DB(){
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
		
		_factory = new DAOFactory(_c);
		
		//drop();
		//create();
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
	    stmt.executeUpdate(sql);
	    sql = "DROP TABLE IF EXISTS ORDER_PRODUCT";
	    stmt.executeUpdate(sql);
	    }catch(Exception e)
	    {
	    	System.out.println(e.getMessage());
	    }
		
	}

	public String[] getSearchFieldView(Class object_class){
		return _factory.create(object_class).getSearchFieldsView();
	}
	
	public <T> ArrayList<T> search(int[] columns,String[] values,Class object_class) throws SQLException{
		return _factory.create(object_class).search(columns, values);
	}

	public <T> void insert(T or) throws SQLException {
		
		_factory.create(or.getClass()).insert(or);
	}
	
	
	private void create(){
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
                  "(NAME VARCHAR(15)  NOT NULL, " + 
                  " SHELFLIFE      INT NOT NULL, " + 
                  " WEIGHT         REAL NOT NULL, " + 
                  " PRODUCERNAME VARCHAR(15),"
                  +"FOREIGN KEY (PRODUCERNAME) REFERENCES PRODUCER(NAME),"
                  + "PRIMARY KEY (NAME,PRODUCERNAME))"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE SUPPLIER_PRODUCT " +
                  "(SN INTEGER PRIMARY KEY AUTOINCREMENT," +
                  " PRODUCTNAME     VARCHAR(15)    NOT NULL, " + 
                  " SUPPLIERCN      VARCHAR(15)     NOT NULL, "
                  + "PRODUCTPRODUCERNAME VARCHAR(15) NOT NULL," + 
                  " FOREIGN KEY (PRODUCTNAME,PRODUCTPRODUCERNAME) REFERENCES PRODUCT(NAME,PRODUCERNAME),"
                  + "FOREIGN KEY (SUPPLIERCN) REFERENCES SUPPLIER(CN))"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE SUPPLIER " +
                  "(CN VARCHAR(15) PRIMARY KEY     NOT NULL," +
                  " NAME           VARCHAR(15)    NOT NULL, " + 
                  " PAYMANETMETHOD INT     NOT NULL, " + 
                  " BANKNUMBER        VACRHAR(15) NOT NULL)"; 
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
                  "SUPPLY_AGREEMENT_ID INTEGER  NOT NULL, " +
                  "WEIGHT REAL NOT NULL, "+
                  "DATE DATE NOT NULL, " + 
                  "PRICE REAL NOT NULL, "+
                  "FOREIGN KEY (SUPPLY_AGREEMENT_ID) REFERENCES SUPPLY_AGREEMENT(ID))";
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
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Tables were created successfully");
	  }

	
}
