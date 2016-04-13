package PL;

import java.io.IOException;
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

}
