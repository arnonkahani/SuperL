package com.Transpotation;

import com.Common.DB.DB;
import com.Common.ISupplierStorage;
import com.Common.ITransportation;
import com.Common.Models.Driver;
import com.Common.Models.Order;
import com.SupplierStorage.BE.OrderProduct;
import com.Transpotation.Models.*;
import com.Workers.Workers;

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
        try{
            db = DB.getInstance();

            GregorianCalendar todayStart = new GregorianCalendar();
            todayStart.setTime(new Date());
            todayStart.set(Calendar.HOUR_OF_DAY,0);
            todayStart.set(Calendar.MINUTE,0);
            todayStart.set(Calendar.SECOND,0);

            GregorianCalendar todayEnd = new GregorianCalendar();
            todayEnd.setTime(todayStart.getTime());
            todayEnd.add(Calendar.DAY_OF_MONTH,1);

            List<com.Transpotation.Models.Transportation> todaysTransportations = db
                    .getTransportationIDBHandler()
                    .select("? < EndTime AND EndTime < ? AND arrived = 0",todayStart.getTime().getTime(),todayEnd.getTime().getTime());

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
    public void makeTransportation(Date time, ArrayList<Order> orders) throws
            NoTrucksAvailable, InternalError {
        try {
            Truck truck = null;
            Driver driver = null;
            List<Truck> trucks = db.getTruckDBHandler().all();

            if(trucks.size() == 0){
                throw new NoTrucksAvailable();
            }

            for(Truck t : trucks){
                List<Driver> drivers = Workers.getInstance().availableDrivers(t.getLicenseType(),time);
                if(drivers.size() > 0){
                    driver = drivers.get(0);
                    truck = t;
                    break;
                }
            }

            if(driver == null)
                throw new NoDriversAvailable();

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
                orderDocument.setDestination(o.getPlace());
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
