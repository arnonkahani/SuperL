package PL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

import BE.AgreementProduct;
import BE.Discount;
import BE.SupplyAgreement;
import BE.SupplyAgreement.*;
import BL.SupplyAgreementManager;

public class supplyAgreementView {

	private SupplyAgreementManager _sam;
	private viewUtils _vu;
	private Scanner scn;
	
	public supplyAgreementView(viewUtils vu,SupplyAgreementManager sam)
	{
		_sam = sam;
		_vu = vu;
		scn = new Scanner(System.in);
	}
	public void createSupplyAgreement(){
		
		_vu.clear();
		
		System.out.println("Create Supplier Agreement");
		
		System.out.println("Please enter supplierID:");
		String supplierID = scn.nextLine();
		
		System.out.println("Please enter number of SupplyType:");
		SupplyType supplyType = SupplyType.values()[_vu.listChoose(SupplyType.values())];
		
		System.out.println("Please enter number of days of delevery or enter 0 if there is not day:");
		int j=scn.nextInt();
		ArrayList<Day> supplyDays = new ArrayList<>();
		if(j!=0){
			boolean error = false;
			for(int i=0;i<j;i++){
				if(error){
					System.out.println("Out of range try again");
					error = false;
				}
				System.out.println("Please choose day of delevery:");
				_vu.printList(Day.values());
				int d = scn.nextInt();
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
		ArrayList<Discount> dicount = new ArrayList<>();
		System.out.println("Please enter number of products at the agreement:");
		int n=scn.nextInt();
		HashMap<String, Float> product_table  = new HashMap<String, Float>();
		for(int i=0;i<n;i++){
			System.out.println("Please enter product id :");
			String m=scn.nextLine();
			System.out.println("Please enter price to the product :");
			float l=scn.nextFloat();
			product_table.put(m,l);
			System.out.println("Please enter number of discounts:");
			int k=scn.nextInt();
			for(int p=0;p<k;p++){
				System.out.println("Please enter amount:");
				int amount = scn.nextInt();
				System.out.println("Please enter precent for discount:");
				Float precent = scn.nextFloat();
				dicount.add(_sam.createDiscount(m,amount,precent));
				}
			
		}
		System.out.println("Please enter type of delevry:");
		DelevryType delevryType = DelevryType.values()[_vu.listChoose(DelevryType.values())];
		
		
		
		try {
			_sam.createSupplyAgreement(supplierID,supplyType,supplyDays,delevryType,dicount,product_table);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	

	
	
	public void searchSupplyAgreement(){
		_vu.clear();
		String[] menu = _sam.getSearchFields(SupplyAgreement.class);
		menu = _vu.createMenu(menu);
		_vu.clear();
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Supply Agreement Search Menu");
			choise = _vu.listChoose(menu);
			if(menu[choise].equals("Return"))
				return;
			else{
				query = scn.nextLine();
				try {
					_vu.showResult(_sam.searchSupplyAgreement(new int[]{choise},new String[]{query}));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		}
	
	public void searchSupplyAgreementProduct(){
		_vu.clear();
		String[] menu = _sam.getSearchFields(AgreementProduct.class);
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
				query = scn.nextLine();
				try {
					_vu.showResult(_sam.searchAgreementProduct(new int[]{choise-1},new String[]{query}));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		}
	
	
	public String[] getAllProductsNames(String supllyagreement) throws SQLException {
		return _sam.getAllProductsNames(supllyagreement);
	}
	public String[] getAllProductsSN(String supllyagreement) throws SQLException {
		return _sam.getAllProductsSN(supllyagreement);
	}
	}

	
	

