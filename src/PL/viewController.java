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
	private supplyAgreementView _sav;
	private OrderManager _om;
	private viewUtils _vu;
	private supplierView _sv;
	private orderView _ov;
	
	public viewController(){
		_vu = new viewUtils();
		_sav = new supplyAgreementView(_vu, _sam);
		_sv = new supplierView();
		
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
				_ov.printOreder();
				break;
			case 2:
				_sv.supplierProduct();
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
				_sav.searchMenu();
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
		_vu.clear();
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

