package com.Workers.DatabaseObjects;

import com.Common.DB.DB;
import com.Common.Models.LicenseType;
import com.Common.DB.AlreadyExistsException;
import com.Common.DB.IDBHandler;
import com.Common.IWorkers;
import com.Common.Models.Driver;
import com.Workers.Objects.*;
import com.Workers.Objects.Date;
import com.Workers.Workers;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.util.*;

public class DAL extends DALInitiateConstants implements DALInterface {


    IDBHandler<Worker> workerIDBHandler;
    IDBHandler<User> userIDBHandler;
    IDBHandler<Driver> driverIDBHandler;

    private Connection connection = null;
    private static DAL dal = null;
    DB db;

    /* Singleton pattern */
    public static DAL getDAL() throws SQLException, ClassNotFoundException {
        if (dal == null) {
            dal = new DAL();
            return dal;
        } else {
            return dal;
        }
    }

    private DAL() throws SQLException, ClassNotFoundException {
        db = DB.getInstance();
        connection = db.getConnection();
        workerIDBHandler = db.getWorkerDBHandler();
        userIDBHandler = db.getUserDBHandler();
        driverIDBHandler = db.getDriverDBHandler();
        turnOnForignkeys();
        updateDBIfNeeded();

        if (getUserByUsernameAndPassword("Admin", "Admin") == null) { //<-------
            try {
                if (getUserByID("000000000") != null)
                    deleteUserByID("000000000");
                if (getWorkerByID("000000000") != null)
                    deleteWorkerByID("000000000");

                LinkedList<Worker.JobEnum> list = new LinkedList<>();
                list.add(Worker.JobEnum.HRManager);

                addWorker("000000000", "000000000000", new Date(1, 1, 2000), "Admin");
                updateEmploymentTermsByID("000000000", "Hard coded Admin");
                addJobsByID("000000000", list);
                addUser("000000000", "Admin", "Admin");
            } catch (Exception e) {
                System.out.println("Critical Error!!!");
                return;
            }
        }
    }

