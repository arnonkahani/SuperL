package com.Transpotation.UI;

import java.util.Objects;
import java.util.Scanner;

public class LoginScreen {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    public void login(){
        Scanner scanner = new Scanner(System.in);

        String username;
        String password;

        System.out.println("Please login to continue:");
        while(true){
            System.out.print("Username:");
            username = scanner.nextLine();
            System.out.print("Password:");
            password = scanner.nextLine();
            if(!Objects.equals(username, USERNAME) || !Objects.equals(password, PASSWORD))
                System.out.println("Incorrect username or password. Please try again");
            else return;
        }
    }
}
