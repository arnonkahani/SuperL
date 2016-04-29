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
		System.out.println("Please enter company number:");
		String cn = _vu.tryGetNumber();
		System.out.println("Please enter supplier name:");
		String name = _vu.tryGetOnlyLetters();
		System.out.println("Please enter supplier payment method (0-10):");
		int paymentmethod = Integer.parseInt(_vu.tryGetNumber(0,10));
		System.out.println("Please enter supplier bank number:");
		String banknumber = _vu.tryGetNumber();
		System.out.println("Please enter number of contacts:");
		int i = Integer.parseInt(_vu.tryGetNumber());
		ArrayList<Contact> contacts = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			System.out.println("Please enter tel number of contact:");
			String _tel = _vu.tryGetNumber();
			System.out.println("Please email of contact:");
			String _email = _vu.tryGetEmail();
			System.out.println("Please name of contact:");
			String _name = _vu.tryGetOnlyLetters();
			contacts.add(_sp.createContact(_name, _email, _tel));
		}
		try{
			_sp.createSupplier(cn,name,paymentmethod,banknumber,contacts);
		}
		catch(SQLException e){
			System.out.println(_vu.exceptionHandler(e));
		}
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
			_sp.createProduct(producername,productname,dayofvalid,weight,supplierid);
		}
		catch(SQLException e){
			System.out.println(_vu.exceptionHandler(e));
		}		 
	}
	
	public void supplierProducts(){
		_vu.clear();
		System.out.println("Please enter supplier company number:");
		String supllierID = _vu.tryGetNumber();
		supplierProducts(supllierID);
	}
	
	public void supplierProducts(String supllierID){
		System.out.println("The products of this supplier are:");
		
		ArrayList<SupplierProduct> supllierPro = new ArrayList<>();
		try {
			supllierPro = _sp.getAllSupllierProduct(supllierID);
		} catch (SQLException e) {
			_vu.exceptionHandler(e);
			return;
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
					_vu.showResult(_sp.searchProduct(new int[]{choise-1},new String[]{query}));
				} catch (SQLException e) {
					_vu.exceptionHandler(e);
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
					_vu.showResult(_sp.searchProducer(new int []{choise},new String []{query}));
				} catch (SQLException e) {
					System.out.println(_vu.exceptionHandler(e));
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
				if(!menu[choise-1].equals("All")){
					System.out.println("Enter query: ");
					query = scn.nextLine();
				}
				else
					query = "";
				try {
					_vu.showResult(_sp.searchSupplier(new int[]{choise-1},new String[]{query}));
				} catch (SQLException e) {
					System.out.println(_vu.exceptionHandler(e));
				}
				}
			}
	}
	
	public String chooseCN()
	{
		try {
			ArrayList<Supplier> sup = _sp.searchSupplier(new int[]{0}, new String[]{""});
			for (int i = 0; i < sup.size(); i++) {
				System.out.println(i + ". " + sup.get(0));
			}
			System.out.println("Choose supplier: ");
			int choise = Integer.parseInt(_vu.tryGetNumber(0, sup.size() - 1));
			return sup.get(choise).get_CN();
		} catch (SQLException e) {
			_vu.exceptionHandler(e);
			return "";
		}
	}
	public SupplierProduct chooseSupplierProduct(String CN)
	{
		try {
			ArrayList<SupplierProduct> sup = _sp.getAllSupllierProduct(CN);
			for (int i = 0; i < sup.size(); i++) {
				System.out.println(i + ". " + sup.get(0));
			}
			System.out.println("Choose product: ");
			int choise = Integer.parseInt(_vu.tryGetNumber(0, sup.size() - 1));
			return sup.get(choise);
		} catch (SQLException e) {
			_vu.exceptionHandler(e);
			return null;
		}
	}
	
}
