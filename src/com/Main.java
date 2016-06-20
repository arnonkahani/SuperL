package com;

import com.Common.DB.DB;
import com.Common.ISupplierStorage;
import com.Common.ITransportation;
import com.Common.IWorkers;
import com.Transpotation.Transportation;
import com.Transpotation.UI.LoginScreen;
import com.Transpotation.UI.MainMenu;
import com.Workers.Menus.LoginMenu;
import com.Workers.Objects.Worker;
import com.Workers.Workers;
import com.SupplierStorage.SupplierStorage;

import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    static DB db;
    static IWorkers iWorkers;
    static ITransportation iTransportation;
    static ISupplierStorage iSupplierStorage;
    public static void main(String[] args) throws Exception{
        db = DB.getInstance();
        iWorkers = Workers.getInstance();


        iWorkers.getEarliestDeleveryDate(new Date(2016,12,12));




        iTransportation = Transportation.getInstance();
        iSupplierStorage = SupplierStorage.getInstance();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Super-Lee Managment System.");
        System.out.println("To continue, please login.");
        System.out.print("Username:");
        String username = sc.next();
        System.out.print("Password:");
        String password = sc.next();
        if(username.compareTo("Super")==0&&password.compareTo("Super")==0) {


            boolean check = false;
            while(!check) {
                System.out.println("please enter the module:");
                System.out.println("1. Workers");
                System.out.println("2. Storage");
                System.out.println("3. Supplier");
                System.out.println("4. Transportation");
                System.out.println("5. Exit");
                String s = sc.next();
                switch (Integer.parseInt(s)) {
                    case 1:
                        LoginMenu.main("Admin", "Admin");
                        break;
                    case 2:
                        iSupplierStorage.showStorage();
                        break;
                    case 3:
                        iSupplierStorage.showSupplier();
                        break;
                    case 4:
                        DB db = DB.getInstance();
                        new MainMenu(db, iWorkers).show();
                        break;
                    case 5:
                        check = true;
                        break;
                    default:
                        System.out.println("Error!");
                        break;
                }
            }
        }
        else {
            LinkedList<Worker.JobEnum> jobs = iWorkers.getJobs(username, password);
            if (jobs != null) {
                if (jobs.contains(Worker.JobEnum.HRManager) || jobs.contains(Worker.JobEnum.ShiftManager)) {
                    LoginMenu.main(username, password);
                } else if (jobs.contains(Worker.JobEnum.StorageManager)) {
                    iSupplierStorage.showStorage();
                } else if (jobs.contains(Worker.JobEnum.SupplierManager)) {
                    iSupplierStorage.showSupplier();
                } else if (jobs.contains(Worker.JobEnum.TransportationManager)) {
                    DB db = DB.getInstance();
                    new MainMenu(db, iWorkers).show();
                }
            }
        }
    }
}
