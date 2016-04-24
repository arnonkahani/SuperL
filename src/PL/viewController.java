package PL;


import java.util.Scanner;



import BE.*;
import BL.*;

public class viewController {
	private Scanner scn;
	
	private supplyAgreementView _sav;
	private viewUtils _vu;
	private supplierView _sv;
	private orderView _ov;
	private BusinessController _bc; 
	
	public viewController(){
		_bc = new BusinessController(); 
		_vu = new viewUtils();
		_sav = new supplyAgreementView(_vu,_bc.get_sam());
		_sv = new supplierView(_vu,_bc.get_sm());
		_ov = new orderView(_vu,_bc.get_om(),_sav);
		
		
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
		String menu[] = {"Supplier","Producer","Order","Product","Supply Agreement","Return"};
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
				_sv.producerSearch();
				break;
			case 3:
				_ov.searchMenu();
				break;
			case 4:
				_sv.productSearch();
				break;
			case 5:
				_sav.searchSupplyAgreement();
				break;
			case 6:
				return;
			}
		}
		
	}
	private void createMenu() {
		_vu.clear();
		System.out.println("Creation Menu");
		String menu[] = {"Supplier","Product","Supply Agreement","Order","Return"};
		int choise = -1;
		while(true)
		{
			choise = _vu.listChoose(menu);
			switch(choise)
			{
			case 1:
				_sv.createSupplier();
				break;
			case 2:
				_sv.createProduct();
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

