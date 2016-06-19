package com.SupplierStorage;

import com.Common.ISupplierStorage;
import com.Common.Models.Order;
import com.SupplierStorage.BE.OrderProduct;
import com.SupplierStorage.PL.ViewController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by arnon on 15/06/2016.
 */
public class SupplierStorage implements ISupplierStorage {

    static SupplierStorage instance;
    private ViewController vc;

    public static SupplierStorage getInstance() throws InternalError{
        if(instance == null)
            instance = new SupplierStorage();
        return instance;
    }
    public SupplierStorage(){
        try {
            ViewController vc = new ViewController(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void showSupplier() {
        vc.showSupplier();
    }

    @Override
    public void showStorage() {
        vc.showStorage();
    }

    @Override
    public void getSupply(ArrayList<OrderProduct> products) {
        vc.getSupply(products);
    }


    @Override
    public Order getOrder(String id) {
        return null;
    }
}
