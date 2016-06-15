package com.Transpotation.DB;

import com.Common.DB.DBHandler;
import com.Common.DB.IDBHandler;
import com.Transpotation.Models.Area;
import com.Transpotation.Models.Place;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaceDBHandler extends DBHandler<Place> implements IDBHandler<Place> {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS Place (" +
            "Address TEXT PRIMARY KEY NOT NULL," +
            "ShipmentArea INTEGER," +
            "PhoneNumber TEXT," +
            "ContactPerson TEXT," +
            "FOREIGN KEY(ShipmentArea) REFERENCES Area(areaID) ON DELETE CASCADE ON UPDATE CASCADE " +
            ");";

    IDBHandler<Area> areaDBHandler;
    public PlaceDBHandler(Connection connection, IDBHandler<Area> areaDBHandler) throws SQLException {
        super(connection);
        this.areaDBHandler = areaDBHandler;
    }

    @Override
    protected void createTableIfNeeded() throws SQLException {
        connection.createStatement().execute(CREATE);
    }

    @Override
    protected Place readRow(ResultSet set) throws SQLException {
        return new Place(
                set.getString("Address"),
                areaDBHandler.get(set.getInt("ShipmentArea")),
                set.getString("PhoneNumber"),
                set.getString("ContactPerson")
        );
    }

    @Override
    protected String getTableName() {
        return "Place";
    }

    @Override
    protected Object getKeyValue(Place o) {
        return o.getAddress();
    }

    @Override
    protected String getKeyName() {
        return "Address";
    }

    @Override
    protected Object[] getColumnValues(Place o) {
        return new Object[]{
                o.getAddress(),
                o.getShipmentArea() != null ? o.getShipmentArea().getAreaID() : null,
                o.getPhoneNumber(),
                o.getContactPerson()
        };
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{
                "Address",
                "ShipmentArea",
                "PhoneNumber",
                "ContactPerson"
        };
    }
}
