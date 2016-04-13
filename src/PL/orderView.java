package PL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
		Hashtable<String, Integer> product_table  = new Hashtable<String, Integer>();
		for(int i=0;i<n;i++){
			System.out.println("Please enter product number :");
			String m=products[_vu.listChoose(products)];
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
	
	public void searchMenu(){
		_vu.clear();
		ArrayList<String> menu = _vu.getNamesOfEnum(Order.Search.values());
		menu.add("Return");
		_vu.clear();
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Order Search Menu");
			choise = _vu.listChoose(menu);
			if(choise == menu.size()-1)
				return;
			else{
				query = scn.nextLine();
				_vu.showResult(_om.search(choise,query));
				}
			}
		}
	
	
}
