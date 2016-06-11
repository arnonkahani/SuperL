package com.SupplierStorage.PL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.SupplierStorage.BE.Catagory;
import com.SupplierStorage.BE.Producer;
import com.SupplierStorage.BE.Product;
import com.SupplierStorage.BE.SubCatagory;
import com.SupplierStorage.BE.SubSubCatagory;
import com.SupplierStorage.BE.SupplierProduct;
import com.SupplierStorage.BL.ProductManager;

public class ProductView {

	private ProductManager _product_manager;
	private SupplierView _supplier_view;
	private Scanner scn;
	private ViewUtils _view_utils;
	
	public ProductView(ProductManager logicManager,ViewUtils vu){
		_product_manager = logicManager;
		scn = new Scanner(System.in);
		_view_utils = vu;
	}
	public void setSupplierView(SupplierView supplier_view){
		_supplier_view = supplier_view;
	}
	
	public void createProduct(){
		_view_utils.clear();
		
		System.out.println("Choose insert option:");
		int choise = _view_utils.listChoose(new String[]{"Exsisting product","New Product"}) - 1;
		if(choise == 1){
		System.out.println("Please enter producer name:");
		String producername = _view_utils.tryGetOnlyLetters();
		
		
		System.out.println("Please enter product name:");
		String productname = scn.nextLine();
	
		
		System.out.println("Please enter Shelf Life:");
		int dayofvalid = Integer.parseInt(_view_utils.tryGetNumber());
	
		
		System.out.println("Please enter weight:");
		float weight =Float.parseFloat(_view_utils.tryGetFloat());
	
		
		System.out.println("Please enter supplierCN:");
		String supplierid = _supplier_view.chooseCN();
		if(supplierid == null)
		{
			System.out.println("No suplliers - Press Enter To Return");
			scn.nextLine();
			return;
		}
	
		
		System.out.println("Please choose catagory:");
		String catagory = chooseCatagory();
		
		
		System.out.println("Please choose sub catagory:");
		String sub_catagory = chooseSubCatagory(catagory);
	
		
		System.out.println("Please choose sub sub catagory:");
		String sub_sub_catagory = chooseSubSubCatagory(catagory,sub_catagory);
		
		
		System.out.println("Please store price:");
		float price = Float.parseFloat(_view_utils.tryGetFloat());
		
		
		System.out.println("Please minimum amount:");
		int amount = Integer.parseInt(_view_utils.tryGetNumber());
		
		try{
			Product pr = new Product(weight,dayofvalid,productname);
			pr.set_producer(new Producer(producername));
			pr.set_category(catagory);
			pr.set_sub_category(sub_catagory);
			pr.set_sub_sub_category(sub_sub_catagory);
			pr.set_min_amount(amount);
			pr.set_price(price);
			_product_manager.create(pr);
			_product_manager.createSupplyProduct(pr, supplierid);
		}
		catch(SQLException e){
			System.out.println(_view_utils.exceptionHandler(e));
		}
		}
		else{
		 Product pro = chooseProducts();
		 if(pro == null){
			 System.out.println("No products - click enter to return");
			 scn.nextLine();
			 return;
		
		 }
		 String supplierid = _supplier_view.chooseCN();
			if(supplierid == null)
			{
				System.out.println("No suplliers - Press Enter To Return");
				scn.nextLine();
				return;
			}
		try {
			_product_manager.createSupplyProduct(pro,supplierid);
		} catch (SQLException e) {
			System.out.println(_view_utils.exceptionHandler(e));
		}
		}
		 
				 
	}
	
