package PL;

import java.sql.SQLException;
import java.util.Scanner;

import BE.Producer;
import BE.Product;
import BL.LogicManager;

public class ProductView {

	private LogicManager _pm;
	private Scanner scn;
	private viewUtils _vu;
	
	public ProductView(LogicManager logicManager,viewUtils vu){
		_pm = logicManager;
		scn = new Scanner(System.in);
		_vu = vu;
	}
	
	public void createProduct(){
		_vu.clear();
		System.out.println("Please enter producer name:");
		String producername = _vu.tryGetOnlyLetters();

		System.out.println("Please enter product name:");
		String productname = scn.nextLine();
		
		System.out.println("Please enter Shelf Life:");
		int dayofvalid = Integer.parseInt(_vu.tryGetNumber());
		
		System.out.println("Please enter weight:");
		float weight =Float.parseFloat(_vu.tryGetFloat());
		
		System.out.println("Please enter supplierCN:");
		String supplierid = _vu.tryGetNumber();
		
		
		try{
			_pm.create(new Object[]{producername,productname,dayofvalid,weight,supplierid});
		}
		catch(SQLException e){
			System.out.println(_vu.exceptionHandler(e));
		}		 
	}
	
	public void productSearch(){
		_vu.clear();
		String [] menu = _pm.getFileds(Product.class);
		menu = _vu.createMenu(menu);
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Product Search Menu");
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
					_vu.showResult(_pm.search(new int[]{choise-1},new String[]{query},Product.class));
				} catch (SQLException e) {
					_vu.exceptionHandler(e);
				}
				}
			}
		}
	
	
	public void producerSearch(){
		_vu.clear();
		String [] menu = _pm.getFileds(Producer.class);
		menu = _vu.createMenu(menu);
		_vu.clear();
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Producer Search Menu");
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
					_vu.showResult(_pm.search(new int []{choise},new String []{query},Producer.class));
				} catch (SQLException e) {
					System.out.println(_vu.exceptionHandler(e));
				}
				}
			}
	}	
}
