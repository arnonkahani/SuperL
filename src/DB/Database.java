package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import BE.Order;
import BE.Product;
import BE.AgreementProduct;
import BE.Supplier;
import BE.SupplyAgreement;

public class Database {

	private Connection c = null;
    private DBquery _dbQuery;
    
	public Database() {
		createTable();
		try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:supllier.db");
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Opened supplier database successfully");
		drop();
		create();
		insert(null);
	}


	public String[] getSearchFieldView(Class object_class){
		return _dbQuery.getSearchFieldsView(object_class);
		
	}
	
	public <T> ArrayList<T> search(ArrayList<T> query_result,int[] fileds,String[] query,Class object_class) throws SQLException{
		return _dbQuery.search(query_result,fileds,query,object_class);
	}
	
	public void insertProduct(String[] values) {
		Statement stmt = null;
	    try{
	    stmt = c.createStatement();
	    String values_str = "";
	    for (int i = 0; i < values.length; i++) 
			values_str = values_str + values[i] + ", ";
	    String sql = "INSERT INTO PRODUCT (NAME,VALIDDATE,WEIGHT,PRODUCERNAME) VALUES ("+ values_str + ")"; 
		stmt.executeUpdate(sql);
	    stmt.close();
	     c.close();
	    } catch ( Exception e ) {
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
	    System.out.println("Insert successfully");
	    
	    
	    
		
	}
	
	public boolean exist(Supplier sp)
	{
		return true;
		//exist("SUPPLIER",{"CN"},sp.getSupID())
	}
	
	private void drop(){
		String [] tables = {"PRODUCER","PRODUCT","PRODUCT_SUPPLIER","SUPPLIER","SUPPLY_AGREEMENT","CONTACT","SUPPLY_AGREEMENT_PRODUCT","ORDERS","ORDER_PRODUCT","DISCOUNT"};
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
	    String sql = "PRAGMA foreign_keys = ON";
	    stmt.execute(sql);
	    sql = "CREATE TABLE PRODUCER " +
	                   "(NAME TEXT PRIMARY KEY  NOT NULL)"; 
	    stmt.executeUpdate(sql);
	      sql = "CREATE TABLE PRODUCT " +
                  "(PID INTEGER PRIMARY KEY AUTOINCREMENT," +
                  " NAME           VARCHAR(15)  NOT NULL, " + 
                  " VALIDDATE      INT     NOT NULL, " + 
                  " WEIGHT         INT NOT NULL, " + 
                  "PRODUCERNAME TEXT,"
                  +"FOREIGN KEY (PRODUCERNAME) REFERENCES PRODUCER(NAME))"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE PRODUCT_SUPPLIER " +
                  "(PIDSUPPLIER INTEGER PRIMARY KEY AUTOINCREMENT," +
                  " PID           INTEGER    NOT NULL, " + 
                  " SUPPLIERCN      VARCHAR(10)     NOT NULL, " + 
                  " FOREIGN KEY (PID) REFERENCES PRODUCT(PID), "
                  + "FOREIGN KEY (SUPPLIERCN) REFERENCES SUPPLIER(CN))"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE SUPPLIER " +
                  "(CN VARCHAR(10) PRIMARY KEY     NOT NULL," +
                  " NAME           VARCHAR(15)    NOT NULL, " + 
                  " PAYMANETMETHOD INT     NOT NULL, " + 
                  " BANKNUMBER        VACRHAR(10))"; 
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE CONTACT " +
                  "(TEL VARCHAR(10) PRIMARY KEY     NOT NULL," +
                  " NAME           VARCHAR(20)    NOT NULL, " + 
                  " EMAIL          VARCHAR(20)     NOT NULL, "
                  + "SUPPLIERCN VARCHAR(10) NOT NULL, " + 
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
                  "SUPPLYID    INTEGER  NOT NULL, "
                  +"PIDSUPPLIER INTEGER NOT NULL," +
                  "PRICE FLOAT NOT NULL, "
                  + "AMOUNT INTEGER NOT NULL, "
                  + "FOREIGN KEY (ORDERID) REFERENCES ORDERS(ORDERID),"
                  + "FOREIGN KEY (SUPPLYID) REFERENCES SUPPLY_AGREEMENT_PRODUCT(SUPPLYID), " + 
                  "FOREIGN KEY (PIDSUPPLIER) REFERENCES SUPPLY_AGREEMENT_PRODUCT(PIDSUPPLIER),"
                  + "PRIMARY KEY(ORDERID,SUPPLYID,PIDSUPPLIER))";
	      stmt.executeUpdate(sql);
	      sql = "CREATE TABLE DISCOUNT " +
                  "(PIDSUPPLIER INTEGER  NOT NULL," +
                  "SUPLLYID   INTEGER  NOT NULL, " + 
                  "AMOUNT    INT   NOT NULL, "
                  +"PRECENT FLOAT NOT NULL, "
                  + "FOREIGN KEY (PIDSUPPLIER) REFERENCES SUPPLY_AGREEMENT_PRODUCT(PIDSUPPLIER), " +  
                  "FOREIGN KEY (SUPLLYID) REFERENCES SUPPLY_AGREEMENT(SUPPLYID)"
                  + "PRIMARY KEY(PIDSUPPLIER,SUPLLYID,AMOUNT))";
	      stmt.executeUpdate(sql);
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Tables were created successfully");
	  }

	public Order getOrder(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}



	public void insertOrder(String samID, Date date, ArrayList<AgreementProduct> product_prices) {
		
		
		
	}



	public SupplyAgreement getSupplyAgreement(String samID) {
		// TODO Auto-generated method stub
		return null;
	}

	public AgreementProduct getProductFromAgreement(String samID, String ProductID) {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertSupplier(Supplier sp) {
		// TODO Auto-generated method stub
		
	}

	public void addProducer(String producerName) {
		// TODO Auto-generated method stub
		
	}

	public void insertProduct(Product pr, Supplier sp) {
		// TODO Auto-generated method stub
		
	}

	public Supplier getSupplier(String supId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertSupplyAgreement(SupplyAgreement sa) {
		// TODO Auto-generated method stub
		
	}


	

	
}
