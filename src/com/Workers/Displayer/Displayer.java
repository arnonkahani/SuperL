package com.Workers.Displayer;

import java.util.Scanner;

public class Displayer {
	Scanner sc;
	
	public Displayer(){
		sc = new Scanner(System.in);
	}
	
	public void DisplayText(String text){
		System.out.print(text);
	}
	
	public String getInput(){
		String userInput = "";
		userInput = sc.nextLine();
		return userInput;
	}
	
	public void closeDisplay(){
		//sc.close();
	}
}
