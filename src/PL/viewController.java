package PL;


import java.sql.SQLException;
import java.util.Scanner;



import BE.*;
import BL.*;

public class viewController {
	private Scanner scn;
	
	private supplyAgreementView _sav;
	private viewUtils _vu;
	private supplierView _sv;
	private orderView _ov;
	private BLFactory _bc; 
	private ProductView _pv;
	
	public viewController() throws SQLException{
		_vu = new viewUtils();
		System.out.println("Is this the first time the program is running? Yes = 1 , No = 0");
		boolean first_time = 1 == Integer.parseInt(_vu.tryGetNumber(0, 1));
		
		_bc = new BLFactory(first_time); 
		_vu = new viewUtils();
		_pv = new ProductView((ProductManager) _bc.getSupplierLogic().getManager(Product.class), _vu);
		_sv = new supplierView(_vu,_pv,(SupplierManager) _bc.getSupplierLogic().getManager(Supplier.class));
		_sav = new supplyAgreementView(_vu,(SupplyAgreementManager) _bc.getSupplierLogic().getManager(SupplyAgreement.class),_sv);
		_ov = new orderView(_vu,(OrderManager) _bc.getSupplierLogic().getManager(Order.class),_sav);
		
		
		run();
	}
	public void run()
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
		String menu[] = {"Supplier","Product","Supply Agreement","Order","Return"};
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
				_ov.createOrder();
				break;
			case 5:
				return;
			}
		}
		
	}
	
	



	
}

