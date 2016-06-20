package com.SupplierStorage.PL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewUtils {

	Scanner scn;
	
	public ViewUtils(){
		scn = new Scanner(System.in);
	}
	
	public boolean isPosFloat(float f)
	{
		return f>0;
	}
	
	public boolean isEmail(String email)
	{
		String EMAIL_REGEX = "";
		return true;
	}
	
	public boolean isOnlyInt(String num)
	{
		return num.matches("[0-9]+");
	}
	
	public boolean isPosInt(int i)
	{
		return i>=0;
	}


	public void clear()
	{
		final String operatingSystem = System.getProperty("os.name");

		if (operatingSystem .contains("Windows")) {
		    try {
				Runtime.getRuntime().exec("cls");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
		    try {
				Runtime.getRuntime().exec("clear");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void printList(Enum [] list)
	{
		for (int i = 0; i < list.length; i++) {
			System.out.println((i+1) +". " + list[i].name());
		}
	}
	public void printList(String [] list)
	{
		for (int i = 0; i < list.length; i++) {
			System.out.println((i+1) +". " + list[i]);
		}
	}
	public void printList(ArrayList<String> list)
	{
		for (int i = 0; i < list.size(); i++) {
			System.out.println((i+1) +". " + list.get(i));
		}
	}
	public int listChoose(String[] menu) {
		int choise = -1;
		boolean error = false;
		while(choise<1 || choise > menu.length){
			if(error){
				System.out.println("Out of range try again");
				error = false;
			}
			printList(menu);
			choise = Integer.parseInt(tryGetNumber());
			if(choise<0 || choise > menu.length-1)
			{
				error = true;
				clear();
			}
				
		}
		return choise;
	}
	
	public int listChoose(ArrayList<String> menu) {
		int choise = -1;
		boolean error = false;
		while(choise<0 || choise > menu.size()-1){
			if(error){
				System.out.println("Out of range try again");
				error = false;
			}
			printList(menu);
			choise = Integer.parseInt(tryGetNumber()) -1;
			if(choise<0 || choise > menu.size()-1)
			{
				error = true;
				clear();
			}
				
		}
		return choise;
	}
	
	public int listChoose(Enum[] menu) {
		int choise = -1;
		boolean error = false;
		while(choise<0 || choise > menu.length-1){
			if(error){
				System.out.println("Out of range try again");
				error = false;
			}
			printList(menu);
			choise = Integer.parseInt(tryGetNumber()) - 1;
			if(choise<0 || choise > menu.length-1)
			{
				error = true;
				clear();
			}
				
		}
		return choise;
	}
	
	public void showResult(ArrayList<?> result){
		clear();
		System.out.println("Search Results");
		if(result.size() == 0)
			System.out.println("There are no objects matching this search");
		else{
			for (int i = 0; i < result.size(); i++) {
				System.out.println("---------------- " + (i + 1) + "---------------- ");
				System.out.println(result.get(i));
			}
		System.out.println("Press enter to return");
		scn.nextLine();
		}
		
	}
	
	public ArrayList<String> getNamesOfEnum(Enum[] list){
		ArrayList<String> ret_list = new ArrayList<>();
		for (int i = 0; i < list.length; i++) {
			ret_list.add(list[i].name());
		}
		return ret_list;
	}
	
	public String[] createMenu(String[] list)
	{
		String [] menu = new String[list.length + 1];
		for (int i = 0; i < list.length; i++) {
			menu[i] = list[i];
		}
		menu[list.length] = "Return";
		return menu;
	}
	
	public String exceptionHandler(SQLException e)
	{
		String exp_msg = "";
		if(e.getMessage().startsWith("UNIQUE"))
        {
           String msg = e.getMessage();
           String object = msg.substring(msg.indexOf(':')+1, msg.indexOf('.'));
           exp_msg = "There is a" + object.toLowerCase() + " with the same " + msg.substring(msg.indexOf('.')+1).toLowerCase();
        }
        else if(e.getMessage().startsWith("FOREIGN"))
        {
                  exp_msg = "There is a problem with forgein relations";
        }
        else{
        	exp_msg = e.getClass().getName() + ": " + e.getMessage() ;
         
        }
		return exp_msg;
	}

	public String tryGetNumber() {
		
		String num = scn.nextLine();
		while(!isOnlyInt(num))
		{
			System.out.println("Please enter number:");
			num = scn.nextLine();
		}
		return num;
	}

	public String tryGetOnlyLetters() {
		String str = scn.nextLine();
		while(!isOnlyLetters(str))
		{
			System.out.println("Please enter only letters:");
			str = scn.nextLine();
		}
		return str;
	}

	private boolean isOnlyLetters(String str) {
		return str.matches("[a-zA-Z]+");
	}

	public String tryGetNumber(int i, int j) {
		
		String num = tryGetNumber();
		while(Integer.parseInt(num)<i || Integer.parseInt(num)>j){
			System.out.println("Out of range");
			num = scn.nextLine();
			while(!isOnlyInt(num))
			{
				System.out.println("Please enter number:");
				num = scn.nextLine();
			}
			
		}
		return num;
		
			
	}

	public String tryGetEmail() {
		String email = scn.nextLine();
		while(!isEmail(email))
		{
			System.out.println("Please enter only email format:");
			email = scn.nextLine();
		}
		return email;
	}

	public String tryGetFloat() {
		String flo = scn.nextLine();
		boolean flag = false;
		while(!flag){
		try{
			Float.parseFloat(flo);
			flag=true;
			
		}
		catch(Exception e){
			System.out.println("not float");
			flo = scn.nextLine();
		}
		}
		return flo;
	}
	
	public <T> T choose(ArrayList<T> list){
			if(list.isEmpty())
				return null;
			T product = null;
			for (int i = 0; i < list.size(); i++) {
				System.out.println((i+1) + ". " + list.get(i));
			}
			int choise = Integer.parseInt(tryGetNumber(1, list.size()));
			product = list.get(choise-1);
			return product;
		
	}
	

}
