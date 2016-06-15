package com.Workers;

import com.Common.Models.LicenseType;
import com.Common.IWorkers;
import com.Common.Models.Driver;
import com.Workers.DatabaseObjects.DAL;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Workers implements IWorkers {
    private static Workers instance;
    private static DAL dal;
    public static Workers getInstance(){

        if(instance == null)
            instance = new Workers();
        return instance;
    }

    public Workers(){
        try {
            dal = DAL.getDAL();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Driver> availableDrivers(LicenseType type, Date time) {
       return dal.availableDrivers(type, time);
    }

    @Override
    public boolean isStockWorkerAvailable(Date time) {
        return dal.isStockWorkerAvailable(time);
    }
}
