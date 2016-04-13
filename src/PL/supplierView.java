package PL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import BE.Contact;
import BE.Product;
import BL.SupplierManager;



public class supplierView {
	
	private SupplierManager _sp;
	private Scanner scn;
	
	public void createSupplier(){
		clear();
		System.out.println("Supplier Creation");
		System.out.println("Please enter supplier name:");
		String name = scn.nextLine();
		System.out.println("Please enter supplier payment method (0-10):");
		int paymentmethod = scn.nextInt();
		System.out.println("Please enter supplier bank number:");
		String banknumber = scn.nextLine();
		System.out.println("Please enter number of contacts:");
		int i = scn.nextInt();
		ArrayList<Contact> contacts = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			System.out.println("Please enter tel number of contact:");
			String _tel = scn.nextLine();
			System.out.println("Please email of contact:");
			String _email = scn.nextLine();
			System.out.println("Please name of contact:");
			String _name = scn.nextLine();
			contacts.add(_sp.createContact(_name, _email, _tel));
		}
		try{
			_sp.createSupplier(name,paymentmethod,banknumber,contacts);
		}
		catch(Exception e){
			
		}
	}
	
	public Product createProduct(){
		System.out.println("Please enter producer name:");
		String producername = scn.nextLine();

		System.out.println("Please enter product name:");
		String productname = scn.nextLine();
		
		System.out.println("Please enter daysOfValid:");
		int dayofvalid = scn.nextInt();
		
		System.out.println("Please enter weight:");
		int weight = scn.nextInt();
		
		System.out.println("Please enter supplierID:");
		String supplierid = scn.nextLine();
		
		return _sp.createProduct(producername,productname,dayofvalid,weight,supplierid);
		 
	}
	
	public Product supplierProduct(){
		System.out.println("Please enter producer name:");
		String supllierID = scn.nextLine();
		System.out.println("The products of this supplier are:");
		 return givesAllSupllierProduct(supllierID);	
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
}
