package PL;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import BE.Contact;
import BE.Supplier;
import BE.SupplierProduct;
import BL.SupplierManager;

public class SupplierView {
	
	private SupplierManager _sp;
	private ProductView _pv;
	private Scanner scn;
	private ViewUtils _vu;
	
	public SupplierView(ViewUtils vu,ProductView pv ,SupplierManager logicManager) {
		_sp=logicManager;
		_vu=vu;
		_pv=pv;
		scn = new Scanner(System.in);
	}

	public void createSupplier(){
		
		_vu.clear();
		System.out.println("Supplier Creation");
		System.out.println("Please enter company number:");
		String cn = _vu.tryGetNumber();
		System.out.println("Please enter supplier Address:");
		String address = scn.nextLine();
		System.out.println("Please enter supplier name:");
		String name = _vu.tryGetOnlyLetters();
		System.out.println("Please enter supplier payment method (0-10):");
		int paymentmethod = Integer.parseInt(_vu.tryGetNumber(0,10));
		System.out.println("Please enter supplier bank number:");
		String banknumber = _vu.tryGetNumber();
		System.out.println("Please enter number of contacts:");
		int i = Integer.parseInt(_vu.tryGetNumber(1,100));
		ArrayList<Contact> contacts = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			System.out.println("Please enter tel number of contact:");
			String _tel = _vu.tryGetNumber();
			System.out.println("Please email of contact:");
			String _email = _vu.tryGetEmail();
			System.out.println("Please name of contact:");
			String _name = _vu.tryGetOnlyLetters();
			contacts.add(new Contact(_name, _email, _tel));
		}
		try{
			Supplier sp = new Supplier(name, cn, paymentmethod, banknumber);
			sp.set_contacts(contacts);
			sp.set_address(address);
			_sp.create(sp);
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
			supllierPro = _pv.getSupplierProduct(supllierID);
		} catch (SQLException e) {
			_vu.exceptionHandler(e);
			return;
		}
		for (int j = 0; j < supllierPro.size(); j++) {
			System.out.println(supllierPro.get(j));
		}
	}
	

	
	
	
	public void supplierSearch(){
		_vu.clear();
		String [] menu = _sp.getSupplierFileds();
		
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
			ArrayList<Supplier> sup = _sp.getAllSuplliers();
			if(sup.size() == 0)
				return null;
			for (int i = 0; i < sup.size(); i++) {
				System.out.println(i + ". " + sup.get(i));
			}
			System.out.println("Choose supplier: ");
			int choise = Integer.parseInt(_vu.tryGetNumber(0, sup.size() - 1));
			return sup.get(choise).get_CN();
		} catch (SQLException e) {
			_vu.exceptionHandler(e);
			return null;
		}
	}
	
	public SupplierProduct chooseSupplierProduct(String CN)
	{
		return _pv.searchSupplierProduct(CN);
		
	}
	
}
