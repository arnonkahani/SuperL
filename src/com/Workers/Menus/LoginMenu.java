package com.Workers.Menus;

import com.Workers.DatabaseObjects.DAL;
import com.Workers.DatabaseObjects.DALInterface;
import com.Workers.Objects.User;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginMenu {
	public static void main(String[] args) {
		String username,password;
		User me;
		DALInterface dal = null;
		Scanner sc = new Scanner(System.in);
		MenuManager menu;
		
		try {
			dal = DAL.getDAL();
		} catch (SQLException e) {
			e.printStackTrace();
			sc.close();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Welcome to Super-Lee Managment System.");
		System.out.println("To continue, please login.");
		System.out.print("Username:");
		username = sc.next();
		System.out.print("Password:");
		password = sc.next();
		
		while((me = dal.getUserByUsernameAndPassword(username, password)) == null){
			System.out.println("User not found, please try again.");
			System.out.print("Username:");
			username = sc.next();
			System.out.print("Password:");
			password = sc.next();
		}
		
		System.out.println("Logged in as: " + me.Name);
		
		menu = new MenuManager(me);
		menu.begin();
		
		//sc.close();
	}
}
