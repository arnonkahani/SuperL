package com.Workers.DatabaseObjects;

import com.Common.DB.DBHandler;
import com.Workers.Objects.Worker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Barakmen on 5/5/2016.
 */
public class WorkerDBHandler extends DBHandler<Worker> {

    public WorkerDBHandler(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected void createTableIfNeeded() throws SQLException {
        connection.createStatement().execute(DALInitiateConstants.CREATE_Worker);
    }

    @Override
    protected Worker readRow(ResultSet set) throws SQLException {
       Worker w = new Worker(
               set.getString("ID"),
               set.getString("BankNO"),
               set.getString("EmploymentTerms"),
               set.getString("EmploymentDate"),
               set.getString("Name"),
               null
       );
        return w;
    }

    @Override
    protected String getTableName() {
        return DALInitiateConstants.TABLE_Worker;
    }

    @Override
    protected Object getKeyValue(Worker o) {
        return o.ID;
    }

    @Override
    protected String getKeyName() {
        return "ID";
    }

    @Override
    protected Object[] getColumnValues(Worker o) {
        return new Object[]{
                o.getID(),
                o.getBankNO(),
                o.getEmpDate(),
                o.getEmpTerms(),
                o.getName()
        };
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{
                "ID",
               "BankNO",
                "EmploymentDate",
               "EmploymentTerms",
               "Name"
        };
    }
}
