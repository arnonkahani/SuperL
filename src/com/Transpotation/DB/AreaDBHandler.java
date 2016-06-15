package com.Transpotation.DB;

import com.Common.DB.DBHandler;
import com.Transpotation.Models.Area;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AreaDBHandler extends DBHandler<Area> {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS Area (" +
            "areaID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "name TEXT" +
            ");";

    public AreaDBHandler(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected void createTableIfNeeded() throws SQLException {
        connection.createStatement().execute(CREATE);
    }

    @Override
    protected Area readRow(ResultSet set) throws SQLException {
        return new Area(
                set.getString("name"),
                set.getInt("areaID")
        );
    }

    @Override
    protected String getTableName() {
        return "Area";
    }

    @Override
    protected Object getKeyValue(Area o) {
        return o.getAreaID();
    }

    @Override
    protected String getKeyName() {
        return "areaID";
    }

    @Override
    protected Object[] getColumnValues(Area o) {
        return new Object[]{
                o.getAreaID(),
                o.getName(),
        };
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{
                "areaID",
                "name",
        };
    }
}
