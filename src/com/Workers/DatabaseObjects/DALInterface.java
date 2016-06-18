package com.Workers.DatabaseObjects;

import com.Common.Models.Driver;
import com.Common.Models.LicenseType;
import com.Workers.Objects.*;

import java.util.LinkedList;

public interface DALInterface {


    /**
     * ---WorkerData---
     **/
    //Add:
    boolean addWorker(String ID, String bankNo, Date employmentDate, String name);

    boolean addUser(String ID, String username, String password);

    boolean addJobsByID(String ID, LinkedList<Worker.JobEnum> jobs);

    boolean addManagerByID(String WID, LinkedList<Shift> shifts);

    //Update:
    boolean updateBankNOByID(String ID, String bankNO);

    boolean updateEmploymentDateByID(String ID, Date employmentDate);

    boolean updateEmploymentTermsByID(String ID, String employmentTerms);

    boolean updateNameByID(String ID, String name);

    boolean updateUsernameByID(String ID, String username);

    boolean updatePasswordByID(String ID, String password);

    //GetInformation:
    User getUserByUsernameAndPassword(String username, String password);

    User getUserByID(String ID);

    Worker getWorkerByID(String ID);

    LinkedList<Worker.JobEnum> getJobsByID(String ID);

    //Delete:
    boolean deleteWorkerByID(String ID);

    boolean deleteUserByUsernameAndPassword(String username, String password);

    boolean deleteUserByID(String ID);


    /**
     * ---Schedule Data---
     **/
    //Add:
    boolean addWorkerScheduleByID(String ID, LinkedList<WorkerSchedule> listTimes);

    //GetInformation:
    LinkedList<WorkerSchedule> getWorkerScheduleByID(String ID);

    //Delete:
    boolean deleteWorkerScheduleByID(String ID, LinkedList<WorkerSchedule> listTimes);

    /**
     * ---ShiftsData---
     **/
    //Add:
    boolean addShiftsToWorkerBYid(String ID, LinkedList<Shift> listShifts);

    //GetInformation:
    LinkedList<Shift> getShiftsByManagerID(String MID);

    LinkedList<Shift> getShiftsByDateAndType(Date date, Shift.TypeEnum type);

    LinkedList<Shift> getShiftsByWorkerID(String ID);

    boolean updateWorker(Worker worker);

    LinkedList<Worker> getWorkersByShift(Shift shift);

    //Delete:
    boolean deleteShiftsByWorkerID(String ID, LinkedList<Shift> s);


    /**
     * is exist
     **/
    boolean doesUsernameExist(String username);

    boolean updateUser(User user);

    LinkedList<Worker> getAllUsers();

    /*Assignment 2*/
    public Driver getDrivetByID(String ID);

    public boolean addDriver(Driver d);

    public boolean addDriverLicenceByID(String ID, LicenseType licenseType);

    public boolean deleteDriverByID(String ID);

    public LinkedList<String> getWorkerIDByJobEnum(Worker.JobEnum je);

	/*historyOfShifts*/
    public boolean deleteShiftsFromHistory(String ID,LinkedList<Shift> slst);

    public boolean  addWeeklyShift(String ID, String Day, WorkerSchedule.TypeEnum type ,Worker.JobEnum job);

    public LinkedList<String> getWeeklyShifts(String ID);
}
