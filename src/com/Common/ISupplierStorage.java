package com.Common;

import com.Common.Models.Order;
import com.SupplierStorage.BE.OrderProduct;

import java.sql.Connection;
import java.util.ArrayList;

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
     * @param
     * @return
     */
    Order getOrder(String id);

    void sentOrder(String id);
}
