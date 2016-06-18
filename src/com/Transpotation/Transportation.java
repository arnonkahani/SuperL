package com.Transpotation;

import com.Common.DB.DB;
import com.Common.ISupplierStorage;
import com.Common.ITransportation;
import com.Common.Models.Driver;
import com.Common.Models.Order;
import com.SupplierStorage.BE.OrderProduct;
import com.SupplierStorage.BE.SupplierProduct;
import com.SupplierStorage.BE.SupplyAgreementProduct;
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
            todayStart.setTime(new Date());
            todayStart.set(Calendar.HOUR_OF_DAY,24);
            todayStart.set(Calendar.MINUTE,0);
            todayStart.set(Calendar.SECOND,0);

            List<com.Transpotation.Models.Transportation> todaysTransportations = db
                    .getTransportationIDBHandler()
                    .select("? < EndTime AND EndTime < ? AND arrived = 0",todayStart.getTime(),todayEnd.getTime());

            for(com.Transpotation.Models.Transportation t : todaysTransportations){
                List<OrderDocument> orderDocuments = db.getOrderDocumentIDBHandler().select("transportation = ?",t.getID());
                ArrayList<OrderProduct> products = new ArrayList<>();
                for(OrderDocument d : orderDocuments){
                    products.addAll(supplierStorage.getOrder(d.getOrderID()+"").get_amountProduct());
                }
                supplierStorage.getSupply(products);

                t.setArrived(true);
                db.getTransportationIDBHandler().update(t);
            }

        } catch (ClassNotFoundException e) {
            throw new InternalError(e.getMessage());
        }

    }

    @Override
    public void makeTransportation(Date time, ArrayList<Order> orders) throws
            NoTrucksAvailable, NoDriversAvailable,InternalError {
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
                orderDocument.setDestination(null);
                orderDocument.setOrderID(Integer.parseInt(o.getOrderID()));

                for(OrderProduct op : o.get_amountProduct()){
                    Product p = new Product();
                    p.setWeight(op.get_weight());
                    p.setOrder(orderDocument);
                    p.setName(op.get_name());
                    db.getProductIDBHandler().insert(p);
                }
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

                for(OrderDocument o : map.get(a)){
                    o.setTransportation(transportation);
                    db.getOrderDocumentIDBHandler().insert(o);
                }

                db.getTransportationIDBHandler().insert(transportation);
            }
        } catch (ValidationException | ClassNotFoundException | SQLException e) {
            throw new InternalError(e.getMessage());
        }
    }

    @Override
    public List<Place> getAllPlaces() {
        return db.getPlaceDBHandler().all();
    }

    public class NoTrucksAvailable extends Exception {
    }

    public class NoDriversAvailable extends Exception {
    }

    public class InternalError extends RuntimeException {
        public InternalError(String message) {
            super(message);
        }
    }
}
