package com.Common;

import com.Common.Models.Order;
import com.SupplierStorage.BE.OrderProduct;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by arnon on 11/06/2016.
 */
public interface ISupplierStorage {


    /**
     * Presents a supplier menu.
     *
     * @param
     * @param
     * @return
     */
    void showSupplier();

    /**
     * Presents a supplier menu.
     *
     * @param
     * @param
     * @return
     */
    void showStorage();


    /**
     * Receive a supply to storage.
     *
     * @param products
     * @return
     */
    void getSupply(ArrayList<OrderProduct> products);


    /**
     * Presents a supplier menu.
     *
     * @param first_time is used to indicate the first time the system is loaded in order to create the tables
     * @param c is the connection for the DB
     * @return
     */
    void initiateSupplierStorage(boolean first_time,Connection c);


    /**
     * Presents a supplier menu.
     *
     * @param
     * @return
     */
    Order getOrder(String id);



}
