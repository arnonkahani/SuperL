package com.Transpotation.Models;

import com.Common.DB.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDocument {
    private Integer ID = null;
    private Place Source;
    private Place Destination;
    private Transportation transportation;

    public OrderDocument() {
    }

    public OrderDocument(int ID, Place Source , Place Destination, Transportation transportation) {
        this.ID = ID;
        this.Destination =Destination ;
        this.Source= Source ;
        this.transportation = transportation;
    }

    public Integer getID() {
        return ID;
    }

    public Place getSource() {
        return Source;
    }

    public void setSource(Place source) throws ValidationException {
        if(source.getShipmentArea()  == null)
            throw new ValidationException("Cannot set a source without an area");
        Source = source;
    }

    public Place getDestination() {
        return Destination;
    }

    public void setDestination(Place destination) throws ValidationException {
        if(destination.getShipmentArea()  == null)
            throw new ValidationException("Cannot set a destination without an area");
        Destination = destination;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) throws ValidationException, SQLException, ClassNotFoundException {
        if(transportation!= null && !transportation.getArea().equals(Source.getShipmentArea()))
            throw new ValidationException("Areas do not match");
        if(transportation!= null && transportation.getTruck() != null && transportation.getTotalWeight() + getTotalWeight() > (transportation.getTruck().getMaxWeight() - transportation.getTruck().getNetWeight()))
            throw new ValidationException("Truck cannot carry more weight");
        this.transportation = transportation;
    }

    public double getTotalWeight() throws SQLException, ClassNotFoundException {
        PreparedStatement p = DB.getInstance().getConnection()
                .prepareStatement("SELECT SUM(weight) FROM Product WHERE orderID = ?");
        p.setInt(1,ID);
        ResultSet set = p.executeQuery();
        return set.getDouble("SUM(weight)");
    }

    public int getNumberOfProducts() throws SQLException, ClassNotFoundException {
        PreparedStatement p = DB.getInstance().getConnection()
                .prepareStatement("SELECT COUNT(weight) FROM Product WHERE orderID = ?");
        p.setInt(1,ID);
        ResultSet set = p.executeQuery();
        return set.getInt("COUNT(weight)");
    }

    @Override
    public String toString() {
        return ID+"";
    }
}
