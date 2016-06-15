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
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AddRemoveUpdateJunit {

    DAL dal = null;
    Worker worker = null;
    String id = "123456789";
    String bankNO = "123";
    String name = "roni";

    @Before
    public void before() {
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
    public void after() {
        assertEquals(true, dal.deleteWorkerByID(id));
    }


    @Test
    public void addShiftsToWorkerBYidTest() {
        LinkedList<Worker.JobEnum> jobEna = new LinkedList<Worker.JobEnum>();
        jobEna.add(Worker.JobEnum.Driver);
        assertEquals(true, dal.addJobsByID(id, jobEna));

        LinkedList<WorkerSchedule> listWorkerSchedules = new LinkedList<>();
        try {
            listWorkerSchedules.add(new WorkerSchedule("Sunday", WorkerSchedule.TypeEnum.Morning));
            listWorkerSchedules.add(new WorkerSchedule("Sunday", WorkerSchedule.TypeEnum.Evening));
        } catch (Exception e) {
            fail(e.getMessage());
        } catch (WorkerSchedule.NotValidDayException e) {
            fail(e.getMessage());
        }
        assertEquals(true, dal.addWorkerScheduleByID(id, listWorkerSchedules));

        LinkedList<Shift> listShifts = new LinkedList<>();
        try {
            listShifts.add(new Shift("Sunday", WorkerSchedule.TypeEnum.Morning, new Date(1, 2, 2016), Worker.JobEnum.Driver));
            listShifts.add(new Shift("Sunday", WorkerSchedule.TypeEnum.Evening, new Date(1, 2, 2016), Worker.JobEnum.Driver));
        } catch (Exception e) {
            fail(e.getMessage());
        } catch (WorkerSchedule.NotValidDayException e) {
            fail(e.getMessage());

        }
        assertEquals(true, dal.addShiftsToWorkerBYid(id, listShifts));
        assertNotEquals(null, dal.getShiftsByWorkerID(id));
        assertEquals(2, dal.getShiftsByWorkerID(id).size()); //cannot delete worker and after delete his shifts
        assertEquals(true, dal.deleteShiftsByWorkerID(id, listShifts));
        assertEquals(0, dal.getShiftsByWorkerID(id).size()); //cannot delete worker and after delete his shifts
        assertEquals(true, dal.deleteWorkerScheduleByID(id, listWorkerSchedules));
        assertEquals(true, dal.deleteShiftsFromHistory(id, listShifts));
    }

    @Test
    public void deleteWorkerScheduleByIDTest() {
        LinkedList<WorkerSchedule> lst = new LinkedList<>();
        try {
            lst.add(new WorkerSchedule("Sunday", WorkerSchedule.TypeEnum.Morning));
            lst.add(new WorkerSchedule("Sunday", WorkerSchedule.TypeEnum.Evening));
        } catch (Exception e) {
            fail(e.getMessage());
        } catch (WorkerSchedule.NotValidDayException e) {
            fail(e.getMessage());

        }
        assertEquals(true, dal.addWorkerScheduleByID(id, lst));
        assertEquals(2, dal.getWorkerScheduleByID(id).size());

        assertEquals(true, dal.deleteWorkerScheduleByID(id, lst));
        assertEquals(0, dal.getWorkerScheduleByID(id).size());
    }

    @Test
    public void addJobsByIDTest() {
        LinkedList<Worker.JobEnum> jobs = new LinkedList<>();
        jobs.add(Worker.JobEnum.ShiftManager);
        assertEquals(true, dal.addJobsByID(id, jobs));
        assertNotEquals(null, dal.getJobsByID(id));
        assertEquals(1, dal.getJobsByID(id).size());

    }

    @Test
    public void getWorkerScheduleByIDTest() {
        LinkedList<WorkerSchedule> lst = new LinkedList<>();
        try {
            lst.add(new WorkerSchedule("Sunday", WorkerSchedule.TypeEnum.Morning));
        } catch (Exception e) {
            fail(e.getMessage());
        } catch (WorkerSchedule.NotValidDayException e) {
            fail(e.getMessage());

        }
        assertEquals(true, dal.addWorkerScheduleByID(id, lst));
        assertNotEquals(null, dal.getWorkerScheduleByID(id));
        assertEquals(1, dal.getWorkerScheduleByID(id).size());

    }

    @Test
    public void deleteUserByUsernameAndPasswordTest() {
        assertEquals(true, dal.addUser(id, "B", "1"));
        assertEquals(true, dal.isUserExistByWorkerID(id));
        assertEquals(true, dal.deleteUserByUsernameAndPassword("B", "1"));
        assertEquals(false, dal.isUserExistByWorkerID(id));
    }


    @Test
    public void addUserTest() {
        assertEquals(true, dal.addUser(id, "1", "1"));
    }

    @Test
    public void updateBankNOByIDTest() {
        String bno = "000";
        assertEquals(true, dal.updateBankNOByID(id, bno));
        assertEquals("000", dal.getWorkerByID(id).getBankNO());
    }

    @Test
    public void updateEmploymentTermsTest() {
        String terms = "fegsd";
        assertEquals(true, dal.updateEmploymentTermsByID(id, terms));
        assertEquals("fegsd", dal.getWorkerByID(id).getEmpTerms());
    }

    @Test
    public void updateUsernameTest() {
        String name = "yoav";
        assertEquals(true, dal.addUser(id, "a","a"));
        assertEquals(true, dal.updateUsernameByID(id, name));
        assertEquals(name, dal.getUserByID(id).getUserName());
    }



}
