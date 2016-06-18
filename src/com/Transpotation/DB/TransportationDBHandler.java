package com.Transpotation.DB;

import com.Common.Models.Driver;
import com.Common.DB.DBHandler;
import com.Common.DB.IDBHandler;
import com.Transpotation.Models.Area;
import com.Transpotation.Models.Transportation;
import com.Transpotation.Models.Truck;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransportationDBHandler extends DBHandler<Transportation> {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS Transportation (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "StartTime DATETIME," +
            "EndTime DATETIME," +
            "Description TEXT," +
            "truck TEXT," +
            "driver TEXT," +
            "area INTEGER," +
            "arrived INTEGER," +
            "FOREIGN KEY(driver) REFERENCES Driver(WID) ON DELETE RESTRICT ON UPDATE CASCADE ," +
            "FOREIGN KEY(truck) REFERENCES Truck(LicenceNumber) ON DELETE RESTRICT ON UPDATE CASCADE ," +
            "FOREIGN KEY(area) REFERENCES Area(areaID) ON DELETE RESTRICT ON UPDATE CASCADE " +
            ");";

    IDBHandler<Truck> truckIDBHandler;
    IDBHandler<Driver> driverIDBHandler;
    IDBHandler<Area> areaIDBHandler;

    public TransportationDBHandler(Connection connection,
                                   IDBHandler<Truck> truckIDBHandler,
                                   IDBHandler<Driver> driverIDBHandler,
                                   IDBHandler<Area> areaIDBHandler) throws SQLException {
        super(connection);
        this.truckIDBHandler = truckIDBHandler;
        this.driverIDBHandler = driverIDBHandler;
        this.areaIDBHandler = areaIDBHandler;
    }

    @Override
    protected void createTableIfNeeded() throws SQLException {
        connection.createStatement().execute(CREATE);
    }

    @Override
    protected Transportation readRow(ResultSet set) throws SQLException {
        Transportation t = new Transportation(
                set.getInt("ID"),
                set.getString("Description"),
                set.getDate("StartTime"),
                set.getDate("EndTime"),
                truckIDBHandler.get(set.getString("truck")),
                driverIDBHandler.get(set.getString("driver")),
                areaIDBHandler.get(set.getString("area"))
        );
        t.setArrived(set.getInt("arrived") == 1);

        return t;
    }

    @Override
    protected String getTableName() {
        return "Transportation";
    }

    @Override
    protected Object getKeyValue(Transportation o) {
        return o.getID();
    }

    @Override
    protected String getKeyName() {
        return "ID";
    }

    @Override
    protected Object[] getColumnValues(Transportation o) {
        return new Object[]{
                o.getID(),
                o.getStartTime(),
                o.getEndTime(),
                o.getDescription(),
                o.getTruck() != null ? o.getTruck().getLicenseNumber() : null,
                o.getDriver() != null ? o.getDriver().getID() : null,
                o.getArea() != null ? o.getArea().getAreaID() : null,
                o.isArrived() ? 1 : 0
        };
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{
                "ID",
                "StartTime",
                "EndTime",
                "Description",
                "truck",
                "driver",
                "area",
                "arrived",
        };
    }
}
