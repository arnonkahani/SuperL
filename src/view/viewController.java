package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

import logic.*;

public class viewController {
	private Scanner scn = new Scanner(System.in);
	private SupplierManager _sp = new SupplierManager();
	private SupplyAgreementManager _sam = new SupplyAgreementManager();
	
	
	public void createSupplier(){
		System.out.println("Please enter supplier name:");
		String name = scn.nextLine();
		System.out.println("Please enter supplier payment method (0-10):");
		int paymentmethod = scn.nextInt();
		System.out.println("Please enter supplier name:");
		String banknumber = scn.nextLine();
		System.out.println("Please enter number of contacts:");
		int i = scn.nextInt();
		ArrayList<Contact> contacts = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			System.out.println("Please enter number of contacts:");
			String _tel = scn.nextLine();
			System.out.println("Please enter number of contacts:");
			String _email = scn.nextLine();
			System.out.println("Please enter number of contacts:");
			String _name = scn.nextLine();
			contacts.add(new Contact(_tel, _email, _name));
		}
		_sp.createSupplier(name,paymentmethod,banknumber,contacts);
	}
	
	public Product createProduct(){
		System.out.println("Please enter producer name:");
		String producername = scn.nextLine();

		System.out.println("Please enter product name:");
		String productname = scn.nextLine();
		
		System.out.println("Please enter daysOfValid:");
		int dayofvalid = scn.nextInt();
		
		System.out.println("Please enter weight:");
		int weight = scn.nextInt();
		
		System.out.println("Please enter supplierID:");
		String supplierid = scn.nextLine();
		
		_sp.createProduct(producername,productname,dayofvalid,weight,supplierid);
		return _sp;
	}
	
	public void createProducer(){
		System.out.println("Please enter producerName:");
		String producername = scn.nextLine();
		
		System.out.println("Please enter number of products:");
		int i = scn.nextInt();
		ArrayList<Product> productlist = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			productlist.add(createProduct());
		}
	
		System.out.println("Please enter supplierID:");
		String supplierid = scn.nextLine();
		
		_sp.createProducer(producername,productlist,supplierid);
	}
	
	public void createSupplyAgreement(){
		System.out.println("Please enter supplierID:");
		String supplierid = scn.nextLine();
		
		System.out.println("Please enter number of SupplyType:");
		String supplytype = scn.nextLine();
		
		System.out.println("Please enter number of days of delevery or enter 0 if there is not day:");
		int j=scn.nextInt();
		
		if(j!=0){
			ArrayList<Day> day = new ArrayList<>();
			for(int i=0;i<j;i++){
				System.out.println("Please enter day of delevery:");
				String d = scn.nextLine();
				day.add(new Day(d));
			}
		}
		else{
			ArrayList<Day> day = null;
		}
		
		System.out.println("Please enter number of products at the delevery:");
		int n=scn.nextInt();
		Hashtable<String, Float> product_table  = new Hashtable<String, Float>();
		for(int i=0;i<n;i++){
			System.out.println("Please enter product id :");
			String m=scn.nextLine();
			System.out.println("Please enter price to the product :");
			float l=scn.nextFloat();
			product_table.put(m,l);
		}
		
		System.out.println("Please enter number of products to discount:");
		int k=scn.nextInt();
		ArrayList<Day> dicount = new ArrayList<>();
		for(int i=0;i<k;i++){
				dicount.add(createDiscount());
			}
		
		_sam.createSupplyAgreement(supplierid,supplytype,day,product_table,dicount);	
	}

	public void createOrder(){
		System.out.println("Please enter suplly areement:");
		String supllyagreement=scn.nextLine();
		
		System.out.println("Please enter number of products at the order:");
		int n=scn.nextInt();
		Hashtable<String, Integer> product_table  = new Hashtable<String, Integer>();
		for(int i=0;i<n;i++){
			System.out.println("Please enter product id :");
			String m=scn.nextLine();
			System.out.println("Please enter amount of the product :");
			int l=scn.nextInt();
			product_table.put(m,l);
		}
		
		System.out.println("Please enter the date of order:");
		String dateS=scn.nextLine();		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
		LocalDate date = LocalDate.parse(dateS, formatter);
		
		_sam.createOrder(supllyagreement,product_table,date);
	}
	
	
	public void createDiscount(){
		System.out.println("Please enter product id:");
		String productid = scn.nextLine();
		System.out.println("Please enter amount:");
		int amount = scn.nextInt();
		System.out.println("Please enter precent for discount:");
		Float precent = scn.nextFloat();
		
		_sp.createDiscount(productid,amount,precent);
	}
	
}

