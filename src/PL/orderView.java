package PL;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

import BE.Product;
import BL.OrderManager;


public class orderView {
	private OrderManager _om;
	private Scanner scn;
	private viewUtils _vu;
	private supplyAgreementView _sav;
	
	public orderView(viewUtils vu, OrderManager logicManager, supplyAgreementView sav) {
		_sav=sav;
		_om=logicManager;
		_vu=vu;
		scn = new Scanner(System.in);
	}

	public void createOrder(){
		_vu.clear();
		try {
		
		System.out.println("Please enter number of products at the order:");
		int n= Integer.parseInt(_vu.tryGetNumber());
		HashMap<Product,Integer> product_table  = new HashMap<>();
		for(int i=0;i<n;i++){
			Product product = _sav.chooseOnDemandAgreementProduct();
			if(product == null)
			{
				System.out.println("No products");
				return;
				
			}
			System.out.println("Please enter amount of the product :");
			int l= Integer.parseInt(_vu.tryGetNumber());
			
			product_table.put(product, new Integer(l));
		}
		
		
		
		_om.create(new Object[]{product_table});
		} catch (SQLException e1) {
			System.out.println(_vu.exceptionHandler(e1));
		}
	}
	
	public void printOrder(){
		_vu.clear();
		
		System.out.println("Please enter order's number:");
		String str=scn.nextLine();
		
		try {
			System.out.println(_om.getOrderByID(str));
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
					_vu.showResult(_om.search(new int[]{choise-1},new String[]{query}));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		}
	
	
}
