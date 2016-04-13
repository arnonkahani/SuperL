package PL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

import BE.Contact;
import BL.OrderManager;

public class orderView {
	private OrderManager _om;
	private Scanner scn;
	private viewUtils _vu;
	
	public void createOrder(){
		_vu.clear();
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
	
	public void printOreder(){
		_vu.clear();
		
		System.out.println("Please enter order's number:");
		String orderNumber=scn.nextLine();
		
		ArrayList<Product> order = new ArrayList<>();
		order=printOredersProduct(orderNumber);
		for (int j = 0; j < order.size(); j++) {
			System.out.println(order.get(j));
		}
	}
	
	
}
