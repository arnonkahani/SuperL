package PL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class viewUtils {

	Scanner scn;
	
	public viewUtils(){
		scn = new Scanner(System.in);
	}
	
	public boolean isPosFloat(float f)
	{
		return f>0;
	}
	
	public boolean isEmail(String email)
	{
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(EMAIL_REGEX);
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
		while(choise<0 || choise > menu.length-1){
			if(error){
				System.out.println("Out of range try again");
				error = false;
			}
			printList(menu);
			choise = scn.nextInt();
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
			choise = scn.nextInt();
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
			choise = scn.nextInt();
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
		if(result == null)
			System.out.println("There are no onjects matching this search");
		else{
			for (int i = 0; i < result.size(); i++) {
				System.out.println("---------------- " + (i + 1) + "---------------- ");
				System.out.println(result.get(i));
			}
		System.out.println("Press enter to return");
		scn.hasNext();
		}
		
	}
	
	public ArrayList<String> getNamesOfEnum(Enum[] list){
		ArrayList<String> ret_list = new ArrayList<>();
		for (int i = 0; i < list.length; i++) {
			ret_list.add(list[i].name());
		}
		return ret_list;
	}
	
	private String[] addReturn(String[] list)
	{
		String [] menu = new String[list.length + 1];
		for (int i = 0; i < list.length; i++) {
			menu[i] = list[i];
		}
		menu[list.length] = "Return";
		return menu;
	}

}
