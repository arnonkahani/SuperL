package com.SupplierStorage;

import com.Common.ISupplierStorage;
import com.SupplierStorage.BE.OrderProduct;
import com.SupplierStorage.PL.ViewController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by arnon on 15/06/2016.
 */
public class SupplierStorage implements ISupplierStorage {

    ViewController vc;

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
    public void initiateSupplierStorage(boolean first_time, Connection c) {

        try {
            vc = new ViewController(first_time,c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
