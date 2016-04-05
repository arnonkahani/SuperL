package view;
import java.util.ArrayList;
import java.util.Scanner;

import logic.Contact;
import logic.SupplierManager;

public class viewController {
	private Scanner scn = new Scanner(System.in);
	private SupplierManager _sp = new SupplierManager();
	
	
	
	
	
	public void createSupplier(){
		System.out.println("Please enter supplier name:");
		String name = scn.nextLine();
		System.out.println("Please enter supplier payment method (0-10):");
		int paymentMethod = scn.nextInt();
		System.out.println("Please enter supplier name:");
		String bankNumber = scn.nextLine();
		System.out.println("Please enter number of contacts:");
		int i = scn.nextInt();
		ArrayList<Contact> contacts = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			System.out.println("Please enter number of contacts:");
			String _tel = scn.nextLine();
			System.out.println("Please enter number of contacts:");
			String _email = scn.nextLine();
			System.out.println("Please enter number of contacts:");
			String _name = scn.nextLine();
			contacts.add(new Contact(_tel, _email, _name));
		}
		_sp.create(name,paymentMethod,bankNumber,contacts);
		
		
	}
}

