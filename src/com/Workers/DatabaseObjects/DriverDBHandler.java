package com.Workers.DatabaseObjects;

import com.Common.Models.Driver;
import com.Common.Models.LicenseType;
import com.Common.DB.DBHandler;
import com.Common.DB.IDBHandler;
import com.Workers.Objects.Worker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDBHandler extends DBHandler<Driver> {
    IDBHandler<Worker> _workerDBHandler;
    public DriverDBHandler(Connection connection, IDBHandler<Worker> workerDBHandler) throws SQLException {
        super(connection);
        _workerDBHandler = workerDBHandler;
    }

    @Override
    protected void createTableIfNeeded() throws SQLException {
        connection.createStatement().execute(DALInitiateConstants.CREATE_Driver);
    }

    @Override
    protected Driver readRow(ResultSet set) throws SQLException {
        return new Driver(
                new Worker(_workerDBHandler.get(set.getString("WID"))),
                LicenseType.valueOf(set.getString("DriverLicence"))
        );
    }

    @Override
    protected String getTableName() {
        return DALInitiateConstants.TABLE_Driver;
    }

    @Override
    protected Object getKeyValue(Driver o) {
        return o.getID();
    }

    @Override
    protected String getKeyName() {
        return "WID";
    }

    @Override
    protected Object[] getColumnValues(Driver o) {
        return new Object[]{
                o.getID(),
                o.getDriverLicenseType().name()
        };
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{
                "WID",
                "DriverLicence"
        };
    }
}
