package com.SupplierStorage.PL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.SupplierStorage.BE.SupplyAgreementProduct;
import com.SupplierStorage.BE.Discount;
import com.SupplierStorage.BE.Product;
import com.SupplierStorage.BE.Supplier;
import com.SupplierStorage.BE.SupplierProduct;
import com.SupplierStorage.BE.SupplyAgreement;
import com.SupplierStorage.BE.SupplyAgreement.*;
import com.SupplierStorage.BL.SupplyAgreementManager;

public class SupplyAgreementView {

	private SupplyAgreementManager _sam;
	private SupplierView _supplier_view;
	private ViewUtils _view_utils;
	private Scanner scn;
	
	public SupplyAgreementView(ViewUtils vu,SupplyAgreementManager logicManager,SupplierView sv)
	{
		_supplier_view = sv;
		_sam = logicManager;
		_view_utils = vu;
		scn = new Scanner(System.in);
	}
	public void createSupplyAgreement(){
		
		_view_utils.clear();
		
		System.out.println("Create Supplier Agreement");
		
		System.out.println("Please enter supplier CN:");
		String supplierID = _supplier_view.chooseCN();
		if(supplierID==null)
		{
			System.out.println("No suppliers");
			return;
		}
		
		System.out.println("Please enter number of SupplyType:");
		int supplyType = _view_utils.listChoose(SupplyType.values());
		ArrayList<Day> supplyDays = new ArrayList<>();
		if(supplyType == 0){
		System.out.println("Please enter number of days of delevery :");
		int j= Integer.parseInt(_view_utils.tryGetNumber(1,7));
		
		if(j!=0){
			boolean error = false;
			for(int i=0;i<j;i++){
				if(error){
					System.out.println("Out of range try again");
					error = false;
				}
				System.out.println("Please choose day of delevery:");
				_view_utils.printList(Day.values());
				int d = Integer.parseInt(_view_utils.tryGetNumber());
				d=d-1;
				if(d<0 || d>6){
					error=true;
					i=i-1;
					_view_utils.clear();
					continue;
				}
				else
					supplyDays.add(Day.values()[d]);
			}
		}
		}
		System.out.println("Please enter number of products at the agreement:");
		int n=Integer.parseInt(_view_utils.tryGetNumber());
		ArrayList<SupplyAgreementProduct> product_table  = new ArrayList<>();
		for(int i=0;i<n;i++){
			ArrayList<Discount> dicount = new ArrayList<>();
			SupplierProduct pr = _supplier_view.chooseSupplierProduct(supplierID);
			System.out.println("Please enter price to the product :");
			float l= Float.parseFloat(_view_utils.tryGetFloat());
			SupplyAgreementProduct agp = new SupplyAgreementProduct(pr, l);
			System.out.println("Please enter number of discounts:");
			int k=scn.nextInt();
			for(int p=0;p<k;p++){
				System.out.println("Please enter amount:");
				int amount = Integer.parseInt(_view_utils.tryGetNumber());
				System.out.println("Please enter precent for discount:");
				float precent = Float.parseFloat(_view_utils.tryGetFloat());
				System.out.println("SupplyGrreView: " + precent);
				dicount.add(new Discount(amount, precent));
				}
			agp.set_discounts(dicount);
			product_table.add(agp);
			
		}
		System.out.println("Please enter type of delevry:");
		int delevryType = _view_utils.listChoose(DelevryType.values());
		
		
		
		try {
			Supplier sp = new Supplier();
			sp.set_CN(supplierID);
			SupplyAgreement sa = new SupplyAgreement(sp,SupplyType.values()[supplyType], 
					supplyDays, DelevryType.values()[delevryType],product_table);
			_sam.create(sa);
		} catch (SQLException e) {
			_view_utils.exceptionHandler(e);
		}	
	}
	

	
	
	public void searchSupplyAgreement(){
		_view_utils.clear();
		String[] menu = _sam.getFileds();
		menu = _view_utils.createMenu(menu);
		_view_utils.clear();
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Supply Agreement Search Menu");
			choise = _view_utils.listChoose(menu);
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
					_view_utils.showResult(_sam.search(new int[]{choise-1},new String[]{query}));
				} catch (SQLException e) {
					_view_utils.exceptionHandler(e);
				}
				}
			}
		}
	
	public void searchSupplyAgreementProduct(){
		_view_utils.clear();
		String[] menu = _sam.getFileds();
		menu = _view_utils.createMenu(menu);
		_view_utils.clear();
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Supply Agreement Product Search Menu");
			choise = _view_utils.listChoose(menu);
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
					_view_utils.showResult(_sam.search(new int[]{choise-1},new String[]{query}));
				} catch (SQLException e) {
					_view_utils.exceptionHandler(e);
				}
				}
			}
		}
	
	
	
	public SupplyAgreement chooseSupplyAgreement() {
		try {
			ArrayList<SupplyAgreement> sup = _sam.search(new int[]{0}, new String[]{""});
			for (int i = 0; i < sup.size(); i++) {
				System.out.println(i + ". " + sup.get(0));
			}
			System.out.println("Choose Supply Agreement: ");
			int choise = Integer.parseInt(_view_utils.tryGetNumber(0, sup.size() - 1));
			return sup.get(choise);
		} catch (SQLException e) {
			_view_utils.exceptionHandler(e);
			return null;
		}
	}
	
	 
	public SupplyAgreementProduct chooseAgreementProduct(ArrayList<SupplyAgreementProduct> get_prices) {
		
			System.out.println("Choose Agreement Product: ");
			for (int i = 0; i < get_prices.size(); i++) {
				System.out.println(i + ". " + get_prices.get(i));
			}
			
			int choise = Integer.parseInt(_view_utils.tryGetNumber(0, get_prices.size() - 1));
			return get_prices.get(choise);
		
	}

	public Product chooseOnDemandAgreementProduct() throws SQLException {
			System.out.println("Choose Agreement Product: ");
			ArrayList<Product> products =_sam.getAllOnDemandProducts();
			if(products.size() == 0)
				return null;
			for (int i = 0; i < products.size(); i++) {
				
				System.out.println(i + ". " + products.get(i));
			}
			int choise = Integer.parseInt(_view_utils.tryGetNumber(0, products.size() - 1));
			return products.get(choise);
		
		
	}
	public Product chooseWeeklyAgreementProduct(int d) throws SQLException {
		
			System.out.println("Choose Agreement Product: ");
			ArrayList<SupplyAgreementProduct> products =_sam.getAllDayProducts(d);
			if(products.size() == 0)
				return null;
			for (int i = 0; i < products.size(); i++) {
				Product p = products.get(i);
				
				System.out.println(i + ". " + p);
				
				
			}
			int choise = Integer.parseInt(_view_utils.tryGetNumber(0, products.size() - 1));
			return products.get(choise);
	
		
	}

	}

	
	

