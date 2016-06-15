package com.Workers.JunitTests;

import com.Workers.DatabaseObjects.DAL;
import com.Workers.Objects.Date;
import com.Workers.Objects.Shift;
import com.Workers.Objects.Worker;
import com.Workers.Objects.WorkerSchedule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Barakmen on 5/6/2016.
 */
public class IworkersMethodsJunit {
    DAL dal = null;
    Worker worker = null;
    String id = "123456789";
    String bankNO = "123";
    String name = "roni";

    @Before
    public void before(){
        try {
            dal = DAL.getDAL();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Date d;
        try {
            d = new Date(3, 1, 1999);
            assertEquals(true, dal.addWorker(id, bankNO, d, name));
        } catch (Exception e) {
            fail(e.getMessage());

        }
    }

    @After
    public void after(){
        assertEquals(true, dal.deleteWorkerByID(id));
    }

    @Test
    public void isStockWorkerAvailableTest(){


        LinkedList<Worker.JobEnum> jobEna = new LinkedList<Worker.JobEnum>();
        jobEna.add(Worker.JobEnum.WarehouseWorker);
        jobEna.add(Worker.JobEnum.Driver);
        assertEquals(true, dal.addJobsByID(id, jobEna));

        LinkedList<WorkerSchedule> lstws = new LinkedList<>();
        try {
            lstws.add(new WorkerSchedule("Sunday", WorkerSchedule.TypeEnum.Morning));
            lstws.add(new WorkerSchedule("Monday", WorkerSchedule.TypeEnum.Morning));
        } catch (WorkerSchedule.NotValidDayException e) {
            fail(e.getMessage());
        }
        assertEquals(true, dal.addWorkerScheduleByID(id,lstws));

        LinkedList<Shift> lsts = new LinkedList<>();
        try {
            lsts.add(new Shift("Sunday", WorkerSchedule.TypeEnum.Morning, new Date(4,3,1999), Worker.JobEnum.Driver));
            lsts.add(new Shift("Monday", WorkerSchedule.TypeEnum.Morning,new Date(4,3,1999), Worker.JobEnum.WarehouseWorker));
        } catch (WorkerSchedule.NotValidDayException e) {
            fail(e.getMessage());
        } catch (ParseException e) {
            fail(e.getMessage());
        }
        assertEquals(true, dal.addShiftsToWorkerBYid(id,lsts));

        String strdate2 = "04-03-1999 11:35";
        java.util.Date time;
        try {
            time = Date.dateWithTimef.parse(strdate2);
            assertEquals(true, dal.isStockWorkerAvailable(time));
        } catch (ParseException e) {
            fail();
        }
        assertEquals(true, dal.deleteShiftsByWorkerID(id,lsts));
        assertEquals(true, dal.deleteShiftsFromHistory(id, lsts));
    }


}
