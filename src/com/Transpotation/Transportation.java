package com.Transpotation;

import com.Common.DB.AlreadyExistsException;
import com.Common.DB.DB;
import com.Common.DB.IDBHandler;
import com.Common.DB.UsedException;
import com.Common.ISupplierStorage;
import com.Common.ITransportation;
import com.Common.Models.Driver;
import com.Common.Models.LicenseType;
import com.Common.Models.Order;
import com.Common.UI.Editor;
import com.Common.UI.Table;
import com.SupplierStorage.BE.OrderProduct;
import com.SupplierStorage.SupplierStorage;
import com.Transpotation.Models.*;
import com.Workers.Workers;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

public class Transportation implements ITransportation {
    private ISupplierStorage supplierStorage;
    private DB db;
    private static Transportation instance;

    public static Transportation getInstance() throws InternalError{
        if(instance == null)
            instance = new Transportation();
        return instance;
    }

    public Transportation(){
        long l = 1467393615429L;
        Date d = new Date(l);


        try {
            db = DB.getInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initialize(){
        supplierStorage = SupplierStorage.getInstance();
        try{
            db = DB.getInstance();

            GregorianCalendar todayStart = new GregorianCalendar();
            todayStart.setTime(new Date());
            todayStart.set(Calendar.HOUR_OF_DAY,24);
            todayStart.set(Calendar.MINUTE,0);
            todayStart.set(Calendar.SECOND,0);

            GregorianCalendar todayEnd = new GregorianCalendar();
            todayEnd.setTime(todayStart.getTime());
            todayEnd.add(Calendar.DAY_OF_MONTH,1);

            List<com.Transpotation.Models.Transportation> todaysTransportations = db
                    .getTransportationIDBHandler()
                    .select("EndTime < ? AND arrived = 0",todayStart.getTime());

            for(com.Transpotation.Models.Transportation t : todaysTransportations){
                List<OrderDocument> orderDocuments = db.getOrderDocumentIDBHandler().select("transportation = ?",t.getID());
                ArrayList<OrderProduct> products = new ArrayList<>();
                for(OrderDocument d : orderDocuments){
                    products.addAll(supplierStorage.getOrder(d.getOrderID()+"").get_amountProduct());
                }
                supplierStorage.getSupply(products);
                for(OrderDocument d : orderDocuments){
                    supplierStorage.sentOrder(d.getOrderID()+"");
                }
                t.setArrived(true);
                db.getTransportationIDBHandler().update(t);
            }

        } catch (ClassNotFoundException e) {
            throw new InternalError(e.getMessage());
        }
    }

    @Override
    public void makeTransportation(Date time, ArrayList<Order> orders,boolean isWeekly) throws
            NoTrucksAvailable, InternalError {
        try {
            Truck truck = null;
            Driver driver = null;
            List<Truck> trucks = db.getTruckDBHandler().all();

            for(Truck t : trucks){
                List<Driver> drivers = Workers.getInstance().availableDrivers(t.getLicenseType(),time, isWeekly);
                if(drivers.size() > 0){
                    driver = drivers.get(0);
                    truck = t;
                    break;
                }
            }

            if(truck == null || driver == null){
                LicenseType min = LicenseType.C;
                Driver minDriver = null;
                float weight = 0;

                List<Driver> drivers = Workers.getInstance().availableDrivers(LicenseType.A,time,isWeekly);
                for(Driver d : drivers)
                    if(min.isBigger(d.getDriverLicenseType())){
                        min = d.getDriverLicenseType();
                        minDriver = d;
                    }

                for(Order o : orders)
                    weight += o.get_weight();

                truck = selectMatchingTruck(min,weight);
                driver = minDriver;
            }

            Map<Area,List<OrderDocument>> map = new HashMap<>();
            for(Order o : orders){
                OrderDocument orderDocument = new OrderDocument();
                List<OrderDocument> l = map.get(o.getPlace().getShipmentArea());
                if(l == null){
                    l = new LinkedList<>();
                    map.put(o.getPlace().getShipmentArea(),l);
                }
                l.add(orderDocument);

                orderDocument.setSource(o.getPlace());
                orderDocument.setDestination(Place.MAIN_BRANCH);
                orderDocument.setOrderID(Integer.parseInt(o.getOrderID()));
            }

            for(Area a : map.keySet()){
                com.Transpotation.Models.Transportation transportation = new com.Transpotation.Models.Transportation();
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(time);
                c.add(Calendar.DAY_OF_MONTH,-1);

                transportation.setArrived(false);
                transportation.setEndTime(time);

                transportation.setStartTime(c.getTime());

                transportation.setDriver(driver);
                transportation.setTruck(truck);
                transportation.setArea(a);

                db.getTransportationIDBHandler().insert(transportation);
                transportation.setID((Integer) db.getTransportationIDBHandler().getLastGeneratedID());

                for(OrderDocument o : map.get(a)){
                    o.setTransportation(transportation);
                    db.getOrderDocumentIDBHandler().insert(o);
                }
            }
        } catch (ValidationException | ClassNotFoundException | SQLException e) {
            throw new InternalError(e.getMessage());
        }
    }

    private Truck selectMatchingTruck(LicenseType licenseType, double weight){
        System.out.println("We have not found any trucks matching the criteria for this transportation.");
        System.out.println("Truck must be of maximum licence type: " + licenseType);
        System.out.println("and must be able to carry at least: " + weight);
        System.out.println("Please create or edit a matching truck and then select it:");

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

        Truck truck = null;
        try {

            while(truck == null){
                table.display();
                truck = table.select();

                if(!licenseType.isBigger(truck.getLicenseType())){
                    System.out.println("Truck must be of maximum licence type: " + licenseType);
                    truck = null;
                }

                if((truck.getMaxWeight() - truck.getNetWeight()) < weight){
                    System.out.println("Truck must be able to carry at least: " + weight);
                    truck = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return truck;
    }

    @Override
    public List<Place> getAllPlaces() {
        return db.getPlaceDBHandler().all();
    }

    @Override
    public List<Area> getAllAreas() {
        return db.getAreaDBHandler().all();
    }

    @Override
    public void addSupplierPlace(Place place) {
        db.getPlaceDBHandler().insert(place);
    }

    public class NoTrucksAvailable extends Exception {
    }

    public class NoDriversAvailable extends RuntimeException {
    }

    public class InternalError extends RuntimeException {
        public InternalError(String message) {
            super(message);
        }
    }
}
