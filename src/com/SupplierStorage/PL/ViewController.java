package com.SupplierStorage.PL;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


import com.Common.Models.Order;
import com.SupplierStorage.BE.*;
import com.SupplierStorage.BL.*;

public class ViewController {
	private Scanner scn;
	public static boolean debug = false;
	private SupplyAgreementView _sav;
	private ViewUtils _vu;
	private SupplierView _sv;
	private OrderView _ov;
	private BLFactory _bc; 
	private ProductView _pv;
	private GUI_storage _gs;
	
	public ViewController(boolean first_time, Connection c) throws SQLException{
		_vu = new ViewUtils();
		scn = new Scanner(System.in);
		System.out.println("Debug? true/false");
		debug = scn.nextBoolean();
		
		System.out.println("Is this the first time the program is running? Yes = 1 , No = 0");

		
		_bc = new BLFactory(first_time,c);
		_vu = new ViewUtils();
		_pv = new ProductView((ProductManager) _bc.getSupplierLogic().getManager(Product.class), _vu);
		_sv = new SupplierView(_vu,_pv,(SupplierManager) _bc.getSupplierLogic().getManager(Supplier.class));
		_pv.setSupplierView(_sv);
		_sav = new SupplyAgreementView(_vu,(SupplyAgreementManager) _bc.getSupplierLogic().getManager(SupplyAgreement.class),_sv);
		_ov = new OrderView(_vu,(OrderManager) _bc.getSupplierLogic().getManager(Order.class),_sav);
		_gs= new GUI_storage(_bc.getStorageLogic());
		
		
		

	}
	public void showSupplier()
	{
		String menu[] = {"Create","Search","Other","Quit"};
		int choise = -1;
		while(true)
		{
			System.out.println("Main Menu");
			choise = _vu.listChoose(menu);
			switch(choise)
			{
				case 1:
					createMenu();
					break;
				case 2:
					searchMenu();
					break;
				case 3:
					otherMenu();
					break;
				case 4:
					return;
			}
		}
	}
	public void showStorage()
	{
		_gs.start(0);
	}

	public void getSupply(ArrayList<OrderProduct> products)
	{
		_bc.getStorageLogic().getSupply(products);
	}
	
	private void otherMenu() {
		_vu.clear();
		String menu[] = {"Print Order","Supllied Products From Supplier","Return"};
		int choise = -1;
		while(true)
		{
			System.out.println("Other Menu");
			choise = _vu.listChoose(menu);
			switch(choise)
			{
			case 1:
				_ov.printOrder();
				break;
			case 2:
				_sv.supplierProducts();
				break;
			case 3:
				return;
			}
		}
		
	}
	private void searchMenu() {
		String menu[] = {"Supplier","Order","Product","Supply Agreement","Supply Agreement Product","Return"};
		int choise = -1;
		while(true)
		{
		System.out.println("Search Menu");
		choise = _vu.listChoose(menu);
			switch(choise)
			{
			case 1:
				_sv.supplierSearch();
				break;
			case 2:
				_ov.searchMenu();
				break;
			case 3:
				_pv.productSearch();
				break;
			case 4:
				_sav.searchSupplyAgreement();
				break;
			case 5:
				_sav.searchSupplyAgreementProduct();
				break;
			case 6:
				return;
			}
		}
		
	}
	private void createMenu() {
		_vu.clear();
		String menu[] = {"Supplier","Product","Supply Agreement","On Demand Order","Weekly Order","Return"};
		int choise = -1;
		while(true)
		{
			System.out.println("Creation Menu");
			choise = _vu.listChoose(menu);
			switch(choise)
			{
			case 1:
				_sv.createSupplier();
				break;
			case 2:
				_pv.createProduct();
				break;
			case 3:
				_sav.createSupplyAgreement();
				break;
			case 4:
				_ov.createOnDemandOrder();
				break;
			case 5:
				_ov.createWeeklyOrder();
				break;
			case 6:
				return;
			}
		}
		
	}
	
	



	
}