	private Product chooseProducts() {
		ArrayList<Product> products = new ArrayList<>();
		try {
			products = _product_manager.getAllProducts();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return _view_utils.choose(products);
	}

	private String chooseCatagory() {
		String catagory_name = "";
		try {
			ArrayList<Catagory> catagory = _product_manager.getAllCatagory();
			if(catagory.size() == 0)
			{
				System.out.println("No Catagories - Please enter a new catagory");
				catagory_name = _view_utils.tryGetOnlyLetters();
				
			}
			else{
				ArrayList<String> all_catagory = new ArrayList<>();
				for (Catagory cat : catagory) {
					all_catagory.add(cat.getName_cat());
				}
				catagory_name = all_catagory.get(_view_utils.listChoose(all_catagory));
			}
		} catch (SQLException e) {
			System.out.println("No Catagories - Please enter a new catagory");
			catagory_name = _view_utils.tryGetOnlyLetters();
		}
		return catagory_name;
	}

	private String chooseSubCatagory(String catagory) {
		String sub_catagory_name = "";
		try {
			ArrayList<SubCatagory> sub_catagory = _product_manager.getAllSubCatagory(catagory);
			if(sub_catagory.size() == 0)
			{
				System.out.println("No Sub Catagories - Please enter a new sub catagory");
				sub_catagory_name = _view_utils.tryGetOnlyLetters();
				
			}
			else{
				ArrayList<String> all_sub_catagory = new ArrayList<>();
				for (SubCatagory cat : sub_catagory) {
					all_sub_catagory.add(cat.getName_scat());
				}
				sub_catagory_name = all_sub_catagory.get(_view_utils.listChoose(all_sub_catagory));
			}
		} catch (SQLException e) {
			System.out.println("No Sub Catagories - Please enter a new sub catagory");
			sub_catagory_name = _view_utils.tryGetOnlyLetters();
		}
		return sub_catagory_name;
	}
	
	private String chooseSubSubCatagory(String catagory,String sub_catagory) {
		String sub_sub_catagory_name = "";
		try {
			ArrayList<SubSubCatagory> sub_sub_catagory = _product_manager.getAllSubSubCatagory(catagory,sub_catagory);
			if(sub_sub_catagory.size() == 0)
			{
				System.out.println("No Sub Sub Catagories - Please enter a new sub sub catagory");
				sub_sub_catagory_name = _view_utils.tryGetOnlyLetters();
				
			}
			else{
				ArrayList<String> all_sub_sub_catagory = new ArrayList<>();
				for (SubSubCatagory subcat : sub_sub_catagory) {
					all_sub_sub_catagory.add(subcat.getName_sscat());
				}
				sub_sub_catagory_name = all_sub_sub_catagory.get(_view_utils.listChoose(all_sub_sub_catagory));
			}
		} catch (SQLException e) {
			System.out.println("No Sub Sub Catagories - Please enter a new sub catagory");
			sub_sub_catagory_name = _view_utils.tryGetOnlyLetters();
		}
		return sub_sub_catagory_name;
	}
	
	public void productSearch(){
		_view_utils.clear();
		String [] menu = _product_manager.getFileds();
		menu = _view_utils.createMenu(menu);
		int choise = -1;
		String query;
		while(true)
		{
			System.out.println("Product Search Menu");
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
					_view_utils.showResult(_product_manager.search(new int[]{choise-1},new String[]{query}));
				} catch (SQLException e) {
					_view_utils.exceptionHandler(e);
				}
				}
			}
		}

	public SupplierProduct searchSupplierProduct(String CN) {
	
			
			
			SupplierProduct product = null;
			try {
				ArrayList<SupplierProduct> products = _product_manager.searchSupplierProduct(new int[]{2}, new String[]{CN});
				for (int i = 0; i < products.size(); i++) {
					System.out.println((i+1) + ". " + products.get(i));
				}
				int choise = Integer.parseInt(_view_utils.tryGetNumber(1, products.size()));
				product = products.get(choise-1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return product;
	}

	public ArrayList<SupplierProduct> getSupplierProduct(String CN) throws SQLException {
		return _product_manager.searchSupplierProduct(new int[]{2}, new String[]{CN});
	}
	
	
	
}
