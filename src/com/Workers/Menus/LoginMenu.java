package com.Workers.Menus;

import com.Workers.DatabaseObjects.DAL;
import com.Workers.DatabaseObjects.DALInterface;
import com.Workers.Objects.User;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginMenu {
	public static void main(String username, String password) {
		User me;
		DALInterface dal = null;
		Scanner sc = new Scanner(System.in);
		MenuManager menu;

		me = dal.getUserByUsernameAndPassword(username, password);

		System.out.println("Logged in as: " + me.Name);
		
		menu = new MenuManager(me);
		menu.begin();
	}
}
