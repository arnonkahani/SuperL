package com.Common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Common.Models.*;
import com.Transpotation.Models.Area;
import com.Transpotation.Models.Place;
import com.Transpotation.Models.ValidationException;
import com.Transpotation.Transportation;

/**
 * Exposed interface of the transportation module for other modules
 * implemented by Transportation in package com.SuperLi.Transportation
 */
public interface ITransportation {

    /**
     * Create a transportation.s
     */
    void makeTransportation(Date time,ArrayList<Order> orders) throws ClassNotFoundException, SQLException, ValidationException, Transportation.NoTrucksAvailable, Transportation.NoDriversAvailable;

    /**
    * return a list of all places
     * * */
    List<Place> getAllPlaces();

    /**
     * return a list of all areas
     * * */
    List<Area> getAllAreas();


    /**
     * Create a place for supplier
     *
     */
    void addSupplierPlace(Place place);
}
