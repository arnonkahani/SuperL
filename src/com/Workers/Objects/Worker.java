package com.Workers.Objects;

import java.util.LinkedList;

public class Worker {


    public Worker() {

    }

    public enum JobEnum {
        ShiftManager,
        HRManager,
        Driver,
        Cahier,
        Cleaner,
        WarehouseWorker,
        StorageManager,
        SupplierManager,
        TransportationManager
    }

    public String ID;
    public String Name;
    public String BankNO;
    public String EmpTerms;
    public String EmpDate;

    public LinkedList<JobEnum> abilities;

    public Worker(String ID, String Name, String BankNO, String EmpTerms, String EmpDate, LinkedList<JobEnum> abilities) {
        this.ID = ID;
        this.Name = Name;
        this.BankNO = BankNO;
        this.EmpTerms = EmpTerms;
        this.EmpDate = EmpDate;
        this.abilities = abilities;

    }

    public Worker(Worker other) {
        this.ID = other.getID();
        this.Name = other.getName();
        this.BankNO = other.getBankNO();
        this.EmpTerms = other.getEmpTerms();
        this.EmpDate = other.getEmpDate();
        this.abilities = other.getAbilities();
    }


    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getBankNO() {
        return BankNO;
    }

    public String getEmpTerms() {
        return EmpTerms;
    }

    public String getEmpDate() {
        return EmpDate;
    }

    public LinkedList<JobEnum> getAbilities() {
        return abilities;
    }

    public void setAbilities(LinkedList<JobEnum> abilities) {
        this.abilities = abilities;
    }
}


