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
        iTransportation = Transportation.getInstance();
        iSupplierStorage = new SupplierStorage();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Super-Lee Managment System.");
        System.out.println("To continue, please login.");
        System.out.print("Username:");
        String username = sc.next();
        System.out.print("Password:");
        String password = sc.next();
        LinkedList<Worker.JobEnum> jobs = iWorkers.getJobs(username, password);
        if(jobs != null) {
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
