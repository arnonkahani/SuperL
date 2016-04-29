package PL;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

import BE.Order;
import BE.OrderProduct;
import BE.SupplyAgreement;
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
		System.out.println("Please enter choose supply agreement:");
		SupplyAgreement supllyagreement= _sav.chooseSupplyAgreement();
		
		System.out.println("Please enter number of products at the order:");
		int n= Integer.parseInt(_vu.tryGetNumber());
		ArrayList<OrderProduct> product_table  = new ArrayList<>();
		for(int i=0;i<n;i++){
			OrderProduct pr = new OrderProduct();
			pr.setAgreementProduct(_sav.chooseAgreementProduct(supllyagreement.get_prices()));
			System.out.println("Please enter amount of the product :");
			int l= Integer.parseInt(_vu.tryGetNumber());
			pr.setAmount(l);
			product_table.add(pr);
		}
		
		System.out.println("Please enter the date of order:");
		System.out.println("Year:");
		String year=scn.nextLine();	
		System.out.println("Month:");
		String month=scn.nextLine();
		System.out.println("Day:");
		String day=scn.nextLine();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			
			date = format.parse(year +"-" + month+"-"+day + " "+"20:20:20");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		_om.create(new Object[]{supllyagreement,product_table,date});
		} catch (SQLException e1) {
			System.out.println(_vu.exceptionHandler(e1));
		}
	}
	
	public void printOrder(){
		_vu.clear();
		
		System.out.println("Please enter order's number:");
		String str=scn.nextLine();
		
		try {
			System.out.println(_om.search(new int[]{1}, new String[]{str},Order.class).get(0));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchMenu(){
		_vu.clear();
		String[] menu = _om.getFileds(Order.class);
		menu = _vu.createMenu(menu);
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Order Search Menu");
			choise = _vu.listChoose(menu);
			if(menu[choise-1].equals("Return"))
				return;
			else{
				if(!menu[choise-1].equals("All")){
					System.out.println("Enter query: ");
					query = scn.nextLine();
				}
				else
					query = "";
				try {
					_vu.showResult(_om.search(new int[]{choise-1},new String[]{query},Order.class));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		}
	
	
}
