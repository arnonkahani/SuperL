package com.Common.DB;

import java.sql.SQLException;
import java.util.List;

/**
 * an interface for interacting with the database
 */
public interface IDBHandler<T> {
    List<T> select(String where, Object... args);
    int insert(T obj);
    int update(T obj, String where, Object... args);
    int delete(String where, Object... args);

    T get(Object key);
    List<T> all();
    void update(T obj);
    void delete(String id);
    List<T> query(String query, Object... args) throws SQLException;
    int executeUpdate(String query, Object... args) throws SQLException;
    boolean execute(String query, Object... args) throws SQLException;
    Object getLastGeneratedID();
}
