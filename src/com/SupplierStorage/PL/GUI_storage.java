package com.SupplierStorage.PL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.Common.Models.Order;
import com.SupplierStorage.BE.INS_product;
import com.SupplierStorage.BL.StorageLogic;

public class GUI_storage {

	String catagory ="";
	String sub_catagory ="";
	String sub_sub_catagory ="";
	String product_name ="";
	String producer_name="";
	ArrayList<String> cat = new ArrayList<String>();
	ArrayList<String> sub_cat = new ArrayList<String>();
	ArrayList<String> sub_sub_cat = new ArrayList<String>();
	ArrayList<String> products = new ArrayList<String>();
	ArrayList<String> producers = new ArrayList<String>();
	StorageLogic logic;
	
    public GUI_storage(StorageLogic logic) {
        this.logic=logic;
    }

public void get_cat(int n)
	{
		if(n==1)
		{
			System.out.println("Invalid option. Please try again");
		}
		cat = logic.cat_search();
		System.out.println("Please select catagory:");
		for (int i = 1; i <= cat.size(); i++)
		{
			System.out.println(i+". "+cat.get(i-1));
		}
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String option = scanner.next();
		if (Integer.parseInt(option)>cat.size())
		{
			get_cat(1);
		}
		else
		{
			catagory = "'"+cat.get(Integer.parseInt(option)-1)+"'";
		}
		
	}
	
public void get_sub_cat(int n){
		if(n==1)
		{
			System.out.println("Invalid option. Please try again");
		}
		sub_cat = logic.sub_cat_search(catagory);
		System.out.println("Please select sub-catagory:");
		for (int i = 1; i <= sub_cat.size(); i++)
		{
			System.out.println(i+". "+sub_cat.get(i-1));
		}
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String option = scanner.next();
		if (Integer.parseInt(option)>cat.size())
		{
			get_sub_cat(1);
		}
		else
		{
			sub_catagory = "'"+sub_cat.get(Integer.parseInt(option)-1)+"'";
		}
		
	}
	
public void get_sub_sub_cat(int n){
		if(n==1)
		{
			System.out.println("Invalid option. Please try again");
		}
		sub_sub_cat = logic.sub_sub_cat_search(catagory,sub_catagory);
		System.out.println("Please select sub-sub-catagory:");
		for (int i = 1; i <= sub_sub_cat.size(); i++)
		{
			System.out.println(i+". "+sub_sub_cat.get(i-1));
		}
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String option = scanner.next();
		if (Integer.parseInt(option)>cat.size())
		{
			get_sub_sub_cat(1);
		}
		else
		{
			sub_sub_catagory = "'"+sub_sub_cat.get(Integer.parseInt(option)-1)+"'";
		}
		
	}
	
public void get_prod(int n){
		if(n==1)
		{
			System.out.println("Invalid option. Please try again");
		}
		products = logic.product_search(catagory,sub_catagory,sub_sub_catagory);
		System.out.println("Please select product:");
		for (int i = 1; i <= products.size(); i++)
		{
			System.out.println(i+". "+products.get(i-1));
		}
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String option = scanner.next();
		if (Integer.parseInt(option)>cat.size())
		{
			get_prod(1);
		}
		else
		{
			product_name = "'"+products.get(Integer.parseInt(option)-1)+"'";
		}
		
	}
	
public void get_producer_prod(int n){
		if(n==1)
		{
			System.out.println("Invalid option. Please try again");
		}
		producers = logic.producer_product_search(catagory,sub_catagory,sub_sub_catagory,product_name);
		System.out.println("Please select producer:");
		for (int i = 1; i <= producers.size(); i++)
		{
			System.out.println(i+". "+producers.get(i-1));
		}
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String option = scanner.next();
		if (Integer.parseInt(option)>cat.size())
		{
			get_producer_prod(1);
		}
		else
		{
			producer_name = "'"+producers.get(Integer.parseInt(option)-1)+"'";
		}
		
	}
	
public void start(int n){
		if (n==0)
		{
		System.out.println("Please select an option:");
		System.out.println("1. Update product condition"+'\n'+"2. add transportation"
				+'\n'+"3. Reports"+"4. remove order\n"+"PRESS # TO EXIT"+'\n');
		}
		else
		{
			System.out.println("Invalid option. Please try again");
		}
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String option = scanner.next();
		 switch(option)
	      {
	         case "1" :
	        	 add_remove(1);
	            break;
	         case "2" :
	        	 add_remove(0);
	        	 break;
	         case "3" :
	        	 reports(0);
	            break;
			  case "4" :
				  delete_order();
				  break;
	         case "#" :
	        	return;
	         default :
	        	 start(1);
	            
	      }
	      
	}
	
public void add_remove(int n) {
	int amount = 0;
	int check;
	if (n == 1) {
		System.out.println("-UPDATE PRODUCT CONDITION-");
		get_cat(0);

		get_sub_cat(0);

		get_sub_sub_cat(0);

		get_prod(0);

		get_producer_prod(0);

		add_remove_a(n, 0,0);
	} else {
		System.out.println("-ADD TRANSPORTATION-");
		System.out.println("Please enter number of products");
		Scanner scanner = new Scanner(System.in);
		String option = scanner.next();
		amount = Integer.parseInt(option);
		for (int i = 0; i < amount; i++) {
			get_cat(0);

			get_sub_cat(0);

			get_sub_sub_cat(0);

			get_prod(0);

			get_producer_prod(0);

			check=add_remove_a(n, 0,amount-i);
			if (check==0){
				i--;
			}
		}


	}
}

public int add_remove_a(int n,int m,int indx){
		int check=0;
		int amount;

		if (m==1)
		{
			System.out.println("Invalid input. Please enter a number");
		}
		else if(m==2)
		{
			System.out.println("Invalid input. Please enter a positive number");
		}
		else if (n==0)
		{
			System.out.println("Please enter amount:");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String option = scanner.next();
			try
			{
				amount = Integer.parseInt(option);
				if (amount < 0)
					add_remove_a(n,2,indx);
				else
					{
						check=add_remove_c(n,0,amount,new Date(),indx);
					}
			}
			catch(Exception e)
			{
				add_remove_a(n,1,indx);
			}
		}
		
			else
			{
				   update_defected();
			}

	return check;

	}

public void update_defected(){
		
		ArrayList<INS_product> ins_products = logic.ins_product_search(catagory,sub_catagory,sub_sub_catagory,product_name,producer_name);
		boolean rep1 =false;
		boolean rep2 =false;
		INS_product ins=null;
		if (!rep1){
			System.out.println("Please select product:");
			for (int i = 1; i <= ins_products.size(); i++)
			{
				System.out.println(i+". "+ins_products.get(i-1));
			}
		}
		if (!rep2){
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String option = scanner.next();
			if (Integer.parseInt(option)>cat.size())
			{
				System.out.println("invalid input please choose product");
				rep1=true;
				update_defected();
			}
			
			else
			{
				ins = ins_products.get(Integer.parseInt(option)-1);	
				System.out.println("Please press 1 for defected or 0 otherwise");
				scanner = new Scanner(System.in);
				option = scanner.next();
				if (Integer.parseInt(option)!=0 && Integer.parseInt(option)!=1)
					{
					System.out.println("invalid input-Please press 1 for defected or 0 otherwise");
					rep2=true;
					update_defected();
					}
				else
					{
					logic.update_defected(ins, (Integer.parseInt(option)));
					}
			}
		}
		else{
			System.out.println("Please press 1 for defected or 0 otherwise");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String option = scanner.next();
			if (Integer.parseInt(option)!=0 && Integer.parseInt(option)!=1)
				{
					System.out.println("invalid input-Please press 1 for defected or 0 otherwise");
					rep2=true;
					update_defected();
					
				}
			else
				{
				logic.update_defected(ins, (Integer.parseInt(option)));
				}
		}
		
	}
	
public int add_remove_c(int n,int m,int amount,Date date,int index) throws SQLException{
		int check=logic.remove_from_storage(catagory, sub_catagory, sub_sub_catagory, product_name,producer_name, amount);
		if (check==0){
			System.err.println("The proccess failed -the amount is higher then the current amount");
			start(0);
		}
		else if (check==1){
			System.err.println("The proccess succeed -NOTICE!! THE CURRENT AMOUNT IS LESS THEN THE MINIMUM ");
			System.err.println("Automatic reservation has been made!!");
			start(0);
		}
		else{
			System.out.println("The product "+product_name+" add to the transportation successfully!");
			if (index==1) {
				cont_supply(n, 0);
			}
		}
	return check;
		
}
	
public void cont_supply(int n,int m){
		
	
	if (m==0)
	{
		if (n==1)
			{
				System.out.println("-GET SUPPLY-");
				System.out.println("The product "+product_name+" recieved successfully!");
			}
		else 
			{
				System.out.println("-ADD TRANSPORTATION-");
				System.out.println("The transportation added successfully!");
			}
		
		System.out.println("PRESS 1 TO CONTINUE OR # FOR MAIN MENU");
	}
	else
	{
		System.out.println("Invalid option. Please try again");
	}
	@SuppressWarnings("resource")
	Scanner scanner = new Scanner(System.in);
	String option = scanner.next();
	 switch(option)
      {
         case "1" :
        	 add_remove(n);
            break;
         case "#" :
        	 start(0);
        	 break;
         default :
        	 cont_supply(n,1);
      }
	}

public void reports(int n){
	if (n==1)
	{
		System.out.println("Invalid option. Please try again");
	}
	System.out.println("-REPORTS-");
	System.out.println("Please select an option:");
	System.out.println("1. By catagory"+'\n'+"2. Defected or invalid products"
			+'\n'+"3. Out of stock"+'\n'+"4. Issue certificate"+"PRESS # FOR MAIN MENU"+'\n');
	@SuppressWarnings("resource")
	Scanner scanner = new Scanner(System.in);
	String option = scanner.next();
	String search = "";
	 switch(option)
     {
        case "1" :
        	search = "CATAGORY";
        	rep_by_cat(0,search);
           break;
        case "2" :
        	search = "DEFECTED";
        	display_report(-1,search);
       	 break;
        case "3" :
        	search = "MIN_AMOUNT";
        	display_report(-1,search);
           break;
        case "4" :
        	search = "ISSUE_CERTIFICATE";
        	display_report(-1,search);
            break;
        case "#" :
        	start(0);
            break;
        default :
        	reports(1);
     }
}

public void rep_by_cat(int n,String search){

	System.out.println("-REPORTS BY CATAGORIES-");
	if (n==0)
	{
		get_cat(0);
	}
	
	else if(n==1)
	{
		get_sub_cat(0);
	}
	else if (n==2)
	{
		get_sub_sub_cat(0);
	}
	else if (n==3)
	{
		get_prod(0);
	}
	else if (n==4) 
	{
		get_producer_prod(0);
	}
	rep_by_cat_a(n,search,0);
}

public void rep_by_cat_a(int n,String search,int m) {
	if (m == 1) {
		System.out.println("Invalid option. Please try again");
	}
	if (n < 4) {
		System.out.println("Please select an option:");
		System.out.println("1. Another filter" + '\n' + "2. Send report" + '\n' + "PRESS # FOR MAIN MENU");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String ans = scanner.next();
		switch (ans) {
			case "1":
				rep_by_cat(n + 1, search);
				break;
			case "2":
				display_report(n, search);
			case "#":
				start(0);
				break;
			default:
				rep_by_cat_a(n, search, 1);
		}
	} else {
		display_report(n, search);
	}



}

public void delete_order(){
	ArrayList<Order> orders = logic.getOrders();
    if (orders.size()==0){
        System.out.print("There are no future orders available to remove \n");
    }
    else {
        System.out.print("Please choose number of order\n");
        for (int i = 0; i < orders.size(); i++) {
            System.out.print(i + 1 + ". ");
            orders.get(i).toString();
        }

        Scanner scanner = new Scanner(System.in);
        String ans = scanner.next();
        int num_order = Integer.parseInt(ans);
        num_order--;
        logic.deleteOrder(orders.get(num_order).getOrderID());
        System.out.print("The order has been removed\n");
    }
    start(0);

}
public void display_report(int n,String search){
	
	System.out.println("PRESS # FOR MAIN MENU");
	
	ArrayList<String> param = new ArrayList<String>();
	param.add(catagory);
	param.add(sub_catagory);
	param.add(sub_sub_catagory);
	param.add(product_name);
	param.add(producer_name);
	String[] category_arr ={"Product Id","Serial Number","Catagory","Sub Catagory","Sub-Sub Catagory","Product Name", "Producer","Weight","Price","Valid Date","Is Defected?"};
	String[] defected_arr ={"Product Id","Serial Number","Catagory","Sub Catagory","Sub-Sub Catagory","Product Name", "Producer","Price","Valid Date","Is Defected?"};
	String[] min_arr ={"Product Id","Catagory","Sub Catagory","Sub-Sub Catagory","Product Name", "Producer","Amount","Min Amount"};
	String[] issu_arr ={"Issue Certificate Id","Issue Date","Product Id","Serial Number","Catagory","Sub Catagory","Sub-Sub Catagory","Product Name", "Producer","Valid Date"};
	ArrayList<ArrayList<String>> report = logic.get_report(param,n+1,search);
	if(search.equals("CATAGORY")){
		for(int i=0; i<category_arr.length; i++){
			System.out.format("%-33s",category_arr[i]);
		}
		System.out.println("");
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	else if(search.equals("DEFECTED")){
		for(int i=0; i<defected_arr.length; i++){
			System.out.format("%-33s",defected_arr[i]);
		}
		System.out.println("");
		System.out.print("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	else if(search.equals("MIN_AMOUNT")){
		for(int i=0; i<min_arr.length; i++){
			System.out.format("%-33s",min_arr[i]);
		}
		System.out.println("");
		System.out.print("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	else if(search.equals("ISSUE_CERTIFICATE")){
		for(int i=0; i<issu_arr.length; i++){
			System.out.format("%-33s",issu_arr[i]);
		}
		System.out.println("");
		System.out.print("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	for(int i=0; i< report.size();i++){
		System.out.println("");
		for(int j=0; j<report.get(i).size();j++){
			System.out.format("%-33s",report.get(i).get(j));
		}
	}
	
	@SuppressWarnings("resource")
	Scanner scanner = new Scanner(System.in);
	String ans = scanner.next();
	if (ans.equals("#")){
		start(0);}
}


	
	

}
