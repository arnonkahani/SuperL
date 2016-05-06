import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.*;

import BE.SupplyAgreementProduct;
import BE.Contact;
import BE.Discount;
import BE.Product;
import BE.Supplier;
import BE.SupplierProduct;
import BE.SupplyAgreement;
import BE.SupplyAgreement.Day;
import BE.SupplyAgreement.DelevryType;
import BE.SupplyAgreement.SupplyType;
import BL.BLFactory;
import BL.LogicManager;
import BL.SupplierManager;

public class Testing {
//
//	private static BLFactory _blfactory;	
//	
//	@BeforeClass
//    public static void oneTimeSetUp() {
//		//_blfactory = new BLFactory(false);       
//    }
//
//    @AfterClass
//    public static void oneTimeTearDown() {
//    	try {
//    	 Class.forName("org.sqlite.JDBC");
//	      Connection _c = DriverManager.getConnection("jdbc:sqlite:supllier.db");
//	      Statement stmt;
//		
//			stmt = _c.createStatement();
//		
//		    String sql;
//		    sql = "PRAGMA foreign_keys = ON";
//		    stmt.executeUpdate(sql);
//		    sql = "DELETE FROM CONTACT WHERE SUPPLIERCN = '12345678' ";
//		    stmt.executeUpdate(sql);
//		    sql = "DELETE FROM SUPPLIER_PRODUCT WHERE SUPPLIERCN = '12345678' ";
//		   stmt.executeUpdate(sql);
//		   sql = "DELETE FROM SUPPLIER_PRODUCT WHERE SUPPLIERCN = '123456789' ";
//		   stmt.executeUpdate(sql);
//		   sql = "DELETE FROM SUPPLY_AGREEMENT WHERE SUPPLIERCN = '12345678' ";
//		    stmt.executeUpdate(sql);
//		    sql = "DELETE FROM SUPPLIER WHERE CN = '12345678' ";
//		    stmt.executeUpdate(sql);
//		    sql = "DELETE FROM SUPPLIER WHERE CN = '123456789' ";
//		    stmt.executeUpdate(sql);
//		    sql = "DELETE FROM PRODUCT WHERE PRODUCERNAME = 'PUMA'";
//		    stmt.executeUpdate(sql);
//		    sql = "DELETE FROM PRODUCER WHERE NAME = 'PUMA' ";
//		    stmt.executeUpdate(sql);
//    	} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//
//	@Test
//	public void test() {
//		LogicManager _sm=_blfactory.getManager(Supplier.class);
//		ArrayList<Contact> contacts = new ArrayList<>();
//		try{
//			_sm.create(new Object[] {"123456789","ARNON",1,"1234",contacts});
//			Supplier sp=(Supplier)_sm.search(new int [] {1},new String[]{"123456789"}, Supplier.class).get(0);
//			
//			assertTrue(sp.get_CN().equals("123456789"));
//			assertTrue(sp.get_name().equals("ARNON"));
//			
//		}
//		catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//
//	}
//	@Test
//	public void test1() {
//		LogicManager _sm=_blfactory.getManager(Supplier.class);
//		ArrayList<Contact> contacts = new ArrayList<>();
//		contacts.add(new Contact("051461341","aa@walla.com","meir"));
//		try{
//			_sm.create(new Object[] {"12345678","OMER",1,"31431",contacts});
//			Supplier sp=(Supplier)_sm.search(new int [] {1},new String[]{"12345678"}, Supplier.class).get(0);
//			Contact con=(Contact)_sm.search(new int[]{4}, new String[]{"12345678"}, Contact.class).get(0);
//			assertTrue(sp.get_CN().equals("12345678"));
//			assertTrue(sp.get_name().equals("OMER"));
//			assertTrue(con.equals(contacts.get(0)));
//			
//		}
//		catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//
//	}
//	@Test
//	public void test2() {
//		LogicManager _sm=_blfactory.getManager(Product.class);
//		
//		try{
//			_sm.create(new Object[]{"PUMA","milk",100,(float)5.0,"12345678"});
//			SupplierProduct sp=(SupplierProduct)_sm.search(new int [] {2,4},new String[]{"'MILK'","12345678"}, SupplierProduct.class).get(0);
//			
//			assertEquals(sp.get_name(),"MILK");
//			assertEquals(sp.get_producer().getName(),"PUMA");
//			
//		}
//		catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//
//	}
//	@Test
//	public void test3() {
//		LogicManager _sm=_blfactory.getManager(Product.class);
//		
//		try{
//			_sm.create(new Object[]{"puma","tv",20,(float)5.051,"12345678"});
//			SupplierProduct sp=(SupplierProduct)_sm.search(new int [] {2,4},new String[]{"'TV'","12345678"}, SupplierProduct.class).get(0);
//			
//			assertEquals(sp.get_name(),"TV");
//			assertEquals(sp.get_producer().getName(),"PUMA");
//			
//		}
//		catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//
//	}
//	@Test
//	public void test4() {
//		LogicManager _sm=_blfactory.getManager(Product.class);
//		
//		try{
//			_sm.create(new Object[]{"puma","shoe",20,(float)5,"12345678"});
//			SupplierProduct sp=(SupplierProduct)_sm.search(new int [] {2,4},new String[]{"'SHOE'","12345678"}, SupplierProduct.class).get(0);
//			
//			assertEquals(sp.get_name(),"SHOE");
//			assertEquals(sp.get_producer().getName(),"PUMA");
//			
//		}
//		catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//
//	}
//	@Test
//	public void test5() {
//		LogicManager _sm=_blfactory.getManager(SupplyAgreement.class);
//		
//		try{
//			_sm.create(new Object[]{"12345678",0, 
//					new ArrayList<Day>(), 0,new ArrayList<SupplyAgreementProduct>()});
//					SupplyAgreement sp=(SupplyAgreement)_sm.search(new int [] {2},new String[]{"12345678"}, SupplyAgreement.class).get(0);
//			
//			assertTrue(sp.get_sup().get_CN().equals("12345678"));
//			
//		}
//		catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//
//	}
//	@Test
//	public void test6() {
//		LogicManager _sm=_blfactory.getManager(Product.class);
//		
//		try{
//			_sm.create(new Object[]{"puma","table",20,(float)5,"12345678"});
//			SupplierProduct sp=(SupplierProduct)_sm.search(new int [] {2,4},new String[]{"'TABLE'","12345678"}, SupplierProduct.class).get(0);
//			
//			assertEquals(sp.get_name(),"TABLE");
//			assertEquals(sp.get_producer().getName(),"PUMA");
//			
//		}
//		catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//
//	}
//	@Test
//	public void test7() {
//		LogicManager _sm=_blfactory.getManager(Product.class);
//		
//		try{
//			_sm.create(new Object[]{"puma","pc",20,(float)5,"12345678"});
//			SupplierProduct sp=(SupplierProduct)_sm.search(new int [] {2,4},new String[]{"'PC'","12345678"}, SupplierProduct.class).get(0);
//			
//			assertEquals(sp.get_name(),"PC");
//			assertEquals(sp.get_producer().getName(),"PUMA");
//			
//		}
//		catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//
//	}
//	@Test
//	public void test8() {
//		LogicManager _sm=_blfactory.getManager(Product.class);
//		
//		try{
//			_sm.create(new Object[]{"puma","laptop",20,(float)5,"12345678"});
//			SupplierProduct sp=(SupplierProduct)_sm.search(new int [] {2,4},new String[]{"'LAPTOP'","12345678"}, SupplierProduct.class).get(0);
//			
//			assertEquals(sp.get_name(),"LAPTOP");
//			assertEquals(sp.get_producer().getName(),"PUMA");
//			
//		}
//		catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//
//	}
//	@Test
//	public void test9() {
//		LogicManager _sm=_blfactory.getManager(Product.class);
//		
//		try{
//			_sm.create(new Object[]{"puma","car",20,(float)5,"12345678"});
//			SupplierProduct sp=(SupplierProduct)_sm.search(new int [] {2,4},new String[]{"'CAR'","12345678"}, SupplierProduct.class).get(0);
//			
//			assertEquals(sp.get_name(),"CAR");
//			assertEquals(sp.get_producer().getName(),"PUMA");
//			
//		}
//		catch (SQLException e){
//			e.printStackTrace();
//		}
//		
//
//	}

}