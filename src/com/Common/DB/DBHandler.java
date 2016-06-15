package com.Common.DB;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * implements IDBHandler by injecting subclass's data to premade SQL queries
 *
 * @param <T> : type of object this handler is responsible for
 */
public abstract class DBHandler<T> implements IDBHandler<T>{
    protected Connection connection;

    private static final String SELECT = "SELECT * FROM %s WHERE ";
    private static final String INSERT = "INSERT INTO %s (%s) VALUES (%s);";
    private static final String DELETE = "DELETE FROM %s WHERE ";
    private static final String UPDATE = "UPDATE %s SET %s WHERE ";

    public DBHandler(Connection connection)  {
        this.connection = connection;
        try{
            createTableIfNeeded();
        }
        catch (SQLException e){
            throw new UnexpectedSQLException(e);
        }

    }

    private Object[] concat(Object[]... arrays) {
        int length = 0;
        for (Object[] array : arrays) {
            length += array.length;
        }
        Object[] result = new Object[length];
        int pos = 0;
        for (Object[] array : arrays) {
            for (Object element : array) {
                result[pos] = element;
                pos++;
            }
        }
        return result;
    }

    protected abstract void createTableIfNeeded() throws SQLException;
    protected abstract T readRow(ResultSet set) throws SQLException;
    protected abstract String getTableName();
    protected abstract Object getKeyValue(T o);
    protected abstract String getKeyName();
    protected abstract Object[] getColumnValues(T o);
    protected abstract String[] getColumnNames();

    public List<T> select(String where, Object... args){
        try {
            String select = String.format(SELECT + where,getTableName());
            return query(select,args);
        }
        catch (SQLException e){
            throw new UnexpectedSQLException(e);
        }

    }
    public int insert(T obj)  {
        try {
            String[] question = new String[getColumnNames().length];
            for(int i = 0 ; i < question.length; i ++)
                question[i] = "?";
            String insert = String.format(INSERT,getTableName(),String.join(",",getColumnNames()),String.join(",",question));
            return executeUpdate(insert,getColumnValues(obj));
        }
        catch (SQLException e){
            if(e.getErrorCode() == 19)
                throw new AlreadyExistsException();
            else
                throw new UnexpectedSQLException(e);
        }
    }
    public int update(T obj, String where, Object... args)  {
        try{
            String[] c = getColumnNames();
            for(int i = 0 ; i < c.length; i ++)
                c[i] += " = ?";
            String update = String.format(UPDATE + where,getTableName(),String.join(",",c));
            Object[] joined = concat(getColumnValues(obj),args);
            return executeUpdate(update,joined);
        }
        catch (SQLException e){
            if(e.getErrorCode() == 19)
                throw new UsedException();
            else
                throw new UnexpectedSQLException(e);
        }
    }

    public int delete(String where, Object... args)  {
        try{
            String delete = String.format(DELETE + where,getTableName());
            return executeUpdate(delete,args);
        }
        catch (SQLException e){
            if(e.getErrorCode() == 19)
                throw new UsedException();
            else
                throw new UnexpectedSQLException(e);
        }
    }

    public T get(Object key) {
        new SQLException().getErrorCode();
        List<T> l = select(getKeyName() + " = ?",key);
        if(l.size() > 0)
            return l.get(0);
        else
            return null;
    }
    public List<T> all() {
        return select("1 = 1");
    }
    public void update(T obj)  {
        update(obj, getKeyName() + " = ?", getKeyValue(obj));
    }
    public void delete(String id)  {
        delete(getKeyName() + " = ?",id);
    }
    public List<T> query(String query, Object... args) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for(int i = 0 ; i < args.length ; i++)
            statement.setObject(i+1,args[i]);
        ResultSet set = statement.executeQuery();
        LinkedList<T> l = new LinkedList<>();
        while (set.next())
            l.add(readRow(set));
        return l;
    }
    public int executeUpdate(String query, Object... args) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for(int i = 0 ; i < args.length ; i++)
            statement.setObject(i+1,args[i]);
        return statement.executeUpdate();
    }
    public boolean execute(String query, Object... args) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        for(int i = 0 ; i < args.length ; i++)
            statement.setObject(i+1,args[i]);
        return statement.execute();
    }
}
