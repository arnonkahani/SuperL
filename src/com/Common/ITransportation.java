package com.Common;

import com.SupplierStorage.BE.OrderProduct;

import java.util.ArrayList;
import java.util.Date;
import com.Common.Models.*;

/**
 * Exposed interface of the transportation module for other modules
 * implemented by Transportation in package com.SuperLi.Transportation
 */
public interface ITransportation {

    /**
     * Create a transportation.
     *
     * @param  time if null, ignore this requirement
     * @param  orders if null, ignore this requirement
     */
    void makeTransportation(Date time,ArrayList<Order> orders);


}
