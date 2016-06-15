package com.Workers.DatabaseObjects;

import com.Common.DB.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Barakmen on 5/5/2016.
 */
public class GeneralSQLiteQuaries {


    private static Connection connection;

    static {
        try {
            connection = DB.getInstance().getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void updateTable(String table, String[] attributesToSet, String[] valuesOfAtribuesToSet,
                                   String[] attributesToWhere, String[] valuesOfAtribuesToWhere) throws SQLException {

        String query = "UPDATE " + table;
        query += " SET "; // BankNO = ? WHERE ID = ?";"
        int numOfAttributes = 0;
        for (String attToSet : attributesToSet) {
            query += attToSet + " = ?";
            numOfAttributes++;
            if (numOfAttributes < attributesToSet.length)
                query += ", ";
        }

        query += " WHERE ";
        numOfAttributes = 0;
        for (String attToWhere : attributesToWhere) {
            query += attToWhere + " = ?";
            numOfAttributes++;
            if (numOfAttributes < attributesToWhere.length)
                query += " AND ";
        }

        PreparedStatement pst = connection.prepareStatement(query);

        // set the corresponding param
        int numInSetString = 1;
        int j = 0;
        for (j = 0; j < valuesOfAtribuesToSet.length; j++) {
            pst.setString(numInSetString, valuesOfAtribuesToSet[j]);
            numInSetString++;
        }

        for (j = 0; j < valuesOfAtribuesToWhere.length; j++) {
            pst.setString(numInSetString, valuesOfAtribuesToWhere[j]);
            numInSetString++;
        }

        // execute the delete statement
        pst.executeUpdate();

    }

    public static void deleteFrom(String table, String[] attributes, String[] valuesOfAtribues) throws SQLException {
        String query = "DELETE FROM " + table;
        query += " WHERE ";
        int numOfAttr = 0;
        for (String attr : attributes) {
            query += attr + " = ?";
            numOfAttr++;
            if (numOfAttr < attributes.length)
                query += " AND ";
        }

        PreparedStatement pst = connection.prepareStatement(query);
        // set the corresponding param
        for (int i = 1; i <= valuesOfAtribues.length; i++) {
            pst.setString(i, valuesOfAtribues[i - 1]);
        }
        // execute the delete statement
        pst.executeUpdate();

    }

    public static boolean deleteListFrom(String table, String[] attributes, String[][] object_values) {
        PreparedStatement pst = null;
        try {
            try {
                String query = "";
                query = "BEGIN;";
                pst = connection.prepareStatement(query);

                pst.execute();

                for (int i = 0; i < object_values.length; i++) {
                    try {
                        deleteFrom(table, attributes, object_values[i]);
                    } catch (SQLException e) {
                        query = "ROLLBACK;";
                        pst = connection.prepareStatement(query);
                        pst.execute();
                        return false;
                    } finally {
                        if (pst != null) {
                            pst.close();
                        }
                    }
                }
                query = "COMMIT;";
                pst = connection.prepareStatement(query);
                pst.execute();
                return true;
            } catch (SQLException e1) {
                return false;
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public static boolean insertListInto(String table, String[] attributes, String[][] object_values) {
        PreparedStatement pst = null;
        try {
            try {
                String query = "";
                query = "BEGIN;";
                pst = connection.prepareStatement(query);

                pst.execute();

                for (int i = 0; i < object_values.length; i++) {
                    try {
                        insertInto(table, attributes, object_values[i]);
                    } catch (SQLException e) {
                        query = "ROLLBACK;";
                        pst = connection.prepareStatement(query);
                        pst.execute();
                        System.out.println(e);
                        return false;
                    } finally {
                        if (pst != null) {
                            pst.close();
                        }
                    }
                }
                query = "COMMIT;";
                pst = connection.prepareStatement(query);
                pst.execute();
                return true;
            } catch (SQLException e1) {
                System.out.println(e1);
                return false;
            } finally {
                if (pst != null) {
                    pst.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public static void insertInto(String table, String[] attributes, String[] values) throws SQLException {

        String query = "";

        query = "INSERT INTO " + table;
        query += "(";
        int endAttributes = 0;
        for (String attr : attributes) {
            query += attr;
            endAttributes++;
            if (endAttributes < attributes.length)
                query += ",";
        }
        query += ")";
        query += "VALUES (";
        int endValues = 0;
        for (String value : values) {
            query += "\"" + value + "\"";
            endValues++;
            if (endValues < attributes.length)
                query += ",";
        }
        query += ");";

        PreparedStatement pst = connection.prepareStatement(query);
        pst.executeUpdate();
        pst.close();

    }

    public static ResultSet selectFrom(String table, String[] attributesToSelect, String[] attributesToWhere, String[] valuesToWhere) throws SQLException {
        String query = "SELECT ";
        int endAttributes = 0;
        for (String attr : attributesToSelect) {
            query += attr;
            endAttributes++;
            if (endAttributes < attributesToSelect.length)
                query += ",";
        }

        query += " FROM " + table;
        query += " WHERE ";
        int numOfAttributes = 0;
        for (String attToWhere : attributesToWhere) {
            query += attToWhere + " = ?";
            numOfAttributes++;
            if (numOfAttributes < attributesToWhere.length)
                query += " AND ";
        }

        PreparedStatement pst = connection.prepareStatement(query);

        int numInSetString = 1;
        int j = 0;
        for (j = 0; j < valuesToWhere.length; j++) {
            pst.setString(numInSetString, valuesToWhere[j]);
            numInSetString++;
        }

        ResultSet rs = pst.executeQuery();

        return rs;
    }

    public static boolean isExists(String table, String[] primaryKeys, String... keysValues) {
        try {

            String query = "SELECT * FROM " + table + " WHERE ";
            int numOfAttributes = 0;
            for (String attToWhere : primaryKeys) {
                query += attToWhere + " = ?";
                numOfAttributes++;
                if (numOfAttributes < primaryKeys.length)
                    query += " AND ";
            }
            PreparedStatement pst = connection.prepareStatement(query);

            int numInSetString = 1;
            int j = 0;
            for (j = 0; j < keysValues.length; j++) {
                pst.setObject(numInSetString, keysValues[j]);
                numInSetString++;
            }

            ResultSet rs = pst.executeQuery();

            int count = 0;
            while (rs.next()) {
                count++;
            }
            pst.close();
            if (count > 0)
                return true;
            return false;
        } catch (SQLException e) {
            return false;
        }
    }
}