    private void updateDBIfNeeded() throws SQLException {
        for (String create : ALL_CREATE_QUARIES) {
            connection.createStatement().execute(create);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private void turnOnForignkeys() throws SQLException {
        String query = "PRAGMA foreign_keys = ON;";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.execute();

    }

    @Override
    public boolean addWorker(String ID, String bankNo, Date employmentDate, String name) {
        try {
            workerIDBHandler.insert(new Worker(ID, name, bankNo, null, employmentDate.toString(), null));
            return true;
        } catch (AlreadyExistsException e) {
            System.out.println(e);
            return false;
        }

    }

    @Override
    public boolean addUser(String ID, String username, String password) {
        try {
            userIDBHandler.insert(new User(this.getWorkerByID(ID), username, password));
            return true;
        } catch (AlreadyExistsException e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean addJobsByID(String ID, LinkedList<Worker.JobEnum> jobs) {
        String[] attributes = {"WID", "Ability"};
        String[][] object_values = new String[jobs.size()][attributes.length];
        int numOfCurrentJob = 0;
        for (Worker.JobEnum je : jobs) {
            object_values[numOfCurrentJob][0] = ID;
            object_values[numOfCurrentJob][1] = je.name();
            numOfCurrentJob++;
        }
        return GeneralSQLiteQuaries.insertListInto("WorkerAbilities", attributes, object_values);
    }

    @Override
    public boolean addManagerByID(String WID, LinkedList<Shift> shifts) {
        String[] attributes = {"MID", "Day", "Type", "Date", "Ability"};
        String[][] object_values = new String[shifts.size()][attributes.length];
        int numOfCurrent = 0;
        for (Shift sh : shifts) {
            object_values[numOfCurrent][0] = WID;
            object_values[numOfCurrent][1] = sh.get_day();
            object_values[numOfCurrent][2] = sh.get_type().name();
            object_values[numOfCurrent][3] = sh.get_date().toString();
            object_values[numOfCurrent][4] = Worker.JobEnum.ShiftManager.toString();
            numOfCurrent++;
        }
        return GeneralSQLiteQuaries.insertListInto(TABLE_ShiftManager, attributes, object_values);
    }

    @Override
    public boolean updateBankNOByID(String ID, String bankNO) {
        String table = "Worker";
        String[] attributesToSet = {"BankNO"};
        String[] valuesOfAtribuesToSet = {bankNO};
        String[] attributesToWhere = {"ID"};
        String[] valuesOfAtribuesToWhere = {ID};
        try {
            GeneralSQLiteQuaries.updateTable(table, attributesToSet, valuesOfAtribuesToSet, attributesToWhere, valuesOfAtribuesToWhere);
            return true;
        } catch (SQLException e) {
            System.out.println("cannot update bankNO");
            return false;
        }

    }

    @Override
    public boolean updateEmploymentDateByID(String ID, Date employmentDate) {
        String table = "Worker";
        String[] attributesToSet = {"EmploymentDate"};
        String[] valuesOfAtribuesToSet = {employmentDate.toString()};
        String[] attributesToWhere = {"ID"};
        String[] valuesOfAtribuesToWhere = {ID};
        try {
            GeneralSQLiteQuaries.updateTable(table, attributesToSet, valuesOfAtribuesToSet, attributesToWhere, valuesOfAtribuesToWhere);
            return true;
        } catch (SQLException e) {
            System.out.println("cannot update Employment Date");
            return false;
        }
    }

    @Override
    public boolean updateEmploymentTermsByID(String ID, String employmentTerms) {
        String table = "Worker";
        String[] attributesToSet = {"EmploymentTerms"};
        String[] valuesOfAtribuesToSet = {employmentTerms};
        String[] attributesToWhere = {"ID"};
        String[] valuesOfAtribuesToWhere = {ID};
        try {
            GeneralSQLiteQuaries.updateTable(table, attributesToSet, valuesOfAtribuesToSet, attributesToWhere, valuesOfAtribuesToWhere);
            return true;
        } catch (SQLException e) {
            System.out.println("cannot update Employment Terms");
            return false;
        }
    }

    @Override
    public boolean updateNameByID(String ID, String name) {
        String table = "Worker";
        String[] attributesToSet = {"Name"};
        String[] valuesOfAtribuesToSet = {name};
        String[] attributesToWhere = {"ID"};
        String[] valuesOfAtribuesToWhere = {ID};
        try {
            GeneralSQLiteQuaries.updateTable(table, attributesToSet, valuesOfAtribuesToSet, attributesToWhere, valuesOfAtribuesToWhere);
            return true;
        } catch (SQLException e) {
            System.out.println("cannot update name");
            return false;
        }
    }

    @Override
    public boolean updateUsernameByID(String ID, String username) {
        String table = TABLE_User;
        String[] attributesToSet = {"Username"};
        String[] valuesOfAtribuesToSet = {username};
        String[] attributesToWhere = {"WID"};
        String[] valuesOfAtribuesToWhere = {ID};
        try {
            GeneralSQLiteQuaries.updateTable(table, attributesToSet, valuesOfAtribuesToSet, attributesToWhere, valuesOfAtribuesToWhere);
            return true;
        } catch (SQLException e) {
            System.out.println("cannot update username");
            return false;
        }
    }

    @Override
    public boolean updatePasswordByID(String ID, String password) {
        String table = TABLE_User;
        String[] attributesToSet = {"Password"};
        String[] valuesOfAtribuesToSet = {password};
        String[] attributesToWhere = {"WID"};
        String[] valuesOfAtribuesToWhere = {ID};
        try {
            GeneralSQLiteQuaries.updateTable(table, attributesToSet, valuesOfAtribuesToSet, attributesToWhere, valuesOfAtribuesToWhere);
            return true;
        } catch (SQLException e) {
            System.out.println("cannot update password");
            return false;
        }
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        try {
            ResultSet rs = GeneralSQLiteQuaries.selectFrom(
                    TABLE_User,
                    new String[]{"WID", "Username", "Password"},
                    new String[]{"Username", "Password"},
                    new String[]{username, password}
            );
            String ID = rs.getString("WID");
            User user = new User(getWorkerByID(ID), rs.getString("Username"), rs.getString("Password"));
            return user;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Worker getWorkerByID(String ID) {
        try {
            ResultSet rs = GeneralSQLiteQuaries.selectFrom(
                    TABLE_Worker,
                    new String[]{"BankNO", "EmploymentDate", "EmploymentTerms", "Name"},
                    new String[]{"ID"},
                    new String[]{ID}
            );
            String BankNO = rs.getString(1);
            String EmploymentDate = rs.getString(2);
            String EmploymentTerms = rs.getString(3);
            String name = rs.getString(4);

            Worker worker = new Worker(ID, name, BankNO, EmploymentTerms, EmploymentDate, getJobsByID(ID)); // TODO: fill the worker class and
            // give it the req info id pass
            // username
            return worker;

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public User getUserByID(String ID) {
        return userIDBHandler.get(ID);
    }

    @Override
    public LinkedList<Worker.JobEnum> getJobsByID(String ID) {
        LinkedList<Worker.JobEnum> lst = new LinkedList<Worker.JobEnum>();
        try {
            String query = "SELECT Ability FROM WorkerAbilities WHERE WID = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            // set the corresponding param
            pst.setString(1, ID);
            // execute the delete statement
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(Worker.JobEnum.valueOf(rs.getString(1)));
            }

            return lst;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean deleteWorkerByID(String ID) {
        String[] attributes = {"ID"};
        String[] valuesOfAtribues = {ID};
        try {
            GeneralSQLiteQuaries.deleteFrom("Worker", attributes, valuesOfAtribues);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean deleteUserByUsernameAndPassword(String username, String password) {
        String[] attributes = {"Username", "Password"};
        String[] valuesOfAtribues = {username, password};
        try {
            GeneralSQLiteQuaries.deleteFrom(TABLE_User, attributes, valuesOfAtribues);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteUserByID(String ID) {
        String[] attributes = {"WID"};
        String[] valuesOfAtribues = {ID};
        try {
            GeneralSQLiteQuaries.deleteFrom(TABLE_User, attributes, valuesOfAtribues);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean addWorkerScheduleByID(String ID, LinkedList<WorkerSchedule> listTimes) {
        String[] attributes = {"WID", "Day", "Type"};
        String[][] object_values = new String[listTimes.size()][attributes.length];
        int numOfCurrentWs = 0;
        for (WorkerSchedule ws : listTimes) {
            object_values[numOfCurrentWs][0] = ID;
            object_values[numOfCurrentWs][1] = ws.get_day();
            object_values[numOfCurrentWs][2] = ws.get_type().name();
            numOfCurrentWs++;
        }
        return GeneralSQLiteQuaries.insertListInto("WorkerSchedule", attributes, object_values);
    }

    @Override
    public LinkedList<WorkerSchedule> getWorkerScheduleByID(String ID) {
        LinkedList<WorkerSchedule> lst = new LinkedList<>();
        try {
            ResultSet rs = GeneralSQLiteQuaries.selectFrom(
                    TABLE_WorkerSchedule,
                    new String[]{"Day", "Type"},
                    new String[]{"WID"},
                    new String[]{ID}
            );

            while (rs.next()) {
                WorkerSchedule.TypeEnum te = rs.getString(2).equals("Morning") ? WorkerSchedule.TypeEnum.Morning
                        : WorkerSchedule.TypeEnum.Evening;

                lst.add(new WorkerSchedule(rs.getString(1), te));
            }
            return lst;
        } catch (SQLException e) {
            return null;
        } catch (WorkerSchedule.NotValidDayException e) {
            return null;
        }
    }

    @Override
    public boolean deleteWorkerScheduleByID(String ID, LinkedList<WorkerSchedule> listTimes) {
        String[] attributes = {"WID", "Day", "Type"};
        String[][] object_values = new String[listTimes.size()][attributes.length];
        int numOfCurrentWs = 0;
        for (WorkerSchedule ws : listTimes) {
            object_values[numOfCurrentWs][0] = ID;
            object_values[numOfCurrentWs][1] = ws.get_day();
            object_values[numOfCurrentWs][2] = ws.get_type().name();

            numOfCurrentWs++;
        }
        return GeneralSQLiteQuaries.deleteListFrom("WorkerSchedule", attributes, object_values);

    }

    @Override
    public boolean addShiftsToWorkerBYid(String ID, LinkedList<Shift> listShifts) {
        LinkedList<WorkerSchedule> workerScheduleLst = this.getWorkerScheduleByID(ID);
        boolean isAllOk = true;
        boolean isOneOk = false;

        for (Shift shift : listShifts) {
            if(getWeeklyShifts(ID).contains(shift.get_day()))
                return false;

            for (WorkerSchedule workerSchedule : workerScheduleLst) {
                if (workerSchedule.equals(shift)) {
                    isOneOk = true;
                    break;
                }
            }
            if (!isOneOk) {
                isAllOk = false;
                break;
            }
            isOneOk = false;
        }

        LinkedList<Worker.JobEnum> jes = dal.getJobsByID(ID);
        for(Shift sh : listShifts){
            if(!jes.contains(sh.getJob()))
                return false;
        }

        if (isAllOk) {
            String[] attributes = {"WID", "Day", "Type", "Date", "Ability"};
            String[][] object_values = new String[listShifts.size()][attributes.length];
            int numOfCurrentsh = 0;
            for (Shift sh : listShifts) {
                object_values[numOfCurrentsh][0] = ID;
                object_values[numOfCurrentsh][1] = sh.get_day();
                object_values[numOfCurrentsh][2] = sh.get_type().name();
                object_values[numOfCurrentsh][3] = sh.get_date().toString();
                object_values[numOfCurrentsh][4] = sh.getJob().name();

                numOfCurrentsh++;
            }
            return GeneralSQLiteQuaries.insertListInto(TABLE_WorkerInShifts, attributes, object_values);
        } else {
            return false;
        }
    }

    @Override
    public LinkedList<Shift> getShiftsByManagerID(String MID) {
        LinkedList<Shift> lst = new LinkedList<>();
        try {
            ResultSet rs = GeneralSQLiteQuaries.selectFrom(
                    TABLE_ShiftManager,
                    new String[]{"Day", "Type", "Date", "Ability"},
                    new String[]{"MID"},
                    new String[]{MID}
            );
            while (rs.next()) {
                WorkerSchedule.TypeEnum te = rs.getString("Type").equals("Morning") ? WorkerSchedule.TypeEnum.Morning
                        : WorkerSchedule.TypeEnum.Evening;

                Date date = new Date(rs.getString("Date"));
                Worker.JobEnum jEnum = Worker.JobEnum.ShiftManager;
                lst.add(new Shift(rs.getString("Day"), te, date, jEnum));
            }

            return lst;

        } catch (SQLException e) {
            return null;
        } catch (WorkerSchedule.NotValidDayException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public LinkedList<Shift> getShiftsByDateAndType(Date date, WorkerSchedule.TypeEnum type) {
        LinkedList<Shift> lst = new LinkedList<>();
        try {
            ResultSet rs = GeneralSQLiteQuaries.selectFrom(
                    TABLE_WorkerInShifts,
                    new String[]{"Day", "Type", "Date"},
                    new String[]{"Date", "Type"},
                    new String[]{date.toString(), type.name()}
            );
            while (rs.next()) {

                WorkerSchedule.TypeEnum te = rs.getString("Type").equals("Morning") ? WorkerSchedule.TypeEnum.Morning
                        : WorkerSchedule.TypeEnum.Evening;

                lst.add(new Shift(rs.getString("Day"), te, date, Worker.JobEnum.Driver));
            }

            return lst;

        } catch (SQLException e) {
            return null;
        } catch (WorkerSchedule.NotValidDayException e) {
            return null;
        }
    }

    @Override
    public LinkedList<Shift> getShiftsByWorkerID(String ID) {
        LinkedList<Shift> lst = new LinkedList<>();
        try {
            ResultSet rs = GeneralSQLiteQuaries.selectFrom(
                    TABLE_WorkerInShifts,
                    new String[]{"Day", "Type", "Date", "Ability"},
                    new String[]{"WID"},
                    new String[]{ID}
            );
            while (rs.next()) {

                WorkerSchedule.TypeEnum te = rs.getString("Type").equals("Morning") ? WorkerSchedule.TypeEnum.Morning
                        : WorkerSchedule.TypeEnum.Evening;

                Date date = new Date(rs.getString("Date"));
                Worker.JobEnum jEnum = Worker.JobEnum.valueOf(rs.getString("Ability"));
                lst.add(new Shift(rs.getString("Day"), te, date, jEnum));
            }

            rs = GeneralSQLiteQuaries.selectFrom(
                    TABLE_ShiftManager,
                    new String[]{"Day", "Type", "Date", "Ability"},
                    new String[]{"MID"},
                    new String[]{ID}
            );
            while (rs.next()) {
                WorkerSchedule.TypeEnum te = rs.getString("Type").equals("Morning") ? WorkerSchedule.TypeEnum.Morning
                        : WorkerSchedule.TypeEnum.Evening;

                Date date = new Date(rs.getString("Date"));
                Worker.JobEnum jEnum = Worker.JobEnum.valueOf(rs.getString("Ability"));
                lst.add(new Shift(rs.getString("Day"), te, date, jEnum));
            }

            return lst;

        } catch (SQLException e) {
            return null;
        } catch (WorkerSchedule.NotValidDayException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public boolean deleteShiftsByWorkerID(String ID, LinkedList<Shift> s) {
        try {
            for (Shift shift : s) {
                String table1 = TABLE_WorkerInShifts;
                String table2 = TABLE_ShiftManager;
                String[] attributes1 = {"WID", "Day", "Type", "Date"};
                String[] attributes2 = {"MID", "Day", "Type", "Date"};
                String[] valuesOfAtribues = {ID, shift.get_day(), shift.get_type().toString(), shift.get_date().toString()};
                GeneralSQLiteQuaries.deleteFrom(table1, attributes1, valuesOfAtribues);
                GeneralSQLiteQuaries.deleteFrom(table2, attributes2, valuesOfAtribues);
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }


    /* ForTests */
    public boolean isWorkerExistByID(String ID) {
        return GeneralSQLiteQuaries.isExists(
                TABLE_Worker,
                new String[]{"ID"},
                ID
        );
    }

    public boolean isWorkerAbilitiesExistsByID(String ID) {
        return GeneralSQLiteQuaries.isExists(
                TABLE_WorkerAbilities,
                new String[]{"WID"},
                ID
        );

    }

    public boolean isWorkerSchedulesExistsByID(String ID) {
        return GeneralSQLiteQuaries.isExists(
                TABLE_WorkerSchedule,
                new String[]{"WID"},
                ID
        );
    }

    public boolean isUserExistByWorkerID(String ID) {
        return GeneralSQLiteQuaries.isExists(
                TABLE_User,
                new String[]{"WID"},
                ID
        );
    }

    @Override
    public boolean doesUsernameExist(String username) {
        return GeneralSQLiteQuaries.isExists(
                TABLE_User,
                new String[]{"Username"},
                username
        );
    }

    @Override
    public boolean updateUser(User user) {
        boolean check = true;
        check = check && updateBankNOByID(user.ID, user.BankNO);
        check = check && updateNameByID(user.ID, user.Name);
        try {
            check = check && updateEmploymentDateByID(user.ID, new Date(user.EmpDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        check = check && updateEmploymentTermsByID(user.ID, user.EmpTerms);
        check = check && updateBankNOByID(user.ID, user.BankNO);
        check = check && updateUsernameByID(user.ID, user.userName);
        check = check && updatePasswordByID(user.ID, user.password);

        return check;
    }

    @Override
    public boolean updateWorker(Worker worker) {
        boolean check = true;
        check = check && updateBankNOByID(worker.ID, worker.BankNO);
        check = check && updateNameByID(worker.ID, worker.Name);
        try {
            check = check && updateEmploymentDateByID(worker.ID, new Date(worker.EmpDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        check = check && updateEmploymentTermsByID(worker.ID, worker.EmpTerms);
        check = check && updateBankNOByID(worker.ID, worker.BankNO);

        return check;
    }

    @Override
    public LinkedList<Worker> getWorkersByShift(Shift shift) {
        LinkedList<Worker> workers = new LinkedList<>();
        LinkedList<String> Wids = new LinkedList<>();
        try {
            String query = "SELECT WID FROM " + TABLE_WorkerInShifts + " WHERE Day = ? AND Type = ? AND Date = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            // set the corresponding param
            pst.setString(1, shift.get_day());
            pst.setString(2, shift.get_type().name());
            pst.setString(3, shift.get_date().toString());

            // execute the delete statement
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Wids.add(rs.getString(1));
            }

        } catch (Exception e) {
            return null;
        }

        try {
            String query = "SELECT MID FROM " + TABLE_ShiftManager + " WHERE Day = ? AND Type = ? AND Date = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            // set the corresponding param
            pst.setString(1, shift.get_day());
            pst.setString(2, shift.get_type().name());
            pst.setString(3, shift.get_date().toString());

            // execute the delete statement
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Wids.add(rs.getString(1));
            }

        } catch (Exception e) {
            return null;
        }


        for (String wid : Wids) {
            Worker w = this.getWorkerByID(wid);
            if (w != null)
                workers.add(w);
        }


        return workers;


    }


    @Override
    public LinkedList<Worker> getAllUsers() {
        LinkedList<Worker> lst = new LinkedList<>();
        try {
            String query = "SELECT * FROM Worker";
            PreparedStatement pst = connection.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                lst.add(new Worker(rs.getString(1), rs.getString(5), rs.getString(2), rs.getString(4), rs.getString(3), null));
            }

            return lst;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public Driver getDrivetByID(String ID) {
        return driverIDBHandler.get(ID);

    }

    @Override
    public boolean addDriver(Driver d) {
        try {
            LinkedList<Worker.JobEnum> jobs = this.getJobsByID(d.getID());
            if (jobs != null)
                if (jobs.contains(Worker.JobEnum.Driver)) {
                    DB.getInstance().getDriverDBHandler().insert(d);
                    return true;
                }
            return false;
        } catch (AlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean addDriverLicenceByID(String ID, LicenseType licenseType) {
        Worker w = this.getWorkerByID(ID);
        Driver d = new Driver(w, licenseType);
        try {
            DB.getInstance().getDriverDBHandler().insert(d);
            return true;
        } catch (AlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteDriverByID(String ID) {
        try {
            DB.getInstance().getDriverDBHandler().delete(ID);
            return true;
        } catch (AlreadyExistsException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public LinkedList<String> getWorkerIDByJobEnum(Worker.JobEnum je) {
        LinkedList<String> wids = new LinkedList<>();
        try {
            ResultSet set = GeneralSQLiteQuaries.selectFrom(
                    TABLE_WorkerAbilities,
                    new String[]{"WID"},
                    new String[]{"Ability"},
                    new String[]{je.name()}
            );
            while (set.next()) {
                wids.add(set.getString("WID"));
            }
            return wids;
        } catch (SQLException e) {
            return null;
        }
    }



    public List<Driver> availableDrivers(LicenseType type, java.util.Date time) {
        LinkedList<Driver> drivers = new LinkedList<>();
        WorkerSchedule.TypeEnum te = WorkerSchedule.getTypeByTime(time);
        Shift relevant = null;
        try {
            Date date = new Date(time);
            relevant = new Shift(date.get_dayInWeek(), te, date, Worker.JobEnum.Driver);
        } catch (WorkerSchedule.NotValidDayException e) {
            System.out.println(e.getMessage());
            return null;
        }

        LinkedList<String> dlst= dal.getWorkerIDByJobEnum(Worker.JobEnum.Driver);
        for(String wid : dlst){
            LinkedList<Shift> shiftsByWorkerID = dal.getShiftsByWorkerID(wid);
            for(Shift sh : shiftsByWorkerID){
                if(sh.equals(relevant)) {
                    Driver d = dal.getDrivetByID(wid);
                    if(d != null)
                        if(d.isLicenseBiggerThan(type)) {
                            drivers.add(dal.getDrivetByID(wid));
                        }
                }
            }
        }

        return drivers;
    }


    public boolean isStockWorkerAvailable(java.util.Date time) {
        WorkerSchedule.TypeEnum te = WorkerSchedule.getTypeByTime(time);
        Shift relevant = null;
        try {
            Date date = new Date(time);
            relevant = new Shift(date.get_dayInWeek(), te, date, Worker.JobEnum.WarehouseWorker);
        } catch (WorkerSchedule.NotValidDayException e) {
            e.printStackTrace();
        }
        LinkedList<String> dlst = this.getWorkerIDByJobEnum(Worker.JobEnum.WarehouseWorker);
        int i = 0;
        for (String wid : dlst) {
            LinkedList<Shift> shiftsByWorkerID = this.getShiftsByWorkerID(wid);
            for (Shift sh : shiftsByWorkerID) {
                if (sh.equals(relevant))
                    return true;
            }
        }
        return true;
    }


    /*historyOfShifts*/
    @Override
    public boolean deleteShiftsFromHistory(String ID, LinkedList<Shift> slst) {
        try {
            for (Shift shift : slst) {
                String table1 = TABLE_ShiftsHistory;
                String[] attributes1 = {"WID", "Day", "Type", "Date"};
                String[] valuesOfAtribues = {ID, shift.get_day(), shift.get_type().toString(), shift.get_date().toString()};
                GeneralSQLiteQuaries.deleteFrom(table1, attributes1, valuesOfAtribues);
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean addWeeklyShift(String ID, String Day, WorkerSchedule.TypeEnum type ,Worker.JobEnum job) {
        String[] attributes = {"WID", "Day", "Type", "Ability"};
        String[] object_values = new String[attributes.length];
        object_values[0] = ID;
        object_values[1] = Day;
        object_values[2] = type.name();
        object_values[3] = job.name();

        try {
            GeneralSQLiteQuaries.insertInto(TABLE_WeeklyShift, attributes, object_values);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public LinkedList<String> getWeeklyShifts(String ID) {
        LinkedList<String> lst = new LinkedList<>();
        try {
            String query = "SELECT Day FROM WeeklyShift WHERE WID = " + ID;
            PreparedStatement pst = connection.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(rs.getString(1));
            }

            return lst;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public LinkedList<Driver> getWeeklyWorkers(String day) {
        LinkedList<String> lst = new LinkedList<>();
        LinkedList<Driver> drivers = new LinkedList<>();
        try {
            String query = "SELECT WID FROM WeeklyShift WHERE Day = " + day;
            PreparedStatement pst = connection.prepareStatement(query);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lst.add(rs.getString(1));
            }

            for(String s : lst) {
                drivers.add(dal.getDrivetByID(s));
            }
            return drivers;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
