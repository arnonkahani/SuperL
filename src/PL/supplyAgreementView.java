package PL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import BE.SupplyAgreementProduct;
import BE.Discount;
import BE.Product;
import BE.SupplierProduct;
import BE.SupplyAgreement;
import BE.SupplyAgreement.*;
import BL.LogicManager;
import BL.SupplyAgreementManager;

public class supplyAgreementView {

	private SupplyAgreementManager _sam;
	private supplierView _sv;
	private viewUtils _vu;
	private Scanner scn;
	
	public supplyAgreementView(viewUtils vu,SupplyAgreementManager logicManager,supplierView sv)
	{
		_sv = sv;
		_sam = logicManager;
		_vu = vu;
		scn = new Scanner(System.in);
	}
	public void createSupplyAgreement(){
		
		_vu.clear();
		
		System.out.println("Create Supplier Agreement");
		
		System.out.println("Please enter supplier CN:");
		String supplierID = _sv.chooseCN();
		if(supplierID==null)
		{
			System.out.println("No suppliers");
			return;
		}
		
		System.out.println("Please enter number of SupplyType:");
		int supplyType = _vu.listChoose(SupplyType.values());
		ArrayList<Day> supplyDays = new ArrayList<>();
		if(supplyType == 0){
		System.out.println("Please enter number of days of delevery or enter 0 if there is not day:");
		int j= Integer.parseInt(_vu.tryGetNumber());
		
		if(j!=0){
			boolean error = false;
			for(int i=0;i<j;i++){
				if(error){
					System.out.println("Out of range try again");
					error = false;
				}
				System.out.println("Please choose day of delevery:");
				_vu.printList(Day.values());
				int d = Integer.parseInt(_vu.tryGetNumber());
				d=d-1;
				if(d<0 || d>6){
					error=true;
					i=i-1;
					_vu.clear();
					continue;
				}
				else
					supplyDays.add(Day.values()[d]);
			}
		}
		}
		System.out.println("Please enter number of products at the agreement:");
		int n=Integer.parseInt(_vu.tryGetNumber());
		ArrayList<SupplyAgreementProduct> product_table  = new ArrayList<>();
		for(int i=0;i<n;i++){
			ArrayList<Discount> dicount = new ArrayList<>();
			SupplierProduct pr = _sv.chooseSupplierProduct(supplierID);
			System.out.println("Please enter price to the product :");
			float l=scn.nextFloat();
			SupplyAgreementProduct agp = new SupplyAgreementProduct(pr, l);
			System.out.println("Please enter number of discounts:");
			int k=scn.nextInt();
			for(int p=0;p<k;p++){
				System.out.println("Please enter amount:");
				int amount = scn.nextInt();
				System.out.println("Please enter precent for discount:");
				Float precent = scn.nextFloat();
				dicount.add(new Discount(amount, precent));
				}
			agp.set_discounts(dicount);
			product_table.add(agp);
			
		}
		System.out.println("Please enter type of delevry:");
		int delevryType = _vu.listChoose(DelevryType.values());
		
		
		
		try {
			_sam.create(new Object[]{supplierID,supplyType,supplyDays,delevryType,product_table});
		} catch (SQLException e) {
			_vu.exceptionHandler(e);
		}	
	}
	

	
	
	public void searchSupplyAgreement(){
		_vu.clear();
		String[] menu = _sam.getFileds();
		menu = _vu.createMenu(menu);
		_vu.clear();
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Supply Agreement Search Menu");
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
					_vu.showResult(_sam.search(new int[]{choise-1},new String[]{query}));
				} catch (SQLException e) {
					_vu.exceptionHandler(e);
				}
				}
			}
		}
	
	public void searchSupplyAgreementProduct(){
		_vu.clear();
		String[] menu = _sam.getFileds();
		menu = _vu.createMenu(menu);
		_vu.clear();
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Supply Agreement Product Search Menu");
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
					_vu.showResult(_sam.search(new int[]{choise-1},new String[]{query}));
				} catch (SQLException e) {
					_vu.exceptionHandler(e);
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
			int choise = Integer.parseInt(_vu.tryGetNumber(0, sup.size() - 1));
			return sup.get(choise);
		} catch (SQLException e) {
			_vu.exceptionHandler(e);
			return null;
		}
	}
	
	 
	public SupplyAgreementProduct chooseAgreementProduct(ArrayList<SupplyAgreementProduct> get_prices) {
		try {
			
			for (int i = 0; i < get_prices.size(); i++) {
				System.out.println(i + ". " + get_prices.get(i));
			}
			System.out.println("Choose Agreement Product: ");
			int choise = Integer.parseInt(_vu.tryGetNumber(0, get_prices.size() - 1));
			return get_prices.get(choise);
		} catch (Exception e) {
			return null;
		}
	}

	public Product chooseOnDemandAgreementProduct() {
		try {
			ArrayList<Product> products =_sam.getAllOnDemandProducts();
			for (int i = 0; i < products.size(); i++) {
				System.out.println(i + ". " + products.get(i));
				System.out.println("Choose Agreement Product: ");
				int choise = Integer.parseInt(_vu.tryGetNumber(0, products.size() - 1));
				return products.get(choise);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	}

	
	

