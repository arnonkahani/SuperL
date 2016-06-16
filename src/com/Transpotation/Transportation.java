package com.Transpotation;

import com.Common.ITransportation;
import com.Common.Models.Order;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Date;

public class Transportation implements ITransportation {
    public static Transportation instance;

    public static Transportation getInstance() {
        if(instance == null)
            instance = new Transportation();
        return instance;
    }

    @Override
    public void makeTransportation(Date time, ArrayList<Order> orders) {
        throw new NotImplementedException();
    }
}
