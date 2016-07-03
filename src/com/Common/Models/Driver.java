package com.Common.Models;


import com.Workers.Objects.Worker;

public class Driver extends Worker {

    private LicenseType DriverLicenseType;

    public Driver(Worker w, LicenseType driver_License_Type){
        super(w);
        this.DriverLicenseType = driver_License_Type;
    }

    public LicenseType getDriverLicenseType() {
        return DriverLicenseType;
    }

    public void setDriverLicenseType(LicenseType driverLicenseType) {
        DriverLicenseType = driverLicenseType;
    }

    public boolean isLicenseBiggerThan(LicenseType driverLicenseType){
        return this.DriverLicenseType.isBigger(driverLicenseType);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)",getName(),getID());
    }
}
