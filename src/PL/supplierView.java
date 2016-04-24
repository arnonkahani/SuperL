package PL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import BE.Contact;
import BE.Producer;
import BE.Product;
import BE.Supplier;
import BE.SupplierProduct;
import BL.SupplierManager;

public class supplierView {
	
	private SupplierManager _sp;
	private Scanner scn;
	private viewUtils _vu;
	
	public supplierView(viewUtils vu, SupplierManager sm) {
		_sp=sm;
		_vu=vu;
		scn = new Scanner(System.in);
	}

	public void createSupplier(){
		
		_vu.clear();
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
	
	public void createProduct(){
		_vu.clear();
		System.out.println("Please enter producer name:");
		String producername = scn.nextLine();

		System.out.println("Please enter product name:");
		String productname = scn.nextLine();
		
		System.out.println("Please enter Shelf Life:");
		int dayofvalid = scn.nextInt();
		
		System.out.println("Please enter weight:");
		int weight = scn.nextInt();
		
		System.out.println("Please enter supplierID:");
		String supplierid = scn.nextLine();
		
		
		try{
			_sp.createProduct(producername,productname,dayofvalid,weight,supplierid);
		}
		catch(Exception e){
			
		}		 
	}
	
	public void supplierProducts(){
		_vu.clear();
		System.out.println("Please enter supplier company number:");
		String supllierID = scn.nextLine();
		System.out.println("The products of this supplier are:");
		
		ArrayList<SupplierProduct> supllierPro = new ArrayList<>();
		try {
			supllierPro = _sp.getAllSupllierProduct(supllierID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int j = 0; j < supllierPro.size(); j++) {
			System.out.println(supllierPro.get(j));
		}
	}
	

	public void productSearch(){
		_vu.clear();
		String [] menu = _sp.getSearchFields(Product.class);
		menu = _vu.createMenu(menu);
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Product Search Menu");
			choise = _vu.listChoose(menu);
			if(menu[choise].equals("Return"))
				return;
			else{
				query = scn.nextLine();
				try {
					_vu.showResult(_sp.searchProduct(new int[]{choise},new String[]{query}));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		}
	
	
	public void producerSearch(){
		_vu.clear();
		String [] menu = _sp.getSearchFields(Producer.class);
		menu = _vu.createMenu(menu);
		_vu.clear();
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Producer Search Menu");
			choise = _vu.listChoose(menu);
			if(menu[choise].equals("Return"))
				return;
			else{
				query = scn.nextLine();
				try {
					_vu.showResult(_sp.searchProducer(new int []{choise},new String []{query}));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
	}	
	
	
	public void supplierSearch(){
		_vu.clear();
		String [] menu = _sp.getSearchFields(Supplier.class);
		
		menu = _vu.createMenu(menu);
		_vu.clear();
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Supllier Search Menu");
			choise = _vu.listChoose(menu);
			if(menu[choise-1].equals("Return"))
				return;
			else{
				query = scn.nextLine();
				try {
					_vu.showResult(_sp.searchSupplier(new int[]{choise-1},new String[]{query}));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
	}
	
}
