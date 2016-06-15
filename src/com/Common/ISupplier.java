package com.Common;

import java.sql.Connection;

/**
 * Created by arnon on 11/06/2016.
 */
public interface ISupplier {


    /**
     * Presents a supplier menu.
     *
     * @param
     * @param
     * @return
     */
    void show();

    /**
     * Presents a supplier menu.
     *
     * @param first_time is used to indicate the first time the system is loaded in order to create the tables
     * @param c is the connection for the DB
     * @return
     */
    void loadDB(boolean first_time,Connection c);

}
