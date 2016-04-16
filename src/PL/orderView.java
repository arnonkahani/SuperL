package PL;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

import BE.Contact;
import BE.Order;
import BL.OrderManager;

public class orderView {
	private OrderManager _om;
	private Scanner scn;
	private viewUtils _vu;
	private supplyAgreementView _sav;
	
	public void createOrder(){
		_vu.clear();
		System.out.println("Please enter suplly areement:");
		String supllyagreement=scn.nextLine();
		String[] products = _sav.showAllProducts(supllyagreement);
		if(products == null)
		{
			System.out.println("No products to order");
			System.out.println("Press enter to return");
			scn.nextLine();
			return;
		}
		System.out.println("Please enter number of products at the order:");
		int n=scn.nextInt();
		HashMap<String, Integer> product_table  = new HashMap<String, Integer>();
		for(int i=0;i<n;i++){
			System.out.println("Please enter product number :");
			String m=products[_vu.listChoose(products)];
			System.out.println("Please enter amount of the product :");
			int l=scn.nextInt();
			product_table.put(m,l);
		}
		
		System.out.println("Please enter the date of order:");
		String dateS=scn.nextLine();		
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(dateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_om.createOrder(supllyagreement,product_table,date);
	}
	
	public void printOrder(){
		_vu.clear();
		
		System.out.println("Please enter order's number:");
		String str=scn.nextLine();
		
		System.out.println(_om.search(1, str).get(0));
	}
	
	public void searchMenu(){
		_vu.clear();
		String[] menu = _om.getFileds();
		_vu.clear();
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Order Search Menu");
			choise = _vu.listChoose(menu);
			if(choise == menu.length)
				return;
			else{
				query = scn.nextLine();
				_vu.showResult(_om.search(choise,query));
				}
			}
		}
	
	
}
