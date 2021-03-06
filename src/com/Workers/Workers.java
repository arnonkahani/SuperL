package com.Workers;

import com.Common.Models.LicenseType;
import com.Common.IWorkers;
import com.Common.Models.Driver;
import com.Workers.DatabaseObjects.DAL;
import com.Workers.Objects.Date;
import com.Workers.Objects.Shift;
import com.Workers.Objects.Worker;
import com.Workers.Objects.WorkerSchedule;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;

public class Workers implements IWorkers {
    private static Workers instance;
    private static DAL dal;
    public static Workers getInstance(){

        if(instance == null)
            instance = new Workers();
        return instance;
    }
    final private String[] WeekDays = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday", "Saturday"};

    private Workers(){
        try {
            dal = DAL.getDAL();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Driver> availableDrivers(LicenseType type, java.util.Date time, boolean isWeekly) {
        if(!isWeekly) {
            List<Driver> lst = dal.availableDrivers(type, time);
            if (lst != null) {
                return lst;
            }
            System.out.println("There is no Driver available for this day");
            System.out.println("Please Enter a Driver ID for a driver who will be working in this shift");
            LinkedList<Worker> workers = dal.getAllUsers();
            System.out.println("ID        |Name            |Licence");
            System.out.println("-----------------------------------");
            for (Worker w : workers) {
                if (dal.getJobsByID(w.ID).contains(Worker.JobEnum.Driver)) {
                    System.out.print(w.getID() + " |");
                    System.out.print(w.getName().substring(0, Math.min(16, w.getName().length())) +
                            "                ".substring(0, Math.max(0, 16 - w.getName().length())) +
                            "|");
                    System.out.println(dal.getDrivetByID(w.getID()).getDriverLicenseType().toString());
                }
            }
            Scanner scan = new Scanner(System.in);
            String dID = scan.next();
            boolean done = false;
            while (!done) {
                while (dal.getDrivetByID(dID) == null) {
                    System.out.println("Error parsing answer, please try again");
                    dID = scan.next();
                }

                LinkedList<WorkerSchedule> schedule = dal.getWorkerScheduleByID(dID);
                for(WorkerSchedule ws : schedule) {
                    if(ws.get_day().compareTo(new Date(time).get_day()) != 0)
                        schedule.remove(ws);
                }

                if(schedule.isEmpty()) {
                    System.out.println("No schedule found for the driver");
                    System.out.println("Write \"add\" to add a schedule day or \"new\" to select another driver");

                    String s = scan.next();
                    while(s.compareTo("add") != 0 && s.compareTo("new") != 0) {
                        System.out.println("Error!");
                        System.out.println("Please type \"add\" or \"new\"");
                        s = scan.next();
                    }

                    if(s.compareTo("new") == 0)
                        break;

                    if(s.compareTo("add") == 0) {
                        try {
                        schedule.add(new WorkerSchedule(WeekDays[time.getDay()%7], WorkerSchedule.getTypeByTime(time)));
                        } catch (WorkerSchedule.NotValidDayException e) { }
                        dal.addWorkerScheduleByID(dID, schedule);
                    }
                }

                Shift s = null;
                try {
                    s = new Shift(dID, WorkerSchedule.getTypeByTime(time), new Date(time), Worker.JobEnum.Driver);
                } catch (WorkerSchedule.NotValidDayException e) {

                }
                LinkedList<Shift> shiftList = new LinkedList<>();
                shiftList.add(s);

                dal.addShiftsToWorkerBYid(dID, shiftList);

                lst = new LinkedList<>();
                lst.add(dal.getDrivetByID(dID));
                done = true;
            }
            return lst;
        }
        else {
            return dal.getWeeklyWorkers(WeekDays[time.getDay()%7]);
        }
    }

    @Override
    public boolean isStockWorkerAvailable(java.util.Date time) {
        boolean b = dal.isStockWorkerAvailable(time);
        if(b)
            return b;
        System.out.println("There is no Stock Worker available for this day");
        System.out.println("Please Enter a worker ID for a stock worker who will be working in this shift");
        LinkedList<Worker> workers = dal.getAllUsers();
        System.out.println("ID        |Name            ");
        System.out.println("---------------------------");
        for(Worker w : workers) {
            if(dal.getJobsByID(w.ID).contains(Worker.JobEnum.WarehouseWorker)) {
                System.out.print(w.getID() + " |");
                System.out.print(w.getName().substring(0,Math.min(16, w.getName().length())) +
                        "                ".substring(0,Math.max(0, 16-w.getName().length())));
            }
        }
        Scanner scan = new Scanner(System.in);
        String dID = scan.next();
        boolean done = false;
        while(!done) {
            while (dal.getWorkerByID(dID) == null && !dal.getJobsByID(dID).contains(Worker.JobEnum.WarehouseWorker)) {
                System.out.println("Error parsing answer, please try again");
                dID = scan.next();
            }

            LinkedList<WorkerSchedule> schedule = dal.getWorkerScheduleByID(dID);
            for(WorkerSchedule ws : schedule) {
                if(ws.get_day().compareTo(new Date(time).get_day()) != 0)
                    schedule.remove(ws);
            }

            if(schedule.isEmpty()) {
                System.out.println("No schedule found for the worker");
                System.out.println("Write \"add\" to add a schedule day or \"new\" to select another worker");

                String s = scan.next();
                while(s.compareTo("add") != 0 && s.compareTo("new") != 0) {
                    System.out.println("Error!");
                    System.out.println("Please type \"add\" or \"new\"");
                    s = scan.next();
                }

                if(s.compareTo("new") == 0)
                    break;

                if(s.compareTo("add") == 0) {
                    try {
                        schedule.add(new WorkerSchedule(WeekDays[time.getDay()%7], WorkerSchedule.getTypeByTime(time)));
                    } catch (WorkerSchedule.NotValidDayException e) { }
                    dal.addWorkerScheduleByID(dID, schedule);
                }
            }

            Shift s = null;
            try {
                s = new Shift(dID, WorkerSchedule.getTypeByTime(time), new Date(time), Worker.JobEnum.WarehouseWorker);
            } catch (WorkerSchedule.NotValidDayException e) {

            }
            LinkedList<Shift> shiftList = new LinkedList<>();
            shiftList.add(s);

            dal.addShiftsToWorkerBYid(dID, shiftList);
            done = true;
        }
        return true;
    }

    @Override
    public java.util.Date getEarliestDeleveryDate(java.util.Date time) {
        Date d = new Date(time);
        Date week = new Date(time);
        d.increase();
        week.increase();
        week.increaseByWeek();
        for(; !(d.equals(week)); d.increase()) {
            if(!availableDrivers(LicenseType.A ,d.get_date(), false).isEmpty())
                return d.get_date();
        }

        //no date...

        System.out.println("There is no driver available between " + (new Date(time)).toString() + " And " + week.toString());
        System.out.println("You'll need to add a shift for a driver!");

        boolean check = false;
        String ID;
        Scanner scan = new Scanner(System.in);
        while(!check) {
            System.out.println("Please Enter a driver ID from the following list");
            LinkedList<Worker> workers = dal.getAllUsers();
            System.out.println("ID        |Name            |Licence");
            System.out.println("-----------------------------------");
            for(Worker w : workers) {
                if(dal.getJobsByID(w.ID).contains(Worker.JobEnum.Driver)) {
                    System.out.print(w.getID() + " |");
                    System.out.print(w.getName().substring(0,Math.min(16, w.getName().length())) + "                ".substring(0,Math.max(0, 16-w.getName().length())) + "|");
                    System.out.println(dal.getDrivetByID(w.getID()).getDriverLicenseType().toString());
                }
            }
            ID = scan.next();
            if(dal.getDrivetByID(ID) == null) {
                System.out.println("A Driver with such an ID doesn't exist!, Please try again");
                break;
            }
            LinkedList<WorkerSchedule> schedule = dal.getWorkerScheduleByID(ID);
            for(WorkerSchedule ws: schedule) {
                if(ws.get_type() != WorkerSchedule.getTypeByTime(time))
                    schedule.remove(ws);
            }
            if(schedule.isEmpty()) {
                System.out.println("The Driver has no option in the schedule for this date");
                System.out.println("Would you like to add a a new day to the schedule or to choose a new driver?");
                System.out.println("Type \"add\" to add a new day or \"new\" to choose a new driver");
                String choice = scan.next();
                while(choice.compareTo("add") != 0 && choice.compareTo("new") != 0) {
                    System.out.println("Error parsing answer, please try again");
                    choice = scan.next();
                }
                if(choice.compareTo("new") == 0)
                    break;
                else {
                    System.out.println("Please enter a day of the week");
                    String day = scan.next();
                    boolean dayCheck = false;
                    while (!dayCheck) {
                        try {
                            WorkerSchedule.WeekDaysEnum.valueOf(day);
                            dayCheck = true;
                        } catch (Exception e) {
                            System.out.println("Error parsing answer, please try again");
                            day = scan.next();
                        }
                    }
                    WorkerSchedule newSchedule = null;
                    try {
                        newSchedule = new WorkerSchedule(day, WorkerSchedule.getTypeByTime(time));
                    } catch (WorkerSchedule.NotValidDayException e) {
                        e.printStackTrace();
                    }
                    LinkedList<WorkerSchedule> l = new LinkedList<>();
                    l.add(newSchedule);
                    dal.addWorkerScheduleByID(ID, l);

                    Calendar c = Calendar.getInstance();
                    c.setTime(time);
                    int dayNum = -1;
                    for(int i = 1; i <= 7; i++){
                        if(WeekDays[i-1].compareTo(day) == 0)
                            dayNum = i;
                    }

                    Shift newShift = null;
                    try {
                        newShift = new Shift(day, WorkerSchedule.getTypeByTime(time), new Date(c.getTime()), Worker.JobEnum.Driver);
                    } catch (WorkerSchedule.NotValidDayException e) {
                        e.printStackTrace();
                    }
                    LinkedList<Shift> s = new LinkedList<>();
                    s.add(newShift);
                    dal.addShiftsToWorkerBYid(ID, s);

                    while(c.get(Calendar.DAY_OF_WEEK) != dayNum)
                        c.add(Calendar.DATE, 1);

                    return c.getTime();
                }
            }
            else {
                Calendar c = Calendar.getInstance();
                c.setTime(time);
                for(int i = 1; i <= 7; i++){
                    for(WorkerSchedule ws: schedule) {
                        if(ws.get_day().compareTo(WeekDays[(c.get(Calendar.DAY_OF_WEEK))%7]) == 0) {
                            Shift newShift = null;
                            try {
                                newShift = new Shift(ws.get_day(), WorkerSchedule.getTypeByTime(time), new Date(c.getTime()), Worker.JobEnum.Driver);
                            } catch (WorkerSchedule.NotValidDayException e) {
                                e.printStackTrace();
                            }
                            LinkedList<Shift> s = new LinkedList<>();
                            s.add(newShift);
                            dal.addShiftsToWorkerBYid(ID, s);
                            return c.getTime();
                        }
                    }
                    c.add(Calendar.DATE, 1);
                }
            }
        }
        return new java.util.Date();
    }

    @Override
    public void setWeeklyDeleveryShifts(List<DayOfWeek> dayOfWeeks) {
        for(DayOfWeek day : dayOfWeeks) {
            boolean found = false;
            LinkedList<Worker> workerList =  dal.getAllUsers();
            for(Worker w : workerList) {
                w.setAbilities(dal.getJobsByID(w.ID));
                 if(!found && w.getAbilities().contains(Worker.JobEnum.Driver)) {
                    LinkedList<WorkerSchedule> ws = dal.getWorkerScheduleByID(w.ID);
                    for(WorkerSchedule s : ws) {
                        if(s.get_day() == day.name() && s.get_type() == WorkerSchedule.TypeEnum.Morning && !dal.getWeeklyShifts(w.ID).contains(day.name())) {
                            if(!found) {
                                dal.addWeeklyShift(w.ID, day.name(), WorkerSchedule.TypeEnum.Morning ,Worker.JobEnum.Driver);
                                found = true;
                            }
                        }
                    }
                }
            }
            if(!found) {
                System.out.println("There is no Driver available for this day");
                System.out.println("Please Enter a Driver ID for a driver who will be working in this shift");
                LinkedList<Worker> workers = dal.getAllUsers();
                System.out.println("ID        |Name            |Licence");
                System.out.println("-----------------------------------");
                for(Worker w : workers) {
                    if(dal.getJobsByID(w.ID).contains(Worker.JobEnum.Driver)) {
                        System.out.print(w.getID() + " |");
                        System.out.print(w.getName().substring(0,Math.min(16, w.getName().length())) + "                ".substring(0,Math.max(0, 16-w.getName().length())) + "|");
                        System.out.println(dal.getDrivetByID(w.getID()).getDriverLicenseType().toString());
                    }
                }
                Scanner scan = new Scanner(System.in);
                String dID = scan.next();
                boolean done = false;
                while(!done) {
                    while (dal.getDrivetByID(dID) == null) {
                        System.out.println("Error parsing answer, please try again");
                        dID = scan.next();
                    }
                    if (!dal.getWeeklyShifts(dID).contains(day.name())) {
                        dal.addWeeklyShift(dID, day.name(), WorkerSchedule.TypeEnum.Morning ,Worker.JobEnum.Driver);
                        done = true;
                    }
                }
            }
        }
    }

    public LinkedList<Worker.JobEnum> getJobs(String UserName, String Password) {
        com.Workers.Objects.User u = dal.getUserByUsernameAndPassword(UserName,Password);
        if(u == null)
            return null;
        return dal.getJobsByID(u.ID);
    }
    @Override
    public boolean isUser(String username, String password) {
        return (dal.getUserByUsernameAndPassword(username, password) != null);
    }
}
