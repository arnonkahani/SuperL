package com.SupplierStorage.PL;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

import com.SupplierStorage.BE.Product;
import com.SupplierStorage.BE.SupplyAgreement.Day;
import com.SupplierStorage.BL.OrderManager;

public class OrderView {
	private OrderManager _order_manager;
	private Scanner scn;
	private ViewUtils _view_utils;
	private SupplyAgreementView _supply_agreement_view;

	public OrderView(ViewUtils vu, OrderManager logicManager, SupplyAgreementView sav) {
		_supply_agreement_view = sav;
		_order_manager = logicManager;
		_view_utils = vu;
		scn = new Scanner(System.in);
	}

	public void createOnDemandOrder() {
		_view_utils.clear();
		try {

			System.out.println("Please enter number of products at the order:");
			int n = Integer.parseInt(_view_utils.tryGetNumber());
			HashMap<Product, Integer> product_table = new HashMap<>();
			for (int i = 0; i < n; i++) {
				Product product = _supply_agreement_view.chooseOnDemandAgreementProduct();
				if (product == null) {
					System.out.println("No products");
					return;

				}
				System.out.println("Please enter amount of the product :");
				int l = Integer.parseInt(_view_utils.tryGetNumber());

				product_table.put(product, new Integer(l));
			}

			_order_manager.makeOnDemand(product_table);
		} catch (SQLException e1) {
			System.out.println(_view_utils.exceptionHandler(e1));
		}
	}

	public void createWeeklyOrder() {

		System.out.println("Create Weekly Order");
		System.out.println("Choose Day:");
		_view_utils.printList(Day.values());
		int d = Integer.parseInt(_view_utils.tryGetNumber(1, 7));
		Day day = Day.values()[d - 1];
		//TODO: DELETE
		if(ViewController.debug == false)
		if (day.getValue() == Calendar.getInstance().get(Calendar.DAY_OF_WEEK) || day.getValue() == Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 1) {
			System.out.println("Wrong day - press Enter to return");
			scn.nextLine();
			return;
		}
		_view_utils.clear();
		try {
			System.out.println("Please enter number of products at the order:");
			int n = Integer.parseInt(_view_utils.tryGetNumber());
			HashMap<Product, Integer> product_table = new HashMap<>();
			for (int i = 0; i < n; i++) {
				Product product = _supply_agreement_view.chooseWeeklyAgreementProduct(d);
				if (product == null) {
					System.out.println("No products");
					return;

				}
				System.out.println("Please enter amount of the product :");
				int l = Integer.parseInt(_view_utils.tryGetNumber());

				product_table.put(product, new Integer(l));
			}

			_order_manager.makeWeeklyOrder(product_table, day);
			
		} catch (SQLException e1) {
			System.out.println(_view_utils.exceptionHandler(e1));
		}
	}

	public void printOrder() {
		_view_utils.clear();

		System.out.println("Please enter order's number:");
		String str = scn.nextLine();

		try {
			System.out.println(_order_manager.getOrderByID(str));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void searchMenu() {
		_view_utils.clear();
		String[] menu = _order_manager.getFileds();
		menu = _view_utils.createMenu(menu);
		int choise = -1;
		String query;
		while (true) {
			System.out.println("Order Search Menu");
			choise = _view_utils.listChoose(menu);
			if (menu[choise - 1].equals("Return"))
				return;
			else {
				if (!menu[choise - 1].equals("All")) {
					System.out.println("Enter query: ");
					query = scn.nextLine();
				} else
					query = "";
				try {
					_view_utils.showResult(_order_manager.search(new int[] { choise - 1 }, new String[] { query }));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
