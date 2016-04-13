package PL;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Scanner;

import javax.xml.bind.ParseConversionEvent;

import BE.*;
import BL.*;

public class viewController {
	private Scanner scn;
	private SupplierManager _sp;
	private SupplyAgreementManager _sam ;
	private OrderManager _om;
	
	public viewController(){
		
		run();
	}
	public void run()
	{
		System.out.println("Menu");
		String menu[] = {"Create","Search","Print Order","Quit"};
		int choise = -1;
		while(true)
		{
		choise = listChoose(menu);
			switch(choise)
			{
			case 1:
				createMenu();
				break;
			case 2:
				searchMenu();
				break;
			case 3:
				printOrderMenu();
			case 4:
				return;
			}
		}
	}
	
	private void printOrderMenu() {
		// TODO Auto-generated method stub
		
	}
	private void searchMenu() {
		// TODO Auto-generated method stub
		
	}
	private void createMenu() {
		clear();
		System.out.println("Creation Menu");
		String menu[] = {"Supplier","Product","Supply Agreement","Return"};
		int choise = -1;
		while(true)
		{
		choise = listChoose(menu);
			switch(choise)
			{
			case 1:
				createSupplier();
				break;
			case 2:
				searchMenu();
				break;
			case 3:
				printOrderMenu();
			case 4:
				return;
			}
		}
		
	}
	
	
	private int listChoose(String[] menu) {
		int choise = -1;
		boolean error = false;
		while(choise<0 || choise > menu.length-1){
			if(error){
				System.out.println("Out of range try again");
				error = false;
			}
			printList(menu);
			choise = scn.nextInt();
			if(choise<0 || choise > menu.length-1)
			{
				error = true;
				clear();
			}
				
		}
		return choise;
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
				printList(SupplyAgreement.Day.values());
				int d = scn.nextInt();
				d=d-1;
				if(d<0 || d>6){
					error=true;
					i=i-1;
					clear();
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

	
	
	public Discount createDiscount(){
		System.out.println("Please enter product id:");
		String productid = scn.nextLine();
		System.out.println("Please enter amount:");
		int amount = scn.nextInt();
		System.out.println("Please enter precent for discount:");
		Float precent = scn.nextFloat();
		
		_sp.createDiscount(productid,amount,precent);
	}
	
	private void clear()
	{
		final String operatingSystem = System.getProperty("os.name");

		if (operatingSystem .contains("Windows")) {
		    try {
				Runtime.getRuntime().exec("cls");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
		    try {
				Runtime.getRuntime().exec("clear");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void printList(Enum [] list)
	{
		for (int i = 0; i < list.length; i++) {
			System.out.println((i+1) +". " + list[i].name());
		}
	}
	private void printList(String [] list)
	{
		for (int i = 0; i < list.length; i++) {
			System.out.println((i+1) +". " + list[i]);
		}
	}
	
}

