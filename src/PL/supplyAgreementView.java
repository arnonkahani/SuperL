package PL;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import BE.Discount;
import BE.SupplyAgreement;
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
		
		
		System.out.println("Please enter supplierID:");
		String supplierid = scn.nextLine();
		
		System.out.println("Please enter number of SupplyType:");
		String supplytype = scn.nextLine();
		
		System.out.println("Please enter number of days of delevery or enter 0 if there is not day:");
		int j=scn.nextInt();
		
		if(j!=0){
			ArrayList<SupplyAgreement.Day> daysChoosen = new ArrayList<>();
			boolean error = false;
			for(int i=0;i<j;i++){
				if(error){
					System.out.println("Out of range try again");
					error = false;
				}
				System.out.println("Please choose day of delevery:");
				_vu.printList(SupplyAgreement.Day.values());
				int d = scn.nextInt();
				d=d-1;
				if(d<0 || d>6){
					error=true;
					i=i-1;
					_vu.clear();
					continue;
				}
				else
					daysChoosen.add(SupplyAgreement.Day.values()[d]);
			}
		}
		else{
			ArrayList<String> day = null;
		}
		
		System.out.println("Please enter number of products at the delevery:");
		int n=scn.nextInt();
		Hashtable<String, Float> product_table  = new Hashtable<String, Float>();
		for(int i=0;i<n;i++){
			System.out.println("Please enter product id :");
			String m=scn.nextLine();
			System.out.println("Please enter price to the product :");
			float l=scn.nextFloat();
			product_table.put(m,l);
		}
		
		System.out.println("Please enter number of products to discount:");
		int k=scn.nextInt();
		ArrayList<Discount> dicount = new ArrayList<>();
		for(int i=0;i<k;i++){
				dicount.add(createDiscount());
			}
		
		_sam.createSupplyAgreement(supplierid,supplytype,day,product_table,dicount);	
	}
	
	
	private Discount createDiscount(){
		System.out.println("Please enter product id:");
		String productid = scn.nextLine();
		System.out.println("Please enter amount:");
		int amount = scn.nextInt();
		System.out.println("Please enter precent for discount:");
		Float precent = scn.nextFloat();
		
		_sam.createDiscount(productid,amount,precent);
	}
	
	
}
