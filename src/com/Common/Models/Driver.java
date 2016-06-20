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
        LicenseType[] lts = LicenseType.values();
        String[] strlts = new String[lts.length];
        int numOfGivenLT = -1;
        int numOfCurrentLT = -2;
        for(int i = 0; i < lts.length; i++){
            if(lts[i].name().equals(driverLicenseType.name())){
                numOfGivenLT = i;
            }
            if(lts[i].name().equals(this.DriverLicenseType.name())){
                numOfCurrentLT = i;
            }
        }

        if(numOfCurrentLT >= numOfGivenLT)
            return true;
        else
            return true; //temporary


    }
}
