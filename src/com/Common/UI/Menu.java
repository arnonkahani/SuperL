package com.Common.UI;

import javafx.util.Pair;

import java.util.List;
import java.util.Scanner;

/**
 * an abstract class that shows a menu using getMenu() asks the user for a choice
 * then executes the corresponding callback
 */
public abstract class Menu {

    static final String format = "%d) %s";
    static Scanner scanner = new Scanner(System.in);

    public interface MenuItem{
        void accept(Integer i, String val) throws Exception;
    }

    public Menu() {
    }

    public void show() throws Exception {
        int choice = -2;
        while(choice != -1){
            List<Pair<String,MenuItem>> menu = getMenu();
            System.out.println(String.format(format,0,"<- back"));
            for(int i = 0 ; i < menu.size() ; i++){
                System.out.println(String.format(format,i+1,menu.get(i).getKey()));
            }
            System.out.print("Enter your choice: ");
            try{
                choice = Integer.parseInt(scanner.nextLine()) - 1;
                if(choice == -1)
                    return;
                if(choice >= menu.size() || choice < 0){
                    System.out.println("(Invalid choice)");
                    choice = -1;
                }
                menu.get(choice).getValue().accept(choice,menu.get(choice).getKey());
            }
            catch (NumberFormatException e){
                System.out.println("(Invalid choice)");
            }
        }

    }

    protected abstract List<Pair<String,MenuItem>> getMenu();
}
