package com.Transpotation.UI;

import com.Common.DB.DB;
import com.Common.UI.Table;
import com.Common.IWorkers;
import com.Common.Models.Driver;
import com.Common.UI.Menu;
import com.Transpotation.Models.OrderDocument;
import com.Workers.Workers;
import com.Transpotation.Models.Transportation;
import com.Transpotation.Models.Truck;
import com.Transpotation.Models.ValidationException;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class TransportationOptionsMenu extends Menu {
    private static final String AVAILABLE_TRUCKS =
            "SELECT Truck.* FROM Truck LEFT JOIN Transportation " +
            "ON Transportation.truck = Truck.LicenceNumber " +
            "WHERE Transportation.EndTime IS NULL";

    DB db;
    IWorkers iWorkers;
    List<Transportation> list;
    Transportation transportation;
    Scanner scanner = new Scanner(System.in);
    public TransportationOptionsMenu(DB db, IWorkers iWorkers, List<Transportation> list, Transportation transportation) {
        this.db = db;
        this.iWorkers = iWorkers;
        this.list = list;
        this.transportation = transportation;
    }

    @Override
    protected List<Pair<String, MenuItem>> getMenu() {
        List<Pair<String, MenuItem>> menu = new LinkedList<>();

        //menu.add(new Pair<>("Attach all Orders from Area", this::attachOrdersFromArea));
        menu.add(new Pair<>("View Attached Orders", this::manageAttachedOrders));
        //menu.add(new Pair<>("Remove All Attached Orders", this::removeAll));
        menu.add(new Pair<>("Attach Truck", this::attachTruck));
        menu.add(new Pair<>("Attach Driver", this::attachDriver));
        return menu;
    }

    private static final String REMOVE_ALL_QUERY = "UPDATE OrderDocument SET transportation = NULL WHERE transportation = ?";
    private void removeAll(Integer integer, String s) throws SQLException {
        System.out.print("Are you sure you want to remove all Orders attached ? y/N:");
        String str = scanner.nextLine();
        if(Objects.equals(str, "y") || Objects.equals(str, "Y"))
            db.getOrderDocumentIDBHandler().executeUpdate(REMOVE_ALL_QUERY,transportation.getID());
    }

    private static final String AVAILABLE_ORDERS_FROM_AREA_QUERY = "SELECT * FROM OrderDocument,Place WHERE transportation IS NULL AND OrderDocument.Source = Place.Address AND Place.ShipmentArea = ?";
    private void attachOrdersFromArea(Integer integer, String s) throws Exception {
        List<OrderDocument> documents = db.getOrderDocumentIDBHandler().query(AVAILABLE_ORDERS_FROM_AREA_QUERY,transportation.getArea().getAreaID());
        for(OrderDocument document: documents){
            try{
                document.setTransportation(transportation);
                db.getOrderDocumentIDBHandler().update(document);
                System.out.println(String.format("Attached %s to transportation",document.toString()));
            }
            catch (ValidationException e){
                System.out.println(String.format("Could not add any more Orders because %s",e.getMessage()));
                break;
            }
        }
    }

    private void manageAttachedOrders(Integer integer, String s) throws Exception {
        List<OrderDocument> list = db.getOrderDocumentIDBHandler().select("transportation = ?",transportation.getID());
        Table<OrderDocument> table = new Table<>(OrderDocument.class,list);
        table.setOptionsAction((i,t)->{
            new OrderDocumentOptionsMenu(t);
        });
        /*table.setNewAction(()->{
            System.out.println("Please select an order to attach: <PRESS ENTER TO BEGIN>");
            scanner.nextLine();
            OrderDocument order = new Table<>(OrderDocument.class, db.getOrderDocumentIDBHandler().query(AVAILABLE_ORDERS_FROM_AREA_QUERY,transportation.getArea().getAreaID())).select();
            if(order != null){
                order.setTransportation(transportation);
                db.getOrderDocumentIDBHandler().update(order);
            }
            list.clear();
            list.addAll(db.getOrderDocumentIDBHandler().select("transportation = ?",transportation.getID()));
        });
        table.setDeleteAction((i,o)->{
            o.setTransportation(null);
            db.getOrderDocumentIDBHandler().update(o);
            list.remove(o);
        });*/
        table.display();
    }

    private void attachTruck(Integer integer, String s) throws Exception {
        List<Truck> list = db.getTruckDBHandler().query(AVAILABLE_TRUCKS);
        Truck t = new Table<>(Truck.class,list).select();
        if(t != null){
            try {
                transportation.setTruck(t);
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                System.out.println("<< Selection failed >>");
            }
            db.getTransportationIDBHandler().update(transportation);
        }
    }

    private void attachDriver(Integer integer, String s) throws Exception {
        List<Driver> list = Workers.getInstance().availableDrivers(null,new java.util.Date(), false);
        Driver t = new Table<>(Driver.class,list).select();
        if(t != null){
            try {
                transportation.setDriver(t);
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
                System.out.println("<< Selection failed >>");
            }
            db.getTransportationIDBHandler().update(transportation);
        }
    }
}
