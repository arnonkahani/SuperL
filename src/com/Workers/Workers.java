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
    public List<Driver> availableDrivers(LicenseType type, java.util.Date time) {
       return dal.availableDrivers(type, time);
    }

    @Override
    public boolean isStockWorkerAvailable(java.util.Date time) {
        return dal.isStockWorkerAvailable(time);
    }

    @Override
    public java.util.Date getEarliestDeleveryDate(java.util.Date time) {
        Date d;
        Date week = new Date(time);
        week.increaseByWeek();
        for(d = new Date(time); !(d.equals(week)); d.increase()) {
            if(!availableDrivers(LicenseType.A ,d.get_date()).isEmpty())//TODO: get a real type
                return d.get_date();
        }

        //no date...

        System.out.println("There is no driver available between " + (new Date(time)).toString() + " And " + week.toString());
        System.out.println("You'll need to add a shift for a driver!");

        boolean check = false;
        String ID;
        Scanner scan = new Scanner(System.in);
        while(!check) {
            System.out.print("Please Enter a driver ID: ");
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
                while(choice.compareTo("add") == 0 || choice.compareTo("new") == 0) {
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
                        if(WeekDays[i].compareTo(day) == 0)
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
                        if(ws.get_day().compareTo(WeekDays[(c.get(Calendar.DAY_OF_WEEK))]) == 0) {
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
        //TODO: ask for Morning/Evening
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
}
