package com.Workers.Menus;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;

import com.Common.Models.LicenseType;
import com.Workers.DatabaseObjects.DAL;
import com.Workers.Displayer.Displayer;
import com.Workers.Objects.CallBack;
import com.Workers.Objects.Date;
import com.Workers.Objects.Shift;
import com.Workers.Objects.User;
import com.Workers.Objects.Worker;
import com.Workers.Objects.WorkerSchedule;
import com.Workers.Objects.WorkerSchedule.TypeEnum;
import com.Workers.Objects.Worker.JobEnum;

public class MenuManager {
    public enum manuNames {
        MainManu,
        AddWorker,
        EditWorker,
        AddUser,
        EditUser,
        AddShift,
        DeleteShift,
        EditSchedule,
        SetShiftManager,
        SearchShifts,
        SeeShiftWorkers,
        SeeAllUsers,
        SeeAllShifts
    }

    private Menu currentMenu;
    private Displayer displayer;

    private User loggedIn;

    private HashMap<manuNames, Menu> menus;

    boolean shouldClose = false;

    private DAL dal;

    private Worker editeduser;

    public MenuManager(User user) {
        displayer = new Displayer();

        loggedIn = user;

        menus = new HashMap<>();

        try {
            dal = DAL.getDAL();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        init();
    }

    public void begin() {
        while (!shouldClose)
            displayMenu();
    }

    public void displayMenu() {
        displayer.DisplayText(currentMenu.GetMassage());
        if (currentMenu.isOption()) {
            String s = displayer.getInput();
            while (!shouldClose && !currentMenu.calcInput(s).call(this))
                s = displayer.getInput();
        } else
            currentMenu.calcInput("def").call(this);
    }

    public void displayText(String text) {
        displayer.DisplayText(text);
    }

    public String getInput() {
        return displayer.getInput();
    }

    public void setMenu(manuNames name) {
        if (menus.containsKey(name))
            currentMenu = menus.get(name);
        else
            currentMenu = new Menu(false, "CRITICAL ERROR!!!");
    }

    public void closeMenus() {
        shouldClose = true;
        displayer.closeDisplay();

    }

    public void init() {
        //if a human resource manager is logged in
        if (loggedIn.abilities.contains(Worker.JobEnum.HRManager)) {

            menus.put(manuNames.MainManu, getMainMenu());

            menus.put(manuNames.AddWorker, getAddWorker());

            menus.put(manuNames.EditWorker, getEditWorker());

            menus.put(manuNames.AddUser, getAddUser());

            menus.put(manuNames.EditUser, getEditUser());

            menus.put(manuNames.AddShift, getAddShift());

            menus.put(manuNames.DeleteShift, getDeleteShift());

            menus.put(manuNames.EditSchedule, getEditSchedule());

            menus.put(manuNames.SetShiftManager, getSetShiftManager());

            menus.put(manuNames.SearchShifts, getSearchShifts());

            menus.put(manuNames.SeeShiftWorkers, getSeeShiftWorkers());

            menus.put(manuNames.SeeAllUsers, getSeeAllUsers());

            menus.put(manuNames.SeeAllShifts, getSeeAllShifts());
        }


        //if a shift manager is logged in
        else if (loggedIn.abilities.contains(Worker.JobEnum.ShiftManager)) {
            menus.put(manuNames.MainManu, getMainMenu());
            menus.put(manuNames.SeeShiftWorkers, getSeeShiftWorkers());
        }

        currentMenu = menus.get(manuNames.MainManu);
    }

    public DAL getDAL() {
        return dal;
    }

    //Menu builders
    //done
    public Menu getMainMenu() {
        if (loggedIn.abilities.contains(Worker.JobEnum.HRManager)) {
            Menu temp = new Menu(true,
                    "Main Menu\n---------\n" +
                            "1) Add a new worker\n" +
                            "2) Edit an existing worker\n" +
                            "3) Add a new user\n" +
                            "4) Edit an existing user\n" +
                            "5) Add a shift to a worker\n" +
                            "6) Delete a shift from a worker\n" +
                            "7) Edit worker schedule\n" +
                            "8) Set or change a shift manager\n" +
                            "9) Watch shift history\n" +
                            "10) See all users\n" +
                            "11) See all shifts\n" +
                            "12) Exit\n");

            temp.addAction("1", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.AddWorker);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("2", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.EditWorker);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("3", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.AddUser);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("4", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.EditUser);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("5", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.AddShift);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("6", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.DeleteShift);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("7", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.EditSchedule);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("8", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.SetShiftManager);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("9", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.SearchShifts);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("10", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.SeeAllUsers);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("11", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.SeeAllShifts);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("12", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Goodbye!");
                    manager.closeMenus();
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            return temp;
        } else if (loggedIn.abilities.contains(Worker.JobEnum.ShiftManager)) {
            Menu temp = new Menu(true,
                    "Main Menu\n\n" +
                            "1) Watch workers in your shift\n" +
                            "2) Exit\n");

            temp.addAction("1", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.SeeShiftWorkers);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            temp.addAction("2", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Goodbye!");
                    manager.closeMenus();
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });
            return temp;
        } else
            return new Menu(true, "Error!!");
    }

    //done
    public Menu getAddWorker() {
        if (loggedIn.abilities.contains(Worker.JobEnum.HRManager)) {
            Menu temp = new Menu(false,
                    "Add Worker\n----------\nType \"back\" to return back to the Main Menu\n");

            temp.addAction("def", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter the workers ID:");
                    String ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    boolean isNumber = true;
                    for(int i  = 0; i < ID.length(); i++){
                        if(ID.charAt(i) < '0' || ID.charAt(i) > '9')
                            isNumber = false;
                    }
                    while(ID.length() != 9 || !isNumber ||ID.isEmpty() || manager.getDAL().getWorkerByID(ID) != null) {
                        if(!isNumber) {
                            manager.displayText("An ID must consist only numbers!, please try again\n");
                            ID = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }
                        else if (ID.length() != 9) {
                            manager.displayText("An ID must be 9 digits long!, please try again\n");
                            ID = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }
                        else if (ID.isEmpty()) {
                            manager.displayText("An ID cannot be empty!, please try again\n");
                            ID = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }

                        else if (manager.getDAL().getWorkerByID(ID) != null) {
                            manager.displayText("A worker with such an ID already exists!, please try again\n");
                            ID = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }

                        isNumber = true;
                        for(int i  = 0; i < ID.length(); i++){
                            if(ID.charAt(i) < '0' || ID.charAt(i) > '9')
                                isNumber = false;
                        }
                    }


                    manager.displayText("Please enter the workers name:");
                    String name = manager.getInput();
                    if (name.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }

                    while (name.isEmpty()) {
                        manager.displayText("A workers name cannot be empty!, please try again\n");
                        name = manager.getInput();
                        if (ID.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                    }

                    manager.displayText("Please enter the workers bank number:");
                    String bankNo = manager.getInput();
                    if (bankNo.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }

                    isNumber = true;
                    for(int i  = 0; i < bankNo.length(); i++){
                        if(bankNo.charAt(i) < '0' || bankNo.charAt(i) > '9')
                            isNumber = false;
                    }

                    while (bankNo.length() != 9 || bankNo.isEmpty() || !isNumber || dal.checkBankNo(bankNo)) {
                        if(bankNo.length() != 9) {
                            manager.displayText("A workers bank number must be 9 digits long!, please try again\n");
                            bankNo = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }
                        else if(!isNumber) {
                            manager.displayText("A workers bank number must consist only number!, please try again\n");
                            bankNo = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }
                        else if(bankNo.isEmpty()) {
                            manager.displayText("A workers bank number cannot be empty!, please try again\n");
                            bankNo = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }
                        else if(dal.checkBankNo(bankNo)) {
                            manager.displayText("A workers with such a bank number already exists!, please try again\n");
                            bankNo = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }

                        isNumber = true;
                        for(int i  = 0; i < bankNo.length(); i++){
                            if(bankNo.charAt(i) < '0' || bankNo.charAt(i) > '9')
                                isNumber = false;
                        }
                    }

                    manager.displayText("Please enter the workers employment terms (can be left blank):");
                    String empTerms = manager.getInput();
                    if (empTerms.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }

                    manager.displayText("Please enter the workers employment date in the format DD/MM/YYYY:");
                    String date = manager.getInput();
                    if (date.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    while (date.length() != 10 ||
                            date.charAt(2) != '/' || date.charAt(5) != '/' ||
                            Integer.parseInt(date.substring(0, 2)) < 0 || Integer.parseInt(date.substring(0, 2)) > 31 ||
                            Integer.parseInt(date.substring(3, 5)) < 0 || Integer.parseInt(date.substring(3, 5)) > 12 ||
                            Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 120) {
                        manager.displayText("Error regarding the Date!, please insert the date in DD/MM/YYYY format\n");
                        date = manager.getInput();
                        if (date.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                    }

                    Date employmentDate;
                    try {
                        employmentDate = new Date(Integer.parseInt(date.substring(0, 2)), Integer.parseInt(date.substring(3, 5)), Integer.parseInt(date.substring(6, 10)));
                    } catch (Exception e) {
                        employmentDate = null;
                        manager.displayText(e.toString());
                    }

                    LinkedList<JobEnum> abilities = new LinkedList<JobEnum>();
                    manager.displayText("Please enter the Jobs the worker can do (enter one at a time and finish with the word \"Done\"):\n");
                    manager.displayText("Possible jobs are: ");
                    boolean first = true;
                    for (Worker.JobEnum jobType : Worker.JobEnum.values()) {
                        if (first) {
                            manager.displayText(jobType.toString());
                            first = false;
                        } else
                            manager.displayText(", " + jobType.toString());
                    }
                    manager.displayText(".\n");
                    String job = manager.getInput();
                    if (job.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }

                    while (job.compareTo("Done") != 0) {
                        try {
                            abilities.add(Worker.JobEnum.valueOf(job));
                        } catch (Exception e) {
                            manager.displayText(job + "This job does not exist, Please try it again(no need to write the previouse ones again)\n");
                        }


                        job = manager.getInput();
                        if (job.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                    }

                    boolean toDrive = false;
                    LicenseType licenceType = null;

                    if(abilities.contains(JobEnum.Driver)){
                        manager.displayText("Please enter the licence type:");first = true;
                        for (LicenseType type : LicenseType.values()) {
                            if (first) {
                                manager.displayText(type.toString());
                                first = false;
                            } else
                                manager.displayText(", " + type.toString());
                        }
                        manager.displayText(".\n");
                        String licence = manager.getInput();
                        if (licence.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                        boolean licenceCheck = false;
                        while(!licenceCheck){
                            try {
                                licenceType = LicenseType.valueOf(licence);
                                licenceCheck = true;
                            } catch (Exception e) {
                                manager.displayText("Licence type is not valid, please try again.\n");
                                licence = manager.getInput();
                            }
                        }
                        toDrive = true;
                    }

                    boolean check = true;

                    check = check && dal.addWorker(ID, bankNo, employmentDate, name);
                    check = check && dal.updateEmploymentTermsByID(ID, empTerms);
                    if (!abilities.isEmpty())
                        check = check && dal.addJobsByID(ID, abilities);
                    if(toDrive)
                        check = check && dal.addDriverLicenceByID(ID,licenceType);



                    if (check) {
                        manager.displayText("Added the worker to the database!\nPress any key to return back to Main Manu");
                        manager.getInput();
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }

                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });

            return temp;
        } else
            return new Menu(false, "Error!!");
    }

    //done
    public Menu getEditWorker() {
        if (loggedIn.abilities.contains(Worker.JobEnum.HRManager)) {
            Menu temp = new Menu(true,
                    "Edit Worker\n-----------\nType \"back\" to return back to the Main Menu\n" +
                            "Please Type \"edit\" to edit a worker or \"delete\" to delete a worker\n");

            temp.addAction("delete", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Enter the worker ID:");
                    String ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    editeduser = manager.getDAL().getWorkerByID(ID);
                    while (editeduser == null) {
                        manager.displayText("A worker with such an ID doesn't exist!, please try again\n");
                        ID = manager.getInput();
                        if (ID.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                        editeduser = manager.getDAL().getWorkerByID(ID);
                    }

                    manager.displayText("Are you sure you want to delete the worker named " + editeduser.Name + " with the ID " + editeduser.ID + "? type \"yes\" or \"no\"\n");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0 || ans.compareTo("no") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    while (ans.compareTo("yes") != 0) {
                        manager.displayText("Please replay with \"yes\" or \"no\" only\n");
                        ans = manager.getInput();
                        if (ans.compareTo("back") == 0 || ans.compareTo("no") == 0) {

                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                    }

                    if (dal.deleteWorkerByID(editeduser.ID)) {
                        manager.displayText("Worker deleted from the database!\nPress any key to return back to Main Manu");
                        manager.getInput();
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }

                    manager.displayText("\n\n\n\n");

                    return true;
                }
            });

            temp.addAction("edit", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Enter the worker ID:");
                    String ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    editeduser = manager.getDAL().getWorkerByID(ID);
                    while (editeduser == null) {
                        manager.displayText("A worker with such an ID doesn't exist!, please try again\n");
                        ID = manager.getInput();
                        if (ID.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                        editeduser = manager.getDAL().getWorkerByID(ID);
                    }

                    manager.displayText("Worker\n------\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("back", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");

                    return true;
                }
            });

            temp.addAction("ID", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("You cannot change the ID!!!\nPlease choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\" or \"back\"\n");
                    manager.displayText("\n\n\n\n");

                    return false;
                }
            });

            temp.addAction("Name", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter a new name: ");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    } else {
                        editeduser.Name = ans;
                        if (dal.updateWorker(editeduser)) {
                            manager.displayText("Worker Updated!, Press any key to continue.\n");
                            manager.displayText("\n\n\n\n");
                        }
                    }

                    manager.displayText("Worker\n------\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("BankNo", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter a new bank number: ");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    } else {
                        editeduser.BankNO = ans;
                        if (dal.updateWorker(editeduser)) {
                            manager.displayText("Worker Updated!, Press any key to continue.\n");
                            manager.displayText("\n\n\n\n");
                        }
                    }

                    manager.displayText("Worker\n------\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("Employment Date", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter a new Date: ");
                    String date = manager.getInput();
                    if (date.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    while (date.length() != 10 ||
                            date.charAt(2) != '/' || date.charAt(5) != '/' ||
                            Integer.parseInt(date.substring(0, 2)) < 0 || Integer.parseInt(date.substring(0, 2)) > 31 ||
                            Integer.parseInt(date.substring(3, 5)) < 0 || Integer.parseInt(date.substring(3, 5)) > 12 ||
                            Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 120) {
                        manager.displayText("Error regarding the Date!, please insert the date in DD/MM/YYYY format\n");
                        date = manager.getInput();
                        if (date.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                    }


                    editeduser.EmpDate = date;
                    if (dal.updateWorker(editeduser)) {
                        manager.displayText("Worker Updated!, Press any key to continue.\n");
                        manager.displayText("\n\n\n\n");
                    }


                    manager.displayText("Worker\n------\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("Employment Terms", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter new employment terms: ");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    } else {
                        editeduser.EmpTerms = ans;
                        if (dal.updateWorker(editeduser)) {
                            manager.displayText("Worker Updated!, Press any key to continue.\n");
                            manager.displayText("\n\n\n\n");
                        }
                    }

                    manager.displayText("Worker\n------\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("Jobs", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("To add a job write \"add <Job Name>\" and to delete a job write \"delete <Job Name>\"\n");
                    manager.displayText("Possible jobs are:");
                    boolean first = true;
                    for(Worker.JobEnum j : Worker.JobEnum.values()){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");
                    String ans = manager.getInput();
                    LinkedList<Worker.JobEnum> jobListTemp = manager.getDAL().getJobsByID(editeduser.ID);
                    if (ans.compareTo("back") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    String[] jobAndWhat = ans.split(" ");
                    boolean ansCheck = ((jobAndWhat[0].compareTo("delete") == 0)||(jobAndWhat[0].compareTo("add") == 0));
                    try{
                        Worker.JobEnum.valueOf(jobAndWhat[1]);
                    } catch (IllegalArgumentException e){
                        ansCheck = false;
                    }

                    while (!ansCheck){
                        manager.displayText("Error while reading input, please try again.\n");
                        manager.displayText("To add a job write \"add <Job Name>\" and to delete a job write \"delete <Job Name>\"\n");
                        ans = manager.getInput();
                        if (ans.compareTo("back") == 0) {

                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                        jobAndWhat = ans.split(" ");
                        ansCheck = ((jobAndWhat[0].compareTo("delete") == 0)||(jobAndWhat[0].compareTo("add") == 0));
                        try{
                            Worker.JobEnum.valueOf(jobAndWhat[1]);
                        } catch (IllegalArgumentException e){
                            ansCheck = false;
                        }
                    }

                    if((jobAndWhat[0].compareTo("delete") == 0)){
                        for(Worker.JobEnum j : jobListTemp){
                            if(j.toString().compareTo(jobAndWhat[1]) == 0)
                                if(jobAndWhat[1].compareTo("Driver") == 0){
                                    if (dal.deleteJobByID(editeduser.ID , j) && dal.deleteDriverByID(editeduser.ID)) {
                                        manager.displayText("Worker Updated!, Press any key to continue.\n");
                                        manager.displayText("\n\n\n\n");
                                    }
                                }
                                else if (dal.deleteJobByID(editeduser.ID , j)) {
                                    manager.displayText("Worker Updated!, Press any key to continue.\n");
                                    manager.displayText("\n\n\n\n");
                                }
                        }
                    }
                    else if((jobAndWhat[0].compareTo("add") == 0)) {
                        jobListTemp.add(Worker.JobEnum.valueOf(jobAndWhat[1]));
                        LinkedList<Worker.JobEnum> addList = new LinkedList<JobEnum>();
                        addList.add(Worker.JobEnum.valueOf(jobAndWhat[1]));

                        LicenseType licenceType = null;
                        if(jobAndWhat[1].compareTo("Driver") == 0) {
                            manager.displayText("Please enter the licence type:");
                            first = true;
                            for (LicenseType type : LicenseType.values()) {
                                if (first) {
                                    manager.displayText(type.toString());
                                    first = false;
                                } else
                                    manager.displayText(", " + type.toString());
                            }
                            manager.displayText(".\n");
                            String licence = manager.getInput();
                            if (licence.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                            boolean licenceCheck = false;
                            while (!licenceCheck) {
                                try {
                                    licenceType = LicenseType.valueOf(licence);
                                    licenceCheck = true;
                                } catch (Exception e) {
                                    manager.displayText("Licence type is not valid, please try again.\n");
                                    licence = manager.getInput();
                                }
                            }
                            if (dal.addJobsByID(editeduser.ID, addList) && dal.addDriverLicenceByID(editeduser.ID, licenceType)) {
                                manager.displayText("Worker Updated!, Press any key to continue.\n");
                                manager.displayText("\n\n\n\n");
                            }
                        }
                        else if (dal.addJobsByID(editeduser.ID, addList)) {
                            manager.displayText("Worker Updated!, Press any key to continue.\n");
                            manager.displayText("\n\n\n\n");
                        }
                    }




                    manager.displayText("Worker\n------\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\" or \"back\"\n");
                    return false;
                }
            });

            return temp;
        } else
            return new Menu(false, "Error!!");
    }

    //done
    public Menu getAddUser() {
        if (loggedIn.abilities.contains(Worker.JobEnum.HRManager)) {
            Menu temp = new Menu(false,
                    "Add User\n--------\nType \"back\" to return back to the Main Menu\n");

            temp.addAction("def", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter the users ID:");
                    String ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    boolean isNumber = true;
                    for(int i  = 0; i < ID.length(); i++){
                        if(ID.charAt(i) < '0' || ID.charAt(i) > '9')
                            isNumber = false;
                    }
                    while(ID.length() != 9 || !isNumber ||ID.isEmpty() || manager.getDAL().getWorkerByID(ID) != null) {
                        if(!isNumber) {
                            manager.displayText("An ID must consist only numbers!, please try again\n");
                            ID = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }
                        else if (ID.length() != 9) {
                            manager.displayText("An ID must be 9 digits long!, please try again\n");
                            ID = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }
                        else if (ID.isEmpty()) {
                            manager.displayText("An ID cannot be empty!, please try again\n");
                            ID = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }

                        else if (manager.getDAL().getWorkerByID(ID) != null) {
                            manager.displayText("A worker with such an ID already exists!, please try again\n");
                            ID = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }

                        isNumber = true;
                        for(int i  = 0; i < ID.length(); i++){
                            if(ID.charAt(i) < '0' || ID.charAt(i) > '9')
                                isNumber = false;
                        }
                    }

                    manager.displayText("Please enter the users name:");
                    String name = manager.getInput();
                    if (name.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }

                    while (name.isEmpty()) {
                        manager.displayText("A users name cannot be empty!, please try again\n");
                        name = manager.getInput();
                        if (ID.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                    }

                    manager.displayText("Please enter the users bank number:");
                    String bankNo = manager.getInput();
                    if (bankNo.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }

                    while (bankNo.length() != 9 || bankNo.isEmpty() || !isNumber || dal.checkBankNo(bankNo)) {
                        if(bankNo.length() != 9) {
                            manager.displayText("A workers bank number must be 9 digits long!, please try again\n");
                            bankNo = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }
                        else if(!isNumber) {
                            manager.displayText("A workers bank number must consist only number!, please try again\n");
                            bankNo = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }
                        else if(bankNo.isEmpty()) {
                            manager.displayText("A workers bank number cannot be empty!, please try again\n");
                            bankNo = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }
                        else if(dal.checkBankNo(bankNo)) {
                            manager.displayText("A workers with such a bank number already exists!, please try again\n");
                            bankNo = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }

                        isNumber = true;
                        for(int i  = 0; i < bankNo.length(); i++){
                            if(bankNo.charAt(i) < '0' || bankNo.charAt(i) > '9')
                                isNumber = false;
                        }
                    }

                    manager.displayText("Please enter the users employment terms:");
                    String empTerms = manager.getInput();
                    if (empTerms.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }

                    manager.displayText("Please enter the users employment date in the format DD/MM/YYYY:");
                    String date = manager.getInput();
                    if (date.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    while (date.length() != 10 ||
                            date.charAt(2) != '/' || date.charAt(5) != '/' ||
                            Integer.parseInt(date.substring(0, 2)) < 0 || Integer.parseInt(date.substring(0, 2)) > 31 ||
                            Integer.parseInt(date.substring(3, 5)) < 0 || Integer.parseInt(date.substring(3, 5)) > 12 ||
                            Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 120) {
                        manager.displayText("Error regarding the Date!, please insert the date in DD/MM/YYYY format\n");
                        date = manager.getInput();
                        if (date.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                    }

                    Date employmentDate;
                    try {
                        employmentDate = new Date(Integer.parseInt(date.substring(0, 2)), Integer.parseInt(date.substring(3, 5)), Integer.parseInt(date.substring(6, 10)));
                    } catch (Exception e) {
                        employmentDate = null;
                        manager.displayText(e.toString());
                    }

                    LinkedList<JobEnum> abilities = new LinkedList<JobEnum>();
                    manager.displayText("Please enter the Jobs the user can do (enter one at a time and finish with the word \"Done\"):\n");
                    manager.displayText("Possible jobs are: ");
                    boolean first = true;
                    for (Worker.JobEnum jobType : Worker.JobEnum.values()) {
                        if (first) {
                            manager.displayText(jobType.toString());
                            first = false;
                        } else
                            manager.displayText(", " + jobType.toString());
                    }
                    manager.displayText(".\n");
                    String job = manager.getInput();
                    if (job.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    while (job.compareTo("Done") != 0) {
                        try {
                            abilities.add(Worker.JobEnum.valueOf(job));
                        } catch (Exception e) {
                            manager.displayText("This job does not exist, Please try it again(no need to write the previouse ones again)\n");
                        }


                        job = manager.getInput();
                        if (job.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                    }
                    boolean toDrive = false;
                    LicenseType licenceType = null;

                    if(abilities.contains(JobEnum.Driver)){
                        manager.displayText("Please enter the licence type:");first = true;
                        for (LicenseType type : LicenseType.values()) {
                            if (first) {
                                manager.displayText(type.toString());
                                first = false;
                            } else
                                manager.displayText(", " + type.toString());
                        }
                        manager.displayText(".\n");
                        String licence = manager.getInput();
                        if (licence.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                        boolean licenceCheck = false;
                        while(!licenceCheck){
                            try {
                                licenceType = LicenseType.valueOf(licence);
                                licenceCheck = true;
                            } catch (Exception e) {
                                manager.displayText("Licence type is not valid, please try again.\n");
                                licence = manager.getInput();
                            }
                        }
                        toDrive = true;
                    }

                    manager.displayText("Please enter the users username:");
                    String username = manager.getInput();
                    if (username.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    while(manager.getDAL().doesUsernameExist(username) || username.isEmpty()) {
                        if (manager.getDAL().doesUsernameExist(username)) {
                            manager.displayText("A user with such a username already exists!, please try again\n");
                            username = manager.getInput();
                            if (username.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        } else if (username.isEmpty()) {
                            manager.displayText("A users username cannot be empty!, please try again\n");
                            username = manager.getInput();
                            if (ID.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                        }
                    }


                    manager.displayText("Please enter the users password:");
                    String password = manager.getInput();
                    if (password.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }

                    while (password.isEmpty()) {
                        manager.displayText("A users password cannot be empty!, please try again\n");
                        password = manager.getInput();
                        if (ID.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                    }

                    boolean check = true;

                    check = check && dal.addWorker(ID, bankNo, employmentDate, name);
                    check = check && dal.updateEmploymentTermsByID(ID, empTerms);
                    if (!abilities.isEmpty())
                        check = check && dal.addJobsByID(ID, abilities);
                    if(toDrive)
                        check = check && dal.addDriverLicenceByID(ID,licenceType);

                    check = check && dal.addUser(ID, username, password);

                    if (check) {
                        manager.displayText("Added the user to the database!\nPress any key to return back to Main Manu");
                        manager.getInput();
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }

                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });

            return temp;
        } else
            return new Menu(false, "Error!!");
    }

    //done
    public Menu getEditUser() {
        if (loggedIn.abilities.contains(Worker.JobEnum.HRManager)) {
            Menu temp = new Menu(true,
                    "Edit User\n---------\nType \"back\" to return back to the Main Menu\n" +
                            "Please Type \"edit\" to edit a user or \"delete\" to delete a user\n");

            temp.addAction("delete", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Enter the user ID:");
                    String ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    editeduser = manager.getDAL().getUserByID(ID);
                    while (editeduser == null) {
                        manager.displayText("A user with such an ID doesn't exist!, please try again\n");
                        ID = manager.getInput();
                        if (ID.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                        editeduser = manager.getDAL().getUserByID(ID);
                    }

                    manager.displayText("Are you sure you want to delete the user named " + editeduser.Name + " with the ID " + editeduser.ID + "? type \"yes\" or \"no\"\n");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0 || ans.compareTo("no") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    while (ans.compareTo("yes") != 0) {
                        manager.displayText("Please replay with \"yes\" or \"no\" only\n");
                        ans = manager.getInput();
                        if (ans.compareTo("back") == 0 || ans.compareTo("no") == 0) {

                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                    }

                    if (dal.deleteUserByID(editeduser.ID)) {
                        manager.displayText("User deleted from the database!\nPress any key to return back to Main Manu");
                        manager.getInput();
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }

                    manager.displayText("\n\n\n\n");

                    return true;
                }
            });

            temp.addAction("edit", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Enter the user ID:");
                    String ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    editeduser = manager.getDAL().getUserByID(ID);
                    while (editeduser == null) {
                        manager.displayText("A user with such an ID doesn't exist!, please try again\n");
                        ID = manager.getInput();
                        if (ID.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                        editeduser = manager.getDAL().getUserByID(ID);
                    }

                    manager.displayText("User\n----\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");
                    manager.displayText("Username: " + ((User) editeduser).userName + "\n" +
                            "Password: " + ((User) editeduser).password + "\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\", \"Username\", \"Password\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("back", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");

                    return true;
                }
            });

            temp.addAction("ID", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("You cannot change the ID!!!\nPlease choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Username\", \"Password\" or \"back\"\n");
                    manager.displayText("\n\n\n\n");

                    return false;
                }
            });

            temp.addAction("Name", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter a new name: ");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    } else {
                        editeduser.Name = ans;
                        if (dal.updateUser((User) editeduser)) {
                            manager.displayText("User Updated!, Press any key to continue.\n");
                            manager.displayText("\n\n\n\n");
                        }
                    }

                    manager.displayText("User\n----\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");
                    manager.displayText(
                            "Username: " + ((User) editeduser).userName + "\n" +
                                    "Password: " + ((User) editeduser).password + "\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\", \"Username\", \"Password\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("BankNo", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter a new bank number: ");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    } else {
                        editeduser.BankNO = ans;
                        if (dal.updateWorker(editeduser)) {
                            manager.displayText("User Updated!, Press any key to continue.\n");
                            manager.displayText("\n\n\n\n");
                        }
                    }

                    manager.displayText("User\n----\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");
                    manager.displayText("Username: " + ((User) editeduser).userName + "\n" +
                            "Password: " + ((User) editeduser).password + "\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\", \"Username\", \"Password\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("Employment Date", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter a new Date: ");
                    String date = manager.getInput();
                    if (date.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    while (date.length() != 10 ||
                            date.charAt(2) != '/' || date.charAt(5) != '/' ||
                            Integer.parseInt(date.substring(0, 2)) < 0 || Integer.parseInt(date.substring(0, 2)) > 31 ||
                            Integer.parseInt(date.substring(3, 5)) < 0 || Integer.parseInt(date.substring(3, 5)) > 12 ||
                            Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 120) {
                        manager.displayText("Error regarding the Date!, please insert the date in DD/MM/YYYY format\n");
                        date = manager.getInput();
                        if (date.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                    }


                    if (dal.updateUser((User) editeduser)) {
                        manager.displayText("Worker Updated!, Press any key to continue.\n");
                        manager.displayText("\n\n\n\n");
                    }


                    manager.displayText("User\n----\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");
                    manager.displayText("Username: " + ((User) editeduser).userName + "\n" +
                            "Password: " + ((User) editeduser).password + "\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\", \"Username\", \"Password\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("Employment Terms", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter new employment terms: ");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    } else {
                        editeduser.EmpTerms = ans;
                        if (dal.updateUser((User) editeduser)) {
                            manager.displayText("Worker Updated!, Press any key to continue.\n");
                            manager.displayText("\n\n\n\n");
                        }
                    }

                    manager.displayText("User\n----\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");
                    manager.displayText("Username: " + ((User) editeduser).userName + "\n" +
                            "Password: " + ((User) editeduser).password + "\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\", \"Username\", \"Password\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("Jobs", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("To add a job write \"add <Job Name>\" and to delete a job write \"delete <Job Name>\"\n");
                    manager.displayText("Possible jobs are:");
                    boolean first = true;
                    for(Worker.JobEnum j : Worker.JobEnum.values()){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");
                    String ans = manager.getInput();
                    LinkedList<Worker.JobEnum> jobListTemp = manager.getDAL().getJobsByID(editeduser.ID);
                    if (ans.compareTo("back") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    String[] jobAndWhat = ans.split(" ");
                    boolean ansCheck = ((jobAndWhat[0].compareTo("delete") == 0)||(jobAndWhat[0].compareTo("add") == 0));
                    try{
                        Worker.JobEnum.valueOf(jobAndWhat[1]);
                    } catch (IllegalArgumentException e){
                        ansCheck = false;
                    }

                    while (!ansCheck){
                        manager.displayText("Error while reading input, please try again.\n");
                        manager.displayText("To add a job write \"add <Job Name>\" and to delete a job write \"delete <Job Name>\"\n");
                        ans = manager.getInput();
                        if (ans.compareTo("back") == 0) {

                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                        jobAndWhat = ans.split(" ");
                        ansCheck = ((jobAndWhat[0].compareTo("delete") == 0)||(jobAndWhat[0].compareTo("add") == 0));
                        try{
                            Worker.JobEnum.valueOf(jobAndWhat[1]);
                        } catch (IllegalArgumentException e){
                            ansCheck = false;
                        }
                    }

                    if((jobAndWhat[0].compareTo("delete") == 0)){
                        for(Worker.JobEnum j : jobListTemp){
                            if(j.toString().compareTo(jobAndWhat[1]) == 0)
                                if(jobAndWhat[1].compareTo("Driver") == 0){
                                    if (dal.deleteJobByID(editeduser.ID , j) && dal.deleteDriverByID(editeduser.ID)) {
                                        manager.displayText("Worker Updated!, Press any key to continue.\n");
                                        manager.displayText("\n\n\n\n");
                                    }
                                }
                                else if (dal.deleteJobByID(editeduser.ID , j)) {
                                    manager.displayText("Worker Updated!, Press any key to continue.\n");
                                    manager.displayText("\n\n\n\n");
                                }
                        }
                    }
                    else if((jobAndWhat[0].compareTo("add") == 0)) {
                        jobListTemp.add(Worker.JobEnum.valueOf(jobAndWhat[1]));
                        LinkedList<Worker.JobEnum> addList = new LinkedList<JobEnum>();
                        addList.add(Worker.JobEnum.valueOf(jobAndWhat[1]));

                        LicenseType licenceType = null;
                        if(jobAndWhat[1].compareTo("Driver") == 0) {
                            manager.displayText("Please enter the licence type:");
                            first = true;
                            for (LicenseType type : LicenseType.values()) {
                                if (first) {
                                    manager.displayText(type.toString());
                                    first = false;
                                } else
                                    manager.displayText(", " + type.toString());
                            }
                            manager.displayText(".\n");
                            String licence = manager.getInput();
                            if (licence.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");
                                return true;
                            }
                            boolean licenceCheck = false;
                            while (!licenceCheck) {
                                try {
                                    licenceType = LicenseType.valueOf(licence);
                                    licenceCheck = true;
                                } catch (Exception e) {
                                    manager.displayText("Licence type is not valid, please try again.\n");
                                    licence = manager.getInput();
                                }
                            }
                            if (dal.addJobsByID(editeduser.ID, addList) && dal.addDriverLicenceByID(editeduser.ID, licenceType)) {
                                manager.displayText("Worker Updated!, Press any key to continue.\n");
                                manager.displayText("\n\n\n\n");
                            }
                        }
                        else if (dal.addJobsByID(editeduser.ID, addList)) {
                            manager.displayText("Worker Updated!, Press any key to continue.\n");
                            manager.displayText("\n\n\n\n");
                        }
                    }




                    manager.displayText("Worker\n------\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("Username", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter new username: ");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    } else {
                        ((User) editeduser).userName = ans;
                        if (dal.updateUser((User) editeduser)) {
                            manager.displayText("Worker Updated!, Press any key to continue.\n");
                            manager.displayText("\n\n\n\n");
                        }
                    }

                    manager.displayText("User\n----\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");
                    manager.displayText("Username: " + ((User) editeduser).userName + "\n" +
                            "Password: " + ((User) editeduser).password + "\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\",\"Jobs\", \"Username\", \"Password\" or \"back\"\n");
                    return false;
                }
            });

            temp.addAction("Password", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Please enter new password: ");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    } else {
                        ((User) editeduser).password = ans;
                        if (dal.updateUser((User) editeduser)) {
                            manager.displayText("Worker Updated!, Press any key to continue.\n");
                            manager.displayText("\n\n\n\n");
                        }
                    }

                    manager.displayText("User\n----\n" +
                            "ID: " + editeduser.ID + "\n" +
                            "Name: " + editeduser.Name + "\n" +
                            "BankNo: " + editeduser.BankNO + "\n" +
                            "Employment Date: " + editeduser.EmpDate + "\n" +
                            "Employment Terms: " + editeduser.EmpTerms + "\n" +
                            "Jobs: ");
                    LinkedList<Worker.JobEnum> jobList = manager.getDAL().getJobsByID(editeduser.ID);
                    boolean first = true;
                    for(Worker.JobEnum j : jobList){
                        if(first){
                            manager.displayText(j.toString());
                            first = false;
                        }
                        else
                            manager.displayText(", " + j.toString());
                    }
                    manager.displayText(".\n");
                    manager.displayText("Username: " + ((User) editeduser).userName + "\n" +
                            "Password: " + ((User) editeduser).password + "\n");

                    manager.displayText("Please choose \"ID\", \"Name\", \"BankNo\", \"Employment Date\", \"Employment Terms\", \"Jobs\", \"Username\", \"Password\" or \"back\"\n");
                    return false;
                }
            });

            return temp;
        } else
            return new Menu(false, "Error!!");
    }

    //done
    public Menu getAddShift() {
        if (loggedIn.abilities.contains(Worker.JobEnum.HRManager)) {
            Menu temp = new Menu(false,
                    "Add Shift\n---------\nType \"back\" to return back to the Main Menu\n");

            temp.addAction("def", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Enter the workers ID:");
                    String ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    editeduser = manager.getDAL().getWorkerByID(ID);
                    while (editeduser == null) {
                        manager.displayText("A worker with such an ID doesn't exist!, please try again\n");
                        ID = manager.getInput();
                        if (ID.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                        editeduser = manager.getDAL().getWorkerByID(ID);
                    }

                    boolean another = true;
                    LinkedList<Shift> shiftList = new LinkedList<Shift>();
                    while (another) {
                        /*manager.displayText("Please enter a week day for the shift: ");
                        String day = manager.getInput();
                        if (day.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                        while ((day.compareTo("Sunday") != 0) && (day.compareTo("Monday") != 0) &&
                                (day.compareTo("Tuesday") != 0) && (day.compareTo("Wednesday") != 0) &&
                                (day.compareTo("Thursday") != 0) && (day.compareTo("Friday") != 0) &&
                                (day.compareTo("Saturday") != 0)) {
                            manager.displayText("Can't procces day, Please try again: ");
                            day = manager.getInput();
                            if (day.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }
                        }*/

                        manager.displayText("Please enter the shift time (Morning or Evening): ");
                        String time = manager.getInput();
                        if (time.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                        while ((time.compareTo("Morning") != 0) && (time.compareTo("Evening") != 0)) {
                            manager.displayText("Can't procces time, Please try again: ");
                            time = manager.getInput();
                            if (time.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }
                        }
                        TypeEnum shiftTimeVar;
                        if (time.compareTo("Morning") == 0)
                            shiftTimeVar = WorkerSchedule.TypeEnum.Morning;
                        else
                            shiftTimeVar = WorkerSchedule.TypeEnum.Evening;

                        manager.displayText("Please enter the shift date: ");
                        String date = manager.getInput();
                        if (date.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                        while (date.length() != 10 ||
                                date.charAt(2) != '/' || date.charAt(5) != '/' ||
                                Integer.parseInt(date.substring(0, 2)) < 0 || Integer.parseInt(date.substring(0, 2)) > 31 ||
                                Integer.parseInt(date.substring(3, 5)) < 0 || Integer.parseInt(date.substring(3, 5)) > 12 ||
                                Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 120 ||
                                Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 16) {
                            manager.displayText("Can't procces date, Please try again: ");
                            date = manager.getInput();
                            if (date.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }
                        }

                        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date dt1= null;
                        try {
                            dt1 = format1.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DateFormat format2=new SimpleDateFormat("EEEE", Locale.US);
                        String day = format2.format(dt1);

                        manager.displayText("Please enter the workers job from the following list:\n");
                        LinkedList<Worker.JobEnum> jobList = dal.getJobsByID(editeduser.ID);
                        boolean firstJob = true;
                        for (Worker.JobEnum jobTemp : jobList) {
                            if (firstJob) {
                                manager.displayText(jobTemp.toString());
                                firstJob = false;
                            } else {
                                manager.displayText(", " + jobTemp.toString());
                            }
                        }
                        manager.displayText(".\n");
                        String job = manager.getInput();
                        if (job.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }

                        Worker.JobEnum workerJob = null;
                        ;

                        boolean check = true;
                        try {
                            workerJob = Worker.JobEnum.valueOf(job);
                        } catch (Exception e) {
                            check = false;
                        }
                        while (!check) {
                            manager.displayText("This worker cannot do this job, pleaase choose a job from the list.\n");
                            job = manager.getInput();
                            if (job.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }

                            check = true;
                            try {
                                workerJob = Worker.JobEnum.valueOf(job);
                            } catch (Exception e) {
                                check = false;
                            }
                        }

                        Shift shift;
                        try {
                            shift = new Shift(day, shiftTimeVar, new Date(date), workerJob);
                        } catch (WorkerSchedule.NotValidDayException e) {
                            shift = null;
                            e.printStackTrace();
                        } catch (ParseException e) {
                            shift = null;
                            e.printStackTrace();
                        }
                        shiftList.add(shift);
                        manager.displayText("Add another shift? \"Yes\" or \"No\"\n");
                        String ans = manager.getInput();
                        if (ans.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                        while (ans.compareTo("Yes") != 0 && ans.compareTo("No") != 0) {
                            manager.displayText("Could not read input, please use \"Yes\" or \"No\" only\n");
                            ans = manager.getInput();
                            if (ans.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }
                        }
                        if (ans.compareTo("No") == 0)
                            another = false;
                    }

                    if (dal.addShiftsToWorkerBYid(editeduser.ID, shiftList)) {
                        manager.displayText("Shift added!, Press any key to continue.\n");
                        manager.getInput();
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                    }

                    manager.displayText("\n\n\n\n");

                    return true;
                }
            });

            return temp;
        } else
            return new Menu(false, "Error!!");
    }

    //done
    public Menu getDeleteShift() {
        if (loggedIn.abilities.contains(Worker.JobEnum.HRManager)) {
            Menu temp = new Menu(false,
                    "Delete Shift\n------------\nType \"back\" to return back to the Main Menu\n");

            temp.addAction("def", new CallBack() {
                public boolean call(MenuManager manager) {
                    manager.displayText("Enter the workers ID:");
                    String ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    editeduser = manager.getDAL().getWorkerByID(ID);
                    while (editeduser == null) {
                        manager.displayText("A worker with such an ID doesn't exist!, please try again\n");
                        ID = manager.getInput();
                        if (ID.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                        editeduser = manager.getDAL().getUserByID(ID);
                    }

                    boolean another = true;
                    LinkedList<Shift> shiftList = new LinkedList<Shift>();
                    while (another) {
                        /*manager.displayText("Please enter a week day for the shift: ");
                        String day = manager.getInput();
                        if (day.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                        while ((day.compareTo("Sunday") != 0) && (day.compareTo("Monday") != 0) &&
                                (day.compareTo("Tuesday") != 0) && (day.compareTo("Wednesday") != 0) &&
                                (day.compareTo("Thursday") != 0) && (day.compareTo("Friday") != 0) &&
                                (day.compareTo("Saturday") != 0)) {
                            manager.displayText("Can't procces day, Please try again: ");
                            day = manager.getInput();
                            if (day.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }
                        }*/

                        manager.displayText("Please enter the shift time (Morning or Evening): ");
                        String time = manager.getInput();
                        if (time.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                        while ((time.compareTo("Morning") != 0) && (time.compareTo("Evening") != 0)) {
                            manager.displayText("Can't procces time, Please try again: ");
                            time = manager.getInput();
                            if (time.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }
                        }
                        TypeEnum shiftTimeVar;
                        if (time.compareTo("Morning") == 0)
                            shiftTimeVar = WorkerSchedule.TypeEnum.Morning;
                        else
                            shiftTimeVar = WorkerSchedule.TypeEnum.Evening;

                        manager.displayText("Please enter the shift date: ");
                        String date = manager.getInput();
                        if (date.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                        while (date.length() != 10 ||
                                date.charAt(2) != '/' || date.charAt(5) != '/' ||
                                Integer.parseInt(date.substring(0, 2)) < 0 || Integer.parseInt(date.substring(0, 2)) > 31 ||
                                Integer.parseInt(date.substring(3, 5)) < 0 || Integer.parseInt(date.substring(3, 5)) > 12 ||
                                Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 120 ||
                                Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 16) {
                            manager.displayText("Can't procces date, Please try again: ");
                            date = manager.getInput();
                            if (date.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }
                        }

                        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date dt1= null;
                        try {
                            dt1 = format1.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DateFormat format2=new SimpleDateFormat("EEEE", Locale.US);
                        String day = format2.format(dt1);

                        Shift shift;
                        try {
                            shift = new Shift(day, shiftTimeVar, new Date(date), null);
                        } catch (WorkerSchedule.NotValidDayException e) {
                            shift = null;
                            e.printStackTrace();
                        } catch (ParseException e) {
                            shift = null;
                            e.printStackTrace();
                        }
                        shiftList.add(shift);
                        manager.displayText("Delete another shift? \"Yes\" or \"No\"\n");
                        String ans = manager.getInput();
                        if (ans.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                        while (ans.compareTo("Yes") != 0 && ans.compareTo("No") != 0) {
                            manager.displayText("Could not read input, please use \"Yes\" or \"No\" only\n");
                            ans = manager.getInput();
                            if (ans.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }
                        }
                        if (ans.compareTo("No") == 0)
                            another = false;
                    }

                    if (dal.deleteShiftsByWorkerID(editeduser.ID, shiftList)) {
                        manager.displayText("Shift deleted!, Press any key to continue.\n");
                        manager.getInput();
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                    }

                    manager.displayText("\n\n\n\n");

                    return true;
                }
            });

            return temp;
        } else
            return new Menu(false, "Error!!");
    }


    //done
    public Menu getEditSchedule() {
        Menu temp = new Menu(true,
                "Edit Worker Schedule\n--------------------\nType \"back\" to return back to the Main Menu\n" +
                        "Please Type \"add\" to add a shift to the Schedule or \"delete\" to delete a shift from the Schedule\n");

        temp.addAction("delete", new CallBack() {
            public boolean call(MenuManager manager) {
                manager.displayText("Enter the user ID:");
                String ID = manager.getInput();
                if (ID.compareTo("back") == 0) {
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
                editeduser = manager.getDAL().getUserByID(ID);
                while (editeduser == null) {
                    manager.displayText("A user with such an ID doesn't exist!, please try again\n");
                    ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    editeduser = manager.getDAL().getUserByID(ID);
                }
                LinkedList<WorkerSchedule> schedList = new LinkedList<>();
                boolean another = true;
                while (another) {
                    manager.displayText("Please enter a week day for the shift: ");
                    String day = manager.getInput();
                    if (day.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while ((day.compareTo("Sunday") != 0) && (day.compareTo("Monday") != 0) &&
                            (day.compareTo("Tuesday") != 0) && (day.compareTo("Wednesday") != 0) &&
                            (day.compareTo("Thursday") != 0) && (day.compareTo("Friday") != 0) &&
                            (day.compareTo("Saturday") != 0)) {
                        manager.displayText("Can't procces day, Please try again: ");
                        day = manager.getInput();
                        if (day.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }

                    manager.displayText("Please enter the shift time (Morning or Evening): ");
                    String time = manager.getInput();
                    if (time.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while ((time.compareTo("Morning") != 0) && (time.compareTo("Evening") != 0)) {
                        manager.displayText("Can't procces time, Please try again: ");
                        time = manager.getInput();
                        if (time.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }
                    WorkerSchedule.TypeEnum shiftTimeVar;
                    if (time.compareTo("Morning") == 0)
                        shiftTimeVar = WorkerSchedule.TypeEnum.Morning;
                    else
                        shiftTimeVar = WorkerSchedule.TypeEnum.Evening;

                    WorkerSchedule shift;

                    try {
                        shift = new WorkerSchedule(day, shiftTimeVar);
                    } catch (WorkerSchedule.NotValidDayException e) {
                        shift = null;
                        e.printStackTrace();
                    }

                    for (WorkerSchedule wSchedule : dal.getWorkerScheduleByID(editeduser.ID)) {
                        if (wSchedule.get_day() == shift.get_day() && wSchedule.get_type() == shift.get_type())
                            schedList.add(shift);
                    }


                    manager.displayText("Delete another shift? \"Yes\" or \"No\"\n");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while (ans.compareTo("Yes") != 0 && ans.compareTo("No") != 0) {
                        manager.displayText("Could not read input, please use \"Yes\" or \"No\" only\n");
                        ans = manager.getInput();
                        if (ans.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }
                    if (ans.compareTo("No") == 0)
                        another = false;
                }

                manager.displayText("Are you sure you want to delete the schedule shifts? type \"yes\" or \"no\"\n");
                String ans2 = manager.getInput();
                if (ans2.compareTo("back") == 0 || ans2.compareTo("no") == 0) {

                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
                while (ans2.compareTo("yes") != 0) {
                    manager.displayText("Please replay with \"yes\" or \"no\" only\n");
                    ans2 = manager.getInput();
                    if (ans2.compareTo("back") == 0 || ans2.compareTo("no") == 0) {

                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                }

                if (dal.deleteWorkerScheduleByID(editeduser.ID, schedList)) {
                    manager.displayText("User deleted from the database!\nPress any key to return back to Main Manu");
                    manager.getInput();
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");

                    return true;
                }

                manager.displayText("\n\n\n\n");

                return true;
            }
        });

        temp.addAction("add", new CallBack() {
            public boolean call(MenuManager manager) {
                manager.displayText("Enter the user ID:");
                String ID = manager.getInput();
                if (ID.compareTo("back") == 0) {
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
                editeduser = manager.getDAL().getWorkerByID(ID);
                while (editeduser == null) {
                    manager.displayText("A user with such an ID doesn't exist!, please try again\n");
                    ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    editeduser = manager.getDAL().getWorkerByID(ID);
                }
                LinkedList<WorkerSchedule> schedList = new LinkedList<>();
                boolean another = true;
                while (another) {
                    manager.displayText("Please enter a week day for the shift: ");
                    String day = manager.getInput();
                    if (day.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while ((day.compareTo("Sunday") != 0) && (day.compareTo("Monday") != 0) &&
                            (day.compareTo("Tuesday") != 0) && (day.compareTo("Wednesday") != 0) &&
                            (day.compareTo("Thursday") != 0) && (day.compareTo("Friday") != 0) &&
                            (day.compareTo("Saturday") != 0)) {
                        manager.displayText("Can't procces day, Please try again: ");
                        day = manager.getInput();
                        if (day.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }

                    manager.displayText("Please enter the shift time (Morning or Evening): ");
                    String time = manager.getInput();
                    if (time.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while ((time.compareTo("Morning") != 0) && (time.compareTo("Evening") != 0)) {
                        manager.displayText("Can't procces time, Please try again: ");
                        time = manager.getInput();
                        if (time.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }
                    WorkerSchedule.TypeEnum shiftTimeVar;
                    if (time.compareTo("Morning") == 0)
                        shiftTimeVar = WorkerSchedule.TypeEnum.Morning;
                    else
                        shiftTimeVar = WorkerSchedule.TypeEnum.Evening;

                    WorkerSchedule shift;

                    try {
                        shift = new WorkerSchedule(day, shiftTimeVar);
                    } catch (WorkerSchedule.NotValidDayException e) {
                        shift = null;
                        e.printStackTrace();
                    }

                    boolean temp = false;
                    for (WorkerSchedule wSchedule : dal.getWorkerScheduleByID(editeduser.ID)) {
                        if (wSchedule.get_day() == shift.get_day() && wSchedule.get_type() == shift.get_type())
                            temp = true;
                    }
                    if (!temp)
                        schedList.add(shift);


                    manager.displayText("Add another shift? \"Yes\" or \"No\"\n");
                    String ans = manager.getInput();
                    if (ans.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while (ans.compareTo("Yes") != 0 && ans.compareTo("No") != 0) {
                        manager.displayText("Could not read input, please use \"Yes\" or \"No\" only\n");
                        ans = manager.getInput();
                        if (ans.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }
                    if (ans.compareTo("No") == 0)
                        another = false;
                }


                if (dal.addWorkerScheduleByID(editeduser.ID, schedList)) {
                    manager.displayText("Worker schedual added to the database!\nPress any key to return back to Main Manu");
                    manager.getInput();
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");

                    return true;
                }

                manager.displayText("\n\n\n\n");

                return true;
            }
        });

        return temp;
    }

    //done
    public Menu getSetShiftManager() {
        Menu temp = new Menu(false,
                "Set Shift Manager\n-----------------\nType \"back\" to return back to the Main Menu\n");

        temp.addAction("def", new CallBack() {
            public boolean call(MenuManager manager) {
                manager.displayText("Enter the workers ID:");
                String ID = manager.getInput();
                if (ID.compareTo("back") == 0) {
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
                editeduser = manager.getDAL().getWorkerByID(ID);
                while (editeduser == null) {
                    manager.displayText("A worker with such an ID doesn't exist!, please try again\n");
                    ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    editeduser = manager.getDAL().getUserByID(ID);
                }
                while (!dal.getJobsByID(editeduser.ID).contains(Worker.JobEnum.ShiftManager)) {
                    manager.displayText("This worker cannot be a shift manager, please choose an other worker\n");
                    ID = manager.getInput();
                    if (ID.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");
                        return true;
                    }
                    editeduser = manager.getDAL().getWorkerByID(ID);
                    while (editeduser == null) {
                        manager.displayText("A worker with such an ID doesn't exist!, please try again\n");
                        ID = manager.getInput();
                        if (ID.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");
                            return true;
                        }
                        editeduser = manager.getDAL().getUserByID(ID);
                    }
                }

                String day;
                String time;
                WorkerSchedule.TypeEnum shiftTimeVar;
                String date;
                Shift shift;
                boolean checker = false;

                /*manager.displayText("Please enter a week day for the shift: ");
                day = manager.getInput();
                if (day.compareTo("back") == 0) {
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");

                    return true;
                }
                while ((day.compareTo("Sunday") != 0) && (day.compareTo("Monday") != 0) &&
                        (day.compareTo("Tuesday") != 0) && (day.compareTo("Wednesday") != 0) &&
                        (day.compareTo("Thursday") != 0) && (day.compareTo("Friday") != 0) &&
                        (day.compareTo("Saturday") != 0)) {
                    manager.displayText("Can't procces day, Please try again: ");
                    day = manager.getInput();
                    if (day.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                }*/

                manager.displayText("Please enter the shift time (Morning or Evening): ");
                time = manager.getInput();
                if (time.compareTo("back") == 0) {
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");

                    return true;
                }
                while ((time.compareTo("Morning") != 0) && (time.compareTo("Evening") != 0)) {
                    manager.displayText("Can't procces time, Please try again: ");
                    time = manager.getInput();
                    if (time.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                }
                if (time.compareTo("Morning") == 0)
                    shiftTimeVar = WorkerSchedule.TypeEnum.Morning;
                else
                    shiftTimeVar = WorkerSchedule.TypeEnum.Evening;

                manager.displayText("Please enter the shift date: ");
                date = manager.getInput();
                if (date.compareTo("back") == 0) {
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");

                    return true;
                }
                while (date.length() != 10 ||
                        date.charAt(2) != '/' || date.charAt(5) != '/' ||
                        Integer.parseInt(date.substring(0, 2)) < 0 || Integer.parseInt(date.substring(0, 2)) > 31 ||
                        Integer.parseInt(date.substring(3, 5)) < 0 || Integer.parseInt(date.substring(3, 5)) > 12 ||
                        Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 120 ||
                        Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 16) {
                    manager.displayText("Can't procces date, Please try again: ");
                    date = manager.getInput();
                    if (date.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                }

                SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date dt1= null;
                try {
                    dt1 = format1.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat format2=new SimpleDateFormat("EEEE", Locale.US);
                day = format2.format(dt1);

                try {
                    shift = new Shift(day, shiftTimeVar, new Date(date), Worker.JobEnum.ShiftManager);
                } catch (WorkerSchedule.NotValidDayException e) {
                    shift = null;
                    e.printStackTrace();
                } catch (ParseException e) {
                    shift = null;
                    e.printStackTrace();
                }

                for (Shift shiftTemp : dal.getShiftsByWorkerID(editeduser.ID)) {
                    if (shiftTemp.get_day() == day && shiftTemp.get_type() == shiftTimeVar)
                        checker = true;
                }

                while (checker) {
                    manager.displayText("You cannot enter a shift in which the worker already works\n");

                    /*manager.displayText("Please enter a week day for the shift: ");
                    day = manager.getInput();
                    if (day.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while ((day.compareTo("Sunday") != 0) && (day.compareTo("Monday") != 0) &&
                            (day.compareTo("Tuesday") != 0) && (day.compareTo("Wednesday") != 0) &&
                            (day.compareTo("Thursday") != 0) && (day.compareTo("Friday") != 0) &&
                            (day.compareTo("Saturday") != 0)) {
                        manager.displayText("Can't procces day, Please try again: ");
                        day = manager.getInput();
                        if (day.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }*/

                    manager.displayText("Please enter the shift time (Morning or Evening): ");
                    time = manager.getInput();
                    if (time.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while (time.compareTo("Morning") != 0 && day.compareTo("Evening") != 0) {
                        manager.displayText("Can't procces time, Please try again: ");
                        time = manager.getInput();
                        if (time.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }
                    if (time.compareTo("Morning") == 0)
                        shiftTimeVar = WorkerSchedule.TypeEnum.Morning;
                    else
                        shiftTimeVar = WorkerSchedule.TypeEnum.Evening;

                    manager.displayText("Please enter the shift date: ");
                    date = manager.getInput();
                    if (date.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while (date.length() != 10 ||
                            date.charAt(2) != '/' || date.charAt(5) != '/' ||
                            Integer.parseInt(date.substring(0, 2)) < 0 || Integer.parseInt(date.substring(0, 2)) > 31 ||
                            Integer.parseInt(date.substring(3, 5)) < 0 || Integer.parseInt(date.substring(3, 5)) > 12 ||
                            Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 120) {
                        manager.displayText("Can't procces date, Please try again: ");
                        date = manager.getInput();
                        if (date.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }



                    format1=new SimpleDateFormat("dd/MM/yyyy");
                    dt1= null;
                    try {
                        dt1 = format1.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    format2=new SimpleDateFormat("EEEE", Locale.US);
                    day = format2.format(dt1);

                    try {
                        shift = new Shift(day, shiftTimeVar, new Date(date), Worker.JobEnum.ShiftManager);
                    } catch (WorkerSchedule.NotValidDayException e) {
                        shift = null;
                        e.printStackTrace();
                    } catch (ParseException e) {
                        shift = null;
                        e.printStackTrace();
                    }

                    for (Shift shiftTemp : dal.getShiftsByWorkerID(editeduser.ID)) {
                        if (shiftTemp.get_day() == day && shiftTemp.get_type() == shiftTimeVar)
                            checker = true;
                    }
                }
                LinkedList<Shift> shiftList = new LinkedList<>();
                shiftList.add(shift);
                if (dal.addManagerByID(editeduser.ID, shiftList)) {
                    manager.displayText("Shift added!, Press any key to continue.\n");
                    manager.getInput();
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");
                }

                manager.displayText("\n\n\n\n");

                return true;
            }
        });
        return temp;
    }

    //done
    public Menu getSearchShifts()
    {
        if (loggedIn.abilities.contains(Worker.JobEnum.HRManager)) {
            Menu temp = new Menu(false,
                    "Search Shifts\n-------------\nType \"back\" to return back to the Main Menu\n");

            temp.addAction("def", new CallBack() {
                public boolean call(MenuManager manager) {

                    /*manager.displayText("Please enter a week day for the shift: ");
                    String day = manager.getInput();
                    if (day.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while ((day.compareTo("Sunday") != 0) && (day.compareTo("Monday") != 0) &&
                            (day.compareTo("Tuesday") != 0) && (day.compareTo("Wednesday") != 0) &&
                            (day.compareTo("Thursday") != 0) && (day.compareTo("Friday") != 0) &&
                            (day.compareTo("Saturday") != 0)) {
                        manager.displayText("Can't procces day, Please try again: ");
                        day = manager.getInput();
                        if (day.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }*/

                    manager.displayText("Please enter the shift time (Morning or Evening): ");
                    String time = manager.getInput();
                    if (time.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while ((time.compareTo("Morning") != 0) && (time.compareTo("Evening") != 0)) {
                        manager.displayText("Can't procces time, Please try again: ");
                        time = manager.getInput();
                        if (time.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }
                    TypeEnum shiftTimeVar;
                    if (time.compareTo("Morning") == 0)
                        shiftTimeVar = WorkerSchedule.TypeEnum.Morning;
                    else
                        shiftTimeVar = WorkerSchedule.TypeEnum.Evening;

                    manager.displayText("Please enter the shift date: ");
                    String date = manager.getInput();
                    if (date.compareTo("back") == 0) {
                        manager.setMenu(manuNames.MainManu);
                        manager.displayText("\n\n\n\n");

                        return true;
                    }
                    while (date.length() != 10 ||
                            date.charAt(2) != '/' || date.charAt(5) != '/' ||
                            Integer.parseInt(date.substring(0, 2)) < 0 || Integer.parseInt(date.substring(0, 2)) > 31 ||
                            Integer.parseInt(date.substring(3, 5)) < 0 || Integer.parseInt(date.substring(3, 5)) > 12 ||
                            Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 120) {
                        manager.displayText("Can't procces date, Please try again: ");
                        date = manager.getInput();
                        if (date.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                    }

                    SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                    java.util.Date dt1= null;
                    try {
                        dt1 = format1.parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    DateFormat format2=new SimpleDateFormat("EEEE", Locale.US);
                    String day = format2.format(dt1);

                    Shift shift;
                    try {
                        shift = new Shift(day, shiftTimeVar, new Date(date), Worker.JobEnum.HRManager);
                    } catch (WorkerSchedule.NotValidDayException e) {
                        shift = null;
                        e.printStackTrace();
                    } catch (ParseException e) {
                        shift = null;
                        e.printStackTrace();
                    }

                    LinkedList<Worker> workerList = manager.getDAL().getWorkersByShift(shift);

                    manager.displayText("ID       |Name            |Job         |Employment Date\n");

                    for (Worker w : workerList) {
                        manager.displayText("-------------------------------------------------------\n");
                        if (w.ID.length() >= 9) {
                            manager.displayText(w.ID.substring(0, 9) + "|");
                        } else {
                            manager.displayText(w.ID);
                            String space = "";
                            for (int i = 0; i < 9 - w.ID.length(); i++)
                                space += " ";
                            manager.displayText(space + "|");
                        }

                        if (w.Name.length() >= 16) {
                            manager.displayText(w.Name.substring(0, 16) + "|");
                        } else {
                            manager.displayText(w.Name);
                            String space = "";
                            for (int i = 0; i < 16 - w.Name.length(); i++)
                                space += " ";
                            manager.displayText(space + "|");
                        }

                        JobEnum newJob = null;
                        LinkedList<Shift> shiftList = manager.dal.getShiftsByWorkerID(w.ID);

                        for (Shift s : shiftList) {
                            if (s.get_type() == shift.get_type() && s.get_date().toString().compareTo(shift.get_date().toString()) == 0) {
                                newJob = s.getJob();
                            } else {
                                System.out.println("s:" + s.get_type() + ", " + s.get_date().toString());
                                System.out.println("a:" + shift.get_type() + ", " + shift.get_date().toString());
                            }
                        }
                        if (newJob.toString().length() >= 12) {
                            manager.displayText(newJob.toString().substring(0, 12) + "|");
                        } else {
                            manager.displayText(newJob.toString());
                            String space = "";
                            for (int i = 0; i < 12 - newJob.toString().length(); i++)
                                space += " ";
                            manager.displayText(space + "|");
                        }

                        if (w.EmpDate.toString().length() >= 15) {
                            manager.displayText(w.EmpDate.toString().substring(0, 15) + "|");
                        } else {
                            manager.displayText(w.EmpDate.toString());
                            String space = "";
                            for (int i = 0; i < 15 - w.EmpDate.toString().length(); i++)
                                space += " ";
                            manager.displayText(space + "\n");
                        }

                    }

                    manager.displayText("\nPress any key to return back to Main Manu");
                    manager.getInput();
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });

            return temp;
        } else
            return new Menu(false, "Error!!");
    }

    //done
    public Menu getSeeShiftWorkers() {
        if (loggedIn.abilities.contains(Worker.JobEnum.ShiftManager)) {
            Menu temp = new Menu(false,
                    "See Shift Workers\n-----------------\nType \"back\" to return back to the Main Menu\n");

            temp.addAction("def", new CallBack() {
                public boolean call(MenuManager manager) {


                    Shift shift;
                    boolean shiftCheck = false;

                    do {
                        manager.displayText("Please enter a week day for the shift: ");
                        String day = manager.getInput();
                        if (day.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                        while ((day.compareTo("Sunday") != 0) && (day.compareTo("Monday") != 0) &&
                                (day.compareTo("Tuesday") != 0) && (day.compareTo("Wednesday") != 0) &&
                                (day.compareTo("Thursday") != 0) && (day.compareTo("Friday") != 0) &&
                                (day.compareTo("Saturday") != 0)) {
                            manager.displayText("Can't procces day, Please try again: ");
                            day = manager.getInput();
                            if (day.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }
                        }

                        manager.displayText("Please enter the shift time (Morning or Evening): ");
                        String time = manager.getInput();
                        if (time.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                        while ((time.compareTo("Morning") != 0) && (time.compareTo("Evening") != 0)) {
                            manager.displayText("Can't procces time, Please try again: ");
                            time = manager.getInput();
                            if (time.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }
                        }
                        TypeEnum shiftTimeVar;
                        if (time.compareTo("Morning") == 0)
                            shiftTimeVar = WorkerSchedule.TypeEnum.Morning;
                        else
                            shiftTimeVar = WorkerSchedule.TypeEnum.Evening;

                        manager.displayText("Please enter the shift date: ");
                        String date = manager.getInput();
                        if (date.compareTo("back") == 0) {
                            manager.setMenu(manuNames.MainManu);
                            manager.displayText("\n\n\n\n");

                            return true;
                        }
                        while (date.length() != 10 ||
                                date.charAt(2) != '/' || date.charAt(5) != '/' ||
                                Integer.parseInt(date.substring(0, 2)) < 0 || Integer.parseInt(date.substring(0, 2)) > 31 ||
                                Integer.parseInt(date.substring(3, 5)) < 0 || Integer.parseInt(date.substring(3, 5)) > 12 ||
                                Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 120 ||
                                Integer.parseInt(date.substring(6, 10)) < Calendar.getInstance().get(Calendar.YEAR) - 16) {
                            manager.displayText("Can't procces date, Please try again: ");
                            date = manager.getInput();
                            if (date.compareTo("back") == 0) {
                                manager.setMenu(manuNames.MainManu);
                                manager.displayText("\n\n\n\n");

                                return true;
                            }
                        }
                        try {
                            shift = new Shift(day, shiftTimeVar, new Date(date), Worker.JobEnum.HRManager);
                        } catch (WorkerSchedule.NotValidDayException e) {
                            shift = null;
                            e.printStackTrace();
                        } catch (ParseException e) {
                            shift = null;
                            e.printStackTrace();
                        }

                        for (Shift s : manager.dal.getShiftsByManagerID(loggedIn.ID)) {
                            if (s.get_day().compareTo(shift.get_day()) == 0 &&
                                    s.get_type() == shift.get_type() &&
                                    s.get_date().toString().compareTo(shift.get_date().toString()) == 0)
                                shiftCheck = true;
                            else {
                                System.out.println("s    :" + s.get_day() + ", " + s.get_type() + ", " + s.get_date().toString());
                                System.out.println("shift:" + shift.get_day() + ", " + shift.get_type() + ", " + shift.get_date().toString());
                            }
                        }

                        if (!shiftCheck)
                            manager.displayText("The user is not the manager of this shift so it cannot be seen!, please try again.\n");

                    } while (!shiftCheck);


                    LinkedList<Worker> workerList = manager.getDAL().getWorkersByShift(shift);

                    manager.displayText("ID       |Name            |Job         |Employment Date\n");

                    for (Worker w : workerList) {
                        manager.displayText("-------------------------------------------------------\n");
                        if (w.ID.length() >= 9) {
                            manager.displayText(w.ID.substring(0, 9) + "|");
                        } else {
                            manager.displayText(w.ID);
                            String space = "";
                            for (int i = 0; i < 9 - w.ID.length(); i++)
                                space += " ";
                            manager.displayText(space + "|");
                        }

                        if (w.Name.length() >= 16) {
                            manager.displayText(w.Name.substring(0, 16) + "|");
                        } else {
                            manager.displayText(w.Name);
                            String space = "";
                            for (int i = 0; i < 16 - w.Name.length(); i++)
                                space += " ";
                            manager.displayText(space + "|");
                        }

                        JobEnum newJob = null;
                        LinkedList<Shift> shiftList = manager.dal.getShiftsByWorkerID(w.ID);

                        for (Shift s : shiftList) {
                            if (s.get_type() == shift.get_type() && s.get_date().toString().compareTo(shift.get_date().toString()) == 0) {
                                newJob = s.getJob();
                            } else {
                                System.out.println("s:" + s.get_type() + ", " + s.get_date().toString());
                                System.out.println("a:" + shift.get_type() + ", " + shift.get_date().toString());
                            }
                        }
                        if (newJob.toString().length() >= 12) {
                            manager.displayText(newJob.toString().substring(0, 12) + "|");
                        } else {
                            manager.displayText(newJob.toString());
                            String space = "";
                            for (int i = 0; i < 12 - newJob.toString().length(); i++)
                                space += " ";
                            manager.displayText(space + "|");
                        }

                        if (w.EmpDate.toString().length() >= 15) {
                            manager.displayText(w.EmpDate.toString().substring(0, 15) + "|");
                        } else {
                            manager.displayText(w.EmpDate.toString());
                            String space = "";
                            for (int i = 0; i < 15 - w.EmpDate.toString().length(); i++)
                                space += " ";
                            manager.displayText(space + "\n");
                        }

                    }

                    manager.displayText("\nPress any key to return back to Main Manu");
                    manager.getInput();
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });

            return temp;
        } else
            return new Menu(false, "Error!!");
    }

    public Menu getSeeAllUsers() {
        if (loggedIn.abilities.contains(Worker.JobEnum.HRManager)) {
            Menu temp = new Menu(false,
                    "See All Workers\n---------------\nType \"back\" to return back to the Main Menu\n");

            temp.addAction("def", new CallBack() {
                public boolean call(MenuManager manager) {
                    LinkedList<Worker> workerList = manager.getDAL().getAllUsers();

                    manager.displayText("ID       |Name            |Employment Date|Username        |password        \n");

                    for (Worker w : workerList) {
                        manager.displayText("----------------------------------------------------------------------------\n");
                        if (w.ID.length() >= 9) {
                            manager.displayText(w.ID.substring(0, 9) + "|");
                        } else {
                            manager.displayText(w.ID);
                            String space = "";
                            for (int i = 0; i < 9 - w.ID.length(); i++)
                                space += " ";
                            manager.displayText(space + "|");
                        }

                        if (w.Name.length() >= 16) {
                            manager.displayText(w.Name.substring(0, 16) + "|");
                        } else {
                            manager.displayText(w.Name);
                            String space = "";
                            for (int i = 0; i < 16 - w.Name.length(); i++)
                                space += " ";
                            manager.displayText(space + "|");
                        }

                        if (w.EmpDate.toString().length() >= 15) {
                            manager.displayText(w.EmpDate.toString().substring(0, 15) + "|");
                        } else {
                            manager.displayText(w.EmpDate.toString());
                            String space = "";
                            for (int i = 0; i < 15 - w.EmpDate.toString().length(); i++)
                                space += " ";
                            manager.displayText(space + "|");
                        }

                        User u;
                        if ((u = manager.dal.getUserByID(w.ID)) != null) {
                            if (u.userName.length() >= 16) {
                                manager.displayText(u.userName.substring(0, 16) + "|");
                            } else {
                                manager.displayText(u.userName);
                                String space = "";
                                for (int i = 0; i < 16 - u.userName.length(); i++)
                                    space += " ";
                                manager.displayText(space + "|");
                            }

                            if (u.password.length() >= 16) {
                                manager.displayText(u.password.substring(0, 16) + "\n");
                            } else {
                                manager.displayText(u.password);
                                String space = "";
                                for (int i = 0; i < 16 - u.password.length(); i++)
                                    space += " ";
                                manager.displayText(space + "\n");
                            }
                        } else {
                            String space = "NONE";
                            for (int i = 0; i < 12; i++) {
                                space += " ";
                            }
                            space += "|NONE";

                            for (int i = 0; i < 12; i++) {
                                space += " ";
                            }
                            manager.displayText(space + "\n");
                        }

                    }

                    manager.displayText("\nPress any key to return back to Main Manu");
                    manager.getInput();
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });

            return temp;
        } else
            return new Menu(false, "Error!!");
    }

    public Menu getSeeAllShifts() {
        if (loggedIn.abilities.contains(Worker.JobEnum.HRManager)) {
            Menu temp = new Menu(false,
                    "See All Workers\n---------------\nType \"back\" to return back to the Main Menu\n");

            temp.addAction("def", new CallBack() {
                public boolean call(MenuManager manager) {
                    LinkedList<Worker> workerList = manager.getDAL().getAllUsers();

                    manager.displayText("ID       |Day         |Date      |Type        |Job         \n");

                    for (Worker w : workerList) {
                        for (Shift s : manager.getDAL().getShiftsByWorkerID(w.ID)) {
                            manager.displayText("----------------------------------------------------------------------------\n");
                            if (w.ID.length() >= 9) {
                                manager.displayText(w.ID.substring(0, 9) + "|");
                            } else {
                                manager.displayText(w.ID);
                                String space = "";
                                for (int i = 0; i < 9 - w.ID.length(); i++)
                                    space += " ";
                                manager.displayText(space + "|");
                            }

                            if (s.get_day().length() >= 12) {
                                manager.displayText(s.get_day().substring(0, 12) + "|");
                            } else {
                                manager.displayText(s.get_day());
                                String space = "";
                                for (int i = 0; i < 12 - s.get_day().length(); i++)
                                    space += " ";
                                manager.displayText(space + "|");
                            }

                            if (s.get_date().toString().length() >= 10) {
                                manager.displayText(s.get_date().toString().substring(0, 10) + "|");
                            } else {
                                manager.displayText(s.get_date().toString());
                                String space = "";
                                for (int i = 0; i < 10 - s.get_date().toString().length(); i++)
                                    space += " ";
                                manager.displayText(space + "|");
                            }

                            if (s.get_type().toString().length() >= 12) {
                                manager.displayText(s.get_type().toString().substring(0, 12) + "|");
                            } else {
                                manager.displayText(s.get_type().toString());
                                String space = "";
                                for (int i = 0; i < 12 - s.get_type().toString().length(); i++)
                                    space += " ";
                                manager.displayText(space + "|");
                            }

                            if (s.getJob().toString().length() >= 12) {
                                manager.displayText(s.getJob().toString().substring(0, 12) + "\n");
                            } else {
                                manager.displayText(s.getJob().toString());
                                String space = "";
                                for (int i = 0; i < 12 - s.getJob().toString().length(); i++)
                                    space += " ";
                                manager.displayText(space + "\n");
                            }
                        }

                    }

                    manager.displayText("\nPress any key to return back to Main Manu");
                    manager.getInput();
                    manager.setMenu(manuNames.MainManu);
                    manager.displayText("\n\n\n\n");
                    return true;
                }
            });

            return temp;
        } else
            return new Menu(false, "Error!!");
    }

}
