package com.Common;

import com.SupplierStorage.BE.OrderProduct;

import java.util.ArrayList;

/**
 * Created by arnon on 11/06/2016.
 */
public interface IStorage {

    /**
     * Presents a storage menu.
     *
     * @param
     * @param
     * @return
     */
    void show();



    /**
     * Receive a supply to storage.
     *
     * @param products
     * @return
     */
    void getSupply(ArrayList<OrderProduct> products);


}
