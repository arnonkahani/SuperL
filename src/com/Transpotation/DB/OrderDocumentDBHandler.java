package com.Transpotation.DB;

import com.Common.DB.DBHandler;
import com.Common.DB.IDBHandler;
import com.Transpotation.Models.OrderDocument;
import com.Transpotation.Models.Place;
import com.Transpotation.Models.Transportation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDocumentDBHandler extends DBHandler<OrderDocument> {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS OrderDocument (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            "orderID INTEGER,"+
            "Source TEXT," +
            "Destination TEXT," +
            "transportation INTEGER," +
            "FOREIGN KEY(orderID) REFERENCES Orders(ID) ON DELETE CASCADE ON UPDATE CASCADE ," +
            "FOREIGN KEY(Source) REFERENCES Place(Address) ON DELETE RESTRICT ON UPDATE CASCADE ," +
            "FOREIGN KEY(Destination) REFERENCES Place(Address) ON DELETE RESTRICT ON UPDATE CASCADE ," +
            "FOREIGN KEY(transportation) REFERENCES Transportation(ID) ON DELETE SET NULL ON UPDATE CASCADE " +
            ");";

    IDBHandler<Place> placeIDBHandler;
    IDBHandler<Transportation> transportationIDBHandler;
    public OrderDocumentDBHandler(Connection connection, IDBHandler<Place> placeIDBHandler, IDBHandler<Transportation> transportationIDBHandler) throws SQLException {
        super(connection);
        this.placeIDBHandler = placeIDBHandler;
        this.transportationIDBHandler = transportationIDBHandler;
    }

    @Override
    protected void createTableIfNeeded() throws SQLException {
        connection.createStatement().execute(CREATE);
    }

    @Override
    protected OrderDocument readRow(ResultSet set) throws SQLException {
        OrderDocument d = new OrderDocument(
                set.getInt("ID"),
                placeIDBHandler.get(set.getString("Source")),
                set.getString("Destination") != null ? placeIDBHandler.get(set.getString("Destination")) : Place.MAIN_BRANCH,
                transportationIDBHandler.get(set.getString("transportation"))
        );
        d.setOrderID(set.getInt("orderID"));
        return d;
    }

    @Override
    protected String getTableName() {
        return "OrderDocument";
    }

    @Override
    protected Object getKeyValue(OrderDocument o) {
        return o.getID();
    }

    @Override
    protected String getKeyName() {
        return "ID";
    }

    @Override
    protected Object[] getColumnValues(OrderDocument o) {
        return new Object[]{
                o.getID(),
                o.getOrderID(),
                o.getSource() != null ? o.getSource().getAddress() : null,
                o.getDestination() != null ? o.getDestination().Address : null,
                o.getTransportation() != null ? o.getTransportation().getID() : null
        };
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{
                "ID",
                "orderID",
                "Source",
                "Destination",
                "transportation"
        };
    }
}
