package PL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import BE.Catagory;
import BE.Producer;
import BE.Product;
import BE.SubCatagory;
import BE.SubSubCatagory;
import BE.SupplierProduct;
import BL.LogicManager;
import BL.ProductManager;

public class ProductView {

	private ProductManager _pm;
	private Scanner scn;
	private viewUtils _vu;
	
	public ProductView(ProductManager logicManager,viewUtils vu){
		_pm = logicManager;
		scn = new Scanner(System.in);
		_vu = vu;
	}
	
	public void createProduct(){
		_vu.clear();
		Object[] values = new Object[10];
		System.out.println("Choose insert option:");
		int choise = _vu.listChoose(new String[]{"Exsisting product","New Product"}) - 1;
		if(choise == 1){
		System.out.println("Please enter producer name:");
		String producername = _vu.tryGetOnlyLetters();
		values[0] = producername;
		
		System.out.println("Please enter product name:");
		String productname = scn.nextLine();
		values[3] = productname;
		
		System.out.println("Please enter Shelf Life:");
		int dayofvalid = Integer.parseInt(_vu.tryGetNumber());
		values[2] = dayofvalid;
		
		System.out.println("Please enter weight:");
		float weight =Float.parseFloat(_vu.tryGetFloat());
		values[1] = weight;
		
		System.out.println("Please enter supplierCN:");
		String supplierid = _vu.tryGetNumber();
		values[9] = supplierid;
		
		System.out.println("Please choose catagory:");
		String catagory = chooseCatagory();
		values[4] = catagory;
		
		System.out.println("Please choose sub catagory:");
		String sub_catagory = chooseSubCatagory(catagory);
		values[5] = sub_catagory;
		
		System.out.println("Please choose sub sub catagory:");
		String sub_sub_catagory = chooseSubSubCatagory(catagory,sub_catagory);
		values[6] = sub_sub_catagory;
		
		System.out.println("Please store price:");
		float price = Float.parseFloat(_vu.tryGetFloat());
		values[8] = price;
		
		System.out.println("Please minimum amount:");
		int amount = Integer.parseInt(_vu.tryGetNumber());
		values[7] = amount;
		try{
			_pm.create(values);
		}
		catch(SQLException e){
			System.out.println(_vu.exceptionHandler(e));
		}
		}
		else{
		 Product pro = chooseProducts();
		 if(pro == null){
			 System.out.println("No products - click enter to return");
			 scn.nextLine();
			 return;
		
		 }
		System.out.println("Please enter supplierCN:");
		String supplierid = _vu.tryGetNumber();
		try {
			_pm.createFromProduct(pro,supplierid);
		} catch (SQLException e) {
			System.out.println(_vu.exceptionHandler(e));
		}
		}
		 
				 
	}
	
	private Product chooseProducts() {
		Product product = null;
		try {
			ArrayList<Product> products = _pm.getAllProducts();
			for (int i = 0; i < products.size(); i++) {
				System.out.println((i+1) + ". " + products.get(i));
			}
			int choise = Integer.parseInt(_vu.tryGetNumber(1, products.size()));
			product = products.get(choise-1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}

	private String chooseCatagory() {
		String catagory_name = "";
		try {
			ArrayList<Catagory> catagory = _pm.getAllCatagory();
			if(catagory.size() == 0)
			{
				System.out.println("No Catagories - Please enter a new catagory");
				catagory_name = _vu.tryGetOnlyLetters();
				
			}
			else{
				ArrayList<String> all_catagory = new ArrayList<>();
				for (Catagory cat : catagory) {
					all_catagory.add(cat.getName_cat());
				}
				catagory_name = all_catagory.get(_vu.listChoose(all_catagory));
			}
		} catch (SQLException e) {
			System.out.println("No Catagories - Please enter a new catagory");
			catagory_name = _vu.tryGetOnlyLetters();
		}
		return catagory_name;
	}

	private String chooseSubCatagory(String catagory) {
		String sub_catagory_name = "";
		try {
			ArrayList<SubCatagory> sub_catagory = _pm.getAllSubCatagory(catagory);
			if(sub_catagory.size() == 0)
			{
				System.out.println("No Sub Catagories - Please enter a new sub catagory");
				sub_catagory_name = _vu.tryGetOnlyLetters();
				
			}
			else{
				ArrayList<String> all_sub_catagory = new ArrayList<>();
				for (SubCatagory cat : sub_catagory) {
					all_sub_catagory.add(cat.getName_scat());
				}
				sub_catagory_name = all_sub_catagory.get(_vu.listChoose(all_sub_catagory));
			}
		} catch (SQLException e) {
			System.out.println("No Sub Catagories - Please enter a new sub catagory");
			sub_catagory_name = _vu.tryGetOnlyLetters();
		}
		return sub_catagory_name;
	}
	
	private String chooseSubSubCatagory(String catagory,String sub_catagory) {
		String sub_sub_catagory_name = "";
		try {
			ArrayList<SubSubCatagory> sub_sub_catagory = _pm.getAllSubSubCatagory(catagory,sub_catagory);
			if(sub_sub_catagory.size() == 0)
			{
				System.out.println("No Sub Sub Catagories - Please enter a new sub sub catagory");
				sub_sub_catagory_name = _vu.tryGetOnlyLetters();
				
			}
			else{
				ArrayList<String> all_sub_sub_catagory = new ArrayList<>();
				for (SubSubCatagory subcat : sub_sub_catagory) {
					all_sub_sub_catagory.add(subcat.getName_sscat());
				}
				sub_sub_catagory_name = all_sub_sub_catagory.get(_vu.listChoose(all_sub_sub_catagory));
			}
		} catch (SQLException e) {
			System.out.println("No Sub Sub Catagories - Please enter a new sub catagory");
			sub_sub_catagory_name = _vu.tryGetOnlyLetters();
		}
		return sub_sub_catagory_name;
	}
	
	public void productSearch(){
		_vu.clear();
		String [] menu = _pm.getFileds();
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
					_vu.showResult(_pm.search(new int[]{choise-1},new String[]{query}));
				} catch (SQLException e) {
					_vu.exceptionHandler(e);
				}
				}
			}
		}

	public SupplierProduct searchSupplierProduct(String CN) {
	
			
			
			SupplierProduct product = null;
			try {
				ArrayList<SupplierProduct> products = _pm.searchSupplierProduct(new int[]{2}, new String[]{CN});
				for (int i = 0; i < products.size(); i++) {
					System.out.println((i+1) + ". " + products.get(i));
				}
				int choise = Integer.parseInt(_vu.tryGetNumber(1, products.size()));
				product = products.get(choise-1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return product;
	}

	public ArrayList<SupplierProduct> getSupplierProduct(String CN) throws SQLException {
		return _pm.searchSupplierProduct(new int[]{2}, new String[]{CN});
	}
	
	
	
}
