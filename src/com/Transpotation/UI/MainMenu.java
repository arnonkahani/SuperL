package com.Transpotation.UI;


import com.Common.UI.Table;
import com.Common.DB.AlreadyExistsException;
import com.Common.DB.DB;
import com.Common.DB.IDBHandler;
import com.Common.DB.UsedException;
import com.Common.IWorkers;
import com.Common.Models.Driver;
import com.Common.UI.Editor;
import com.Common.UI.Menu;
import com.Transpotation.Models.*;
import javafx.util.Pair;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MainMenu extends Menu {

    DB db;
    IWorkers iWorkers;
    private Scanner scanner = new Scanner(System.in);

    public MainMenu(DB db, IWorkers iWorkers) {
        this.db = db;
        this.iWorkers = iWorkers;
    }

    @Override
    protected List<Pair<String, MenuItem>> getMenu() {
        List<Pair<String, MenuItem>> menu = new LinkedList<>();

        menu.add(new Pair<>("Manage Future Transportations", this::manageFutureTransportations));
        menu.add(new Pair<>("View Current Transportations", this::viewCurrentTransportations));
        menu.add(new Pair<>("Manage Unattached Orders", this::manageOrders));
        menu.add(new Pair<>("Manage Trucks", this::manageTrucks));
        menu.add(new Pair<>("Manage Places & Areas", this::managePlacesAndAreas));
        menu.add(new Pair<>("Archive", this::archive));
        return menu;
    }

    @Override
    public void show() throws Exception {
        System.out.println("Welcome to SuperLi Transportation System™ !");
        System.out.println("Use the `back` option to return to the previous menu.");
        super.show();
    }

    private void manageFutureTransportations(Integer integer, String s) throws Exception {
        IDBHandler<Transportation> handler = db.getTransportationIDBHandler();
        List<Transportation> list = handler.select("StartTime > ?", new Date());

        Table<Transportation> table = new Table<>(Transportation.class,list);
        table.setDeleteAction((i,t)->{
            handler.delete(t.getID()+"");
            list.remove(t);
        });
        table.setNewAction(()->{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the following info to make a new Transportation:");
            Transportation t = new Editor<>(Transportation.class,new Transportation()).edit();

            System.out.println("Please select an area for this transportation: <PRESS ENTER TO BEGIN>");
            scanner.nextLine();
            Area area = new Table<>(Area.class, db.getAreaDBHandler().all()).select();
            if(area != null)
                t.setArea(area);
            else
                return;

            System.out.println("Please select a truck for this transportation: <PRESS ENTER TO BEGIN>");
            scanner.nextLine();
            Truck truck = new Table<>(Truck.class,db.getTruckDBHandler().all()).select();
            if(truck != null)
                t.setTruck(truck);
            else
                return;

            System.out.println("Please select a driver for this transportation: <PRESS ENTER TO BEGIN>");
            scanner.nextLine();
            Driver driver = new Table<>(Driver.class, iWorkers.availableDrivers(truck.getLicenseType(),t.getStartTime())).select();
            if(driver != null)
                t.setDriver(driver);
            else
                return;

            System.out.println("Please manage the orders for this transportation using the options menu");

            handler.insert(t);
            list.clear();
            list.addAll(handler.select("StartTime > ?" , new Date()));
        });
        table.setOptionsAction((i,t)->{
            new TransportationOptionsMenu(db,iWorkers,list,t).show();
        });
        table.display();
    }

    private void viewCurrentTransportations(Integer integer, String s) throws Exception {
        List<Transportation> l = db.getTransportationIDBHandler().select("StartTime < ? AND EndTime > ?",new Date(),new Date());
        new Table<>(Transportation.class,l).display();
    }

    private void manageOrders(Integer integer, String s) throws Exception {
        IDBHandler<OrderDocument> handler = db.getOrderDocumentIDBHandler();
        List<OrderDocument> list = handler.select("transportation IS NULL");

        Table<OrderDocument> table = new Table<>(OrderDocument.class,list);
        table.setDeleteAction((i,t)->{
            handler.delete(t.getID()+"");
            list.remove(t);
        });
        table.setNewAction(()->{
            System.out.println("Enter the following info to make a new Order:");
            OrderDocument t = new Editor<>(OrderDocument.class,new OrderDocument()).edit();

            System.out.println("Please select a source for this order: <PRESS ENTER TO BEGIN>");
            scanner.nextLine();
            Place source = new Table<>(Place.class, db.getPlaceDBHandler().all()).select();
            if(source != null)
                t.setSource(source);
            else
                return;

            System.out.println("Please select a destination for this order: <PRESS ENTER TO BEGIN>");
            scanner.nextLine();
            Place destination = new Table<>(Place.class, db.getPlaceDBHandler().all()).select();
            if(destination != null)
                t.setDestination(destination);
            else
                return;

            handler.insert(t);
            list.clear();
            list.addAll(handler.select("transportation IS NULL"));
        });
        table.setOptionsAction((i,t)->{
            new OrderOptionsMenu(db,list,t).show();
            list.clear();
            list.addAll(handler.select("transportation IS NULL"));
        });
        table.display();
    }

    private void manageTrucks(Integer integer, String s) throws Exception {
        IDBHandler<Truck> handler = db.getTruckDBHandler();
        List<Truck> list = handler.all();

        Table<Truck> table = new Table<>(Truck.class,list);
        table.setEditAction((i,t)-> {
            Truck truck = new Editor<>(Truck.class,t).edit();
            if(truck != null)
                handler.update(truck);
        });
        table.setDeleteAction((i,t)->{
            try{
                handler.delete(t.getLicenseNumber());
                list.remove(t);
            }
            catch (UsedException e){
                System.out.println("This place is used and referenced by another entity.");
                System.out.println("<<OPERATION CANCELED>>");
            }
        });
        table.setNewAction(()->{
            System.out.println("Enter the following info to make a new Truck:");
            Truck t = new Editor<>(Truck.class,new Truck()).edit();
            if(t != null){
                try{
                    handler.insert(t);
                    list.clear();
                    list.addAll(handler.all());
                }
                catch (AlreadyExistsException e){
                    System.out.println("A Truck with this LicenceNumber already exists.");
                    System.out.println("<<OPERATION CANCELED>>");
                }
            }
        });
        table.display();
    }

    private void managePlacesAndAreas(Integer integer, String s) throws Exception {
        new PlacesAndAreasMenu(db).show();
    }

    private void archive(Integer integer, String s) throws Exception {
        new ArchiveMenu(db).show();
    }
}
