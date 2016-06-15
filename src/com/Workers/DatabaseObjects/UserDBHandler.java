package com.Workers.DatabaseObjects;

import com.Common.DB.DBHandler;
import com.Common.DB.IDBHandler;
import com.Workers.Objects.User;
import com.Workers.Objects.Worker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Barakmen on 5/5/2016.
 */
public class UserDBHandler extends DBHandler<User> {

    IDBHandler<Worker> _workerDBHandler;
    public UserDBHandler(Connection connection, IDBHandler<Worker> workerDBHandler) throws SQLException {
        super(connection);
        _workerDBHandler = workerDBHandler;
    }

    @Override
    protected void createTableIfNeeded() throws SQLException {
        connection.createStatement().execute(DALInitiateConstants.CREATE_User);
    }

    @Override
    protected User readRow(ResultSet set) throws SQLException {
        return new User(
                new Worker(_workerDBHandler.get(set.getString("WID"))),
                set.getString("Username"),
                set.getString("Password")
        );
    }

    @Override
    protected String getTableName() {
        return DALInitiateConstants.TABLE_User;
    }

    @Override
    protected Object getKeyValue(User o) {
        return o.getID();
    }

    @Override
    protected String getKeyName() {
        return "WID";
    }

    @Override
    protected Object[] getColumnValues(User o) {
        return new Object[]{
                o.getID(),
                o.getUserName(),
                o.getPassword()
        };
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{
                "WID",
                "Username",
                "Password"
        };
    }
}