package com.Transpotation.Models;

import com.Common.DB.DB;
import com.Common.Models.Driver;

import java.sql.SQLException;
import java.util.Date;
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

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) throws ValidationException {
        if(startTime.getTime() < new Date().getTime())
            throw new ValidationException("Start time must be in the future");
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
        if(StartTime != null && endTime.before(StartTime))
            throw new ValidationException("End time must be after Start time");
        /*if(!Workers.getInstance().isStockWorkerAvailable(endTime))
            throw new ValidationException("No Stock Worker available at that time");*/
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
        if(driver != null && driver.getDriverLicenseType() != truck.getLicenseType())
            throw new ValidationException("Licence Type does not match with attached driver's Licence Type");
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

    public double getTotalWeight() throws SQLException, ClassNotFoundException {
        List<OrderDocument> l = DB.getInstance().getOrderDocumentIDBHandler().select("transportation = ?",ID);
        double sum = 0;
        for(OrderDocument d : l)
            sum += d.getTotalWeight();
        return sum;
    }

    public int getNumberOfOrders() throws SQLException, ClassNotFoundException {
        return DB.getInstance().getOrderDocumentIDBHandler().select("transportation = ?",ID).size();
    }

    @Override
    public String toString() {
        return String.format("%d(%s)",ID ,ellipsize(Description,30));
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

    public void setArrived(boolean arrived) {
        this.arrived = arrived ? 1 : 0;
    }

    public boolean isArrived() {
        return arrived == 1;
    }
}
