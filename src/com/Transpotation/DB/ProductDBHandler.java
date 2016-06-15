package com.Transpotation.DB;

import com.Common.DB.DBHandler;
import com.Common.DB.IDBHandler;
import com.Transpotation.Models.OrderDocument;
import com.Transpotation.Models.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDBHandler extends DBHandler<Product> {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS Product (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "name TEXT," +
            "description TEXT," +
            "weight REAL," +
            "orderID INTEGER," +
            "FOREIGN KEY(orderID) REFERENCES OrderDocument(ID) ON DELETE CASCADE ON UPDATE CASCADE " +
            ");";

    IDBHandler<OrderDocument> orderDocumentIDBHandler;
    public ProductDBHandler(Connection connection, IDBHandler<OrderDocument> orderDocumentIDBHandler) throws SQLException {
        super(connection);
        this.orderDocumentIDBHandler = orderDocumentIDBHandler;
    }

    @Override
    protected void createTableIfNeeded() throws SQLException {
        connection.createStatement().execute(CREATE);
    }

    @Override
    protected Product readRow(ResultSet set) throws SQLException {
        return new Product(
                set.getInt("ID"),
                set.getString("name"),
                set.getString("description"),
                set.getDouble("weight"),
                orderDocumentIDBHandler.get(set.getInt("orderID"))
        );
    }

    @Override
    protected String getTableName() {
        return "Product";
    }

    @Override
    protected Object getKeyValue(Product o) {
        return o.getID();
    }

    @Override
    protected String getKeyName() {
        return "ID";
    }

    @Override
    protected Object[] getColumnValues(Product o) {
        return new Object[]{
                o.getID(),
                o.getName(),
                o.getDescription(),
                o.getWeight(),
                o.getOrder() != null ? o.getOrder().getID() : null
        };
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{
                "ID",
                "name",
                "description",
                "weight",
                "orderID"
        };
    }
}
