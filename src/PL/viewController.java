package PL;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

import javax.xml.bind.ParseConversionEvent;

import BE.*;
import BL.*;

public class viewController {
	private Scanner scn;
	private SupplierManager _sp;
	private SupplyAgreementManager _sam ;
	private OrderManager _om;
	
	public viewController(){
		
		run();
	}
	public void run()
	{
		System.out.println("Menu");
		String menu[] = {"Create","Search","Print Order","Quit"};
		int choise = -1;
		while(true)
		{
		choise = listChoose(menu);
			switch(choise)
			{
			case 1:
				createMenu();
				break;
			case 2:
				searchMenu();
				break;
			case 3:
				printOrderMenu();
			case 4:
				return;
			}
		}
	}
	
	private void printOrderMenu() {
		// TODO Auto-generated method stub
		
	}
	private void searchMenu() {
		// TODO Auto-generated method stub
		
	}
	
	
	private void createMenu() {
		clear();
		System.out.println("Creation Menu");
		String menu[] = {"Supplier","Product","Supply Agreement","Order","Return"};
		int choise = -1;
		while(true)
		{
		choise = listChoose(menu);
			switch(choise)
			{
			case 1:
				createSupplier();
				break;
			case 2:
				createProduct();
				break;
			case 3:
				createSupplyAgreement();
			case 4:
				createOrder();
			case 5:
				return;
			}
		}
		
	}
	
	
	
	
	public void createSupplier(){
		clear();
		System.out.println("Supplier Creation");
		System.out.println("Please enter supplier name:");
		String name = scn.nextLine();
		System.out.println("Please enter supplier payment method (0-10):");
		int paymentmethod = scn.nextInt();
		System.out.println("Please enter supplier bank number:");
		String banknumber = scn.nextLine();
		System.out.println("Please enter number of contacts:");
		int i = scn.nextInt();
		ArrayList<Contact> contacts = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			System.out.println("Please enter tel number of contact:");
			String _tel = scn.nextLine();
			System.out.println("Please email of contact:");
			String _email = scn.nextLine();
			System.out.println("Please name of contact:");
			String _name = scn.nextLine();
			contacts.add(_sp.createContact(_name, _email, _tel));
		}
		try{
			_sp.createSupplier(name,paymentmethod,banknumber,contacts);
		}
		catch(Exception e){
			
		}
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
		
		return _sp.createProduct(producername,productname,dayofvalid,weight,supplierid);
		 
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
		
		_om.createOrder(supllyagreement,product_table,date);
	}
	
	

	}

