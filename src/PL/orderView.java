package PL;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;


import BL.OrderManager;

public class orderView {
	private OrderManager _om;
	private Scanner scn;
	private viewUtils _vu;
	private supplyAgreementView _sav;
	
	public orderView(viewUtils vu, OrderManager om, supplyAgreementView sav) {
		_sav=sav;
		_om=om;
		_vu=vu;
		scn = new Scanner(System.in);
	}

	public void createOrder(){
		_vu.clear();
		try {
		System.out.println("Please enter suplly agreement ID:");
		String supllyagreement=scn.nextLine();
		String[] productsSN;
		String[] products;
		productsSN = _sav.getAllProductsSN(supllyagreement);
		products = _sav.getAllProductsNames(supllyagreement);
		
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
			String m=productsSN[_vu.listChoose(products)];
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
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void printOrder(){
		_vu.clear();
		
		System.out.println("Please enter order's number:");
		String str=scn.nextLine();
		
		try {
			System.out.println(_om.search(new int[]{1}, new String[]{str}).get(0));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchMenu(){
		_vu.clear();
		String[] menu = _om.getFileds();
		menu = _vu.createMenu(menu);
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Order Search Menu");
			choise = _vu.listChoose(menu);
			if(menu[choise].equals("Return"))
				return;
			else{
				System.out.println("Please enter " + menu[choise] + ":");
				query = scn.nextLine();
				try {
					_vu.showResult(_om.search(new int[]{choise},new String[]{query}));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		}
	
	
}
