package com.Common.DB;

import com.Common.Models.Driver;
import com.Transpotation.DB.*;
import com.Transpotation.Models.*;
import com.Workers.DatabaseObjects.DriverDBHandler;
import com.Workers.DatabaseObjects.UserDBHandler;
import com.Workers.DatabaseObjects.WorkerDBHandler;
import com.Workers.Objects.User;
import com.Workers.Objects.Worker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * main class used to interact with the database, used to get IDBHandler's for classes
 */
public class DB {
    private static DB instance;

    public static DB getInstance() throws ClassNotFoundException {
        if (instance == null)
            instance = new DB("SuperLi");
        return instance;
    }

    private Connection connection;

    private IDBHandler<Worker> workerIDBHandler;
    private IDBHandler<User> userIDBHandler;
    private IDBHandler<Driver> driverIDBHandler;

    private IDBHandler<Truck> truckDBHandler;
    private IDBHandler<Area> areaDBHandler;
    private IDBHandler<Place> placeDBHandler;
    private IDBHandler<OrderDocument> orderDocumentIDBHandler;
    private IDBHandler<Transportation> transportationIDBHandler;

    private DB(String databaseName) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName + ".db");

            workerIDBHandler = new WorkerDBHandler(connection);
            userIDBHandler = new UserDBHandler(connection, workerIDBHandler);
            driverIDBHandler = new DriverDBHandler(connection, workerIDBHandler);

            truckDBHandler = new TruckDBHandler(connection);
            areaDBHandler = new AreaDBHandler(connection);
            placeDBHandler = new PlaceDBHandler(connection, areaDBHandler);
            transportationIDBHandler = new TransportationDBHandler(connection, truckDBHandler, driverIDBHandler, areaDBHandler);
            orderDocumentIDBHandler = new OrderDocumentDBHandler(connection, placeDBHandler, transportationIDBHandler);
        }
        catch (SQLException e){
            throw new UnexpectedSQLException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public IDBHandler<User> getUserDBHandler() {
        return userIDBHandler;
    }

    public IDBHandler<Driver> getDriverDBHandler() {
        return driverIDBHandler;
    }

    public IDBHandler<Worker> getWorkerDBHandler() {
        return workerIDBHandler;
    }

    public IDBHandler<Truck> getTruckDBHandler() {
        return truckDBHandler;
    }

    public IDBHandler<Area> getAreaDBHandler() {
        return areaDBHandler;
    }

    public IDBHandler<Place> getPlaceDBHandler() {
        return placeDBHandler;
    }

    public IDBHandler<OrderDocument> getOrderDocumentIDBHandler() {
        return orderDocumentIDBHandler;
    }

    public IDBHandler<Transportation> getTransportationIDBHandler() {
        return transportationIDBHandler;
    }

    public void close() {
        try{
            connection.close();
        }
        catch (SQLException e){
            throw new UnexpectedSQLException(e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        connection.close();
        super.finalize();
    }
}
