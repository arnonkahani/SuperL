package com.Transpotation.Models;

import com.Common.DB.DB;
import com.Common.Models.Driver;
import com.Common.UI.Hide;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class Transportation {
    private Integer ID = null;
    private Date StartTime = null;
    private Date EndTime = null ;
    private String Description ;
    private Truck truck;
    private Driver driver;
    private Area area;
    private int arrived;

    public Transportation() {
    }
    public Transportation(Integer ID, String description , Date StartTime ,Date EndTIme,
                          Truck truck , Driver driver,Area area) {
        this.ID = ID;
        Description = description;
        this.EndTime=EndTIme;
        this.StartTime =StartTime;
        this.driver =driver;
        this.truck=truck;
        this.area = area;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) throws ValidationException {
        if(EndTime != null && EndTime.before(startTime))
            throw new ValidationException("Start time must be before End time");
        StartTime = startTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void end(){
        EndTime = new Date();
    }

    public void setEndTime(Date endTime) throws ValidationException {
        if(endTime.getTime() < new Date().getTime())
            throw new ValidationException("End time must be in the future");

        if(StartTime != null){
            GregorianCalendar startplusday = new GregorianCalendar();
            startplusday.setTime(StartTime);
            startplusday.add(Calendar.DAY_OF_MONTH,1);

            GregorianCalendar startplusweek = new GregorianCalendar();
            startplusweek.setTime(StartTime);
            startplusweek.add(Calendar.DAY_OF_MONTH,7);

            if(endTime.before(startplusday.getTime()))
                throw new ValidationException("End time must be at least a day after Start time");

            if(endTime.after(startplusweek.getTime()))
                throw new ValidationException("End time must be at most a week after Start time");
        }

        EndTime = endTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) throws ValidationException {
        //if(driver != null && driver.isLicenseBiggerThan(truck.getLicenseType()))
        //    throw new ValidationException("Licence Type does not match with attached driver's Licence Type");
        this.truck = truck;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) throws ValidationException {
        if(truck != null && (driver.getDriverLicenseType().compareTo(truck.getLicenseType()) < 0))
            throw new ValidationException("Licence Type does not match with attached truck's Licence Type");
        this.driver = driver;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    /*public double getTotalWeight() throws SQLException, ClassNotFoundException {
        List<OrderDocument> l = DB.getInstance().getOrderDocumentIDBHandler().select("transportation = ?",ID);
        double sum = 0;
        for(OrderDocument d : l)
            sum += d.getTotalWeight();
        return sum;
    }*/

    /*public int getNumberOfOrders() throws SQLException, ClassNotFoundException {
        return DB.getInstance().getOrderDocumentIDBHandler().select("transportation = ?",ID).size();
    }*/

    @Override
    public String toString() {
        return String.format("%d(%s)",ID ,ellipsize(Description != null ? Description : "",30));
    }

    private final static String NON_THIN = "[^iIl1\\.,']";
    private static int textWidth(String str) {
        return (int) (str.length() - str.replaceAll(NON_THIN, "").length() / 2);
    }
    public static String ellipsize(String text, int max) {

        if (textWidth(text) <= max)
            return text;

        // Start by chopping off at the word before max
        // This is an over-approximation due to thin-characters...
        int end = text.lastIndexOf(' ', max - 3);

        // Just one long word. Chop it off.
        if (end == -1)
            return text.substring(0, max-3) + "...";

        // Step forward as long as textWidth allows.
        int newEnd = end;
        do {
            end = newEnd;
            newEnd = text.indexOf(' ', end + 1);

            // No more spaces.
            if (newEnd == -1)
                newEnd = text.length();

        } while (textWidth(text.substring(0, newEnd) + "...") < max);

        return text.substring(0, end) + "...";
    }

    @Hide
    public void setArrived(boolean arrived) {
        this.arrived = arrived ? 1 : 0;
    }

    public boolean isArrived() {
        return arrived == 1;
    }
}
