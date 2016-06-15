package com.Transpotation.DB;

import com.Common.Models.LicenseType;
import com.Common.DB.DBHandler;
import com.Common.DB.IDBHandler;
import com.Transpotation.Models.Truck;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TruckDBHandler extends DBHandler<Truck> implements IDBHandler<Truck> {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS Truck (" +
            "LicenceNumber TEXT PRIMARY KEY NOT NULL," +
            "LicenceType TEXT," +
            "Model TEXT," +
            "Color TEXT," +
            "MaxWeight REAL," +
            "NetWeight REAL" +
            ");";

    public TruckDBHandler(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected void createTableIfNeeded() throws SQLException {
        connection.createStatement().execute(CREATE);
    }

    @Override
    protected Truck readRow(ResultSet set) throws SQLException {
        return new Truck(
                LicenseType.valueOf(set.getString("LicenceType")),
                set.getString("LicenceNumber"),
                set.getString("Model"),
                set.getString("Color"),
                set.getDouble("NetWeight"),
                set.getDouble("MaxWeight"));
    }

    @Override
    protected String getTableName() {
        return "Truck";
    }

    @Override
    protected Object getKeyValue(Truck o) {
        return o.getLicenseNumber();
    }

    @Override
    protected String getKeyName() {
        return "LicenceNumber";
    }

    @Override
    protected Object[] getColumnValues(Truck o) {
        return new Object[]{
                o.getLicenseNumber(),
                o.getLicenseType() != null ? o.getLicenseType().toString() : null,
                o.getModel(),
                o.getColor(),
                o.getMaxWeight(),
                o.getNetWeight()
        };
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{
                "LicenceNumber",
                "LicenceType",
                "Model",
                "Color",
                "MaxWeight",
                "NetWeight"
        };
    }
}
