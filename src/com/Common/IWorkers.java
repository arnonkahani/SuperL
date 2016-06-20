package com.Common;

import com.Common.Models.Driver;
import com.Common.Models.LicenseType;
import com.Workers.Objects.Worker;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Exposed interface of the transportation module for other modules
 * implemented by Workers in package com.SuperLi.Workers
 */
public interface IWorkers {

    /**
     * Returns a list of available Drivers at the time specified and with a LicenceType type.
     *
     * @param  type if null, ignore this requirement
     * @param  time if null, ignore this requirement
     * @return      the list of drivers
     */
    List<Driver> availableDrivers(LicenseType type, Date time, boolean isWeekly) throws SQLException, ClassNotFoundException;

    /**
     * @param  time if null, ignore this requirement
     * @return      true if there is a Stock Worker at the time specified
     */
    boolean isStockWorkerAvailable(Date time);

    /**
     * Returns a date of the earliest available transportation from time to time + 7 Days.
     *
     * @param  time if null, ignore this requirement
     * @return      transportation date
     */
    Date getEarliestDeleveryDate(Date time);

    /**
     * Creates driver shifts for set day supplier agreements.
     *
     * @param  dayOfWeeks if null, ignore this requirement
     * @return
     */
    void setWeeklyDeleveryShifts(List<DayOfWeek> dayOfWeeks);

    public LinkedList<Worker.JobEnum> getJobs(String UserName, String Password);
}
