package com.Transpotation.Tests.DB;

import com.Common.Models.LicenseType;
import com.Common.DB.AlreadyExistsException;
import com.Common.DB.IDBHandler;
import com.Transpotation.DB.TruckDBHandler;
import com.Transpotation.Models.Truck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class TruckDBHandlerTest {
    IDBHandler<Truck> handler;
    List<Truck> list;
    Connection connection;
    @Before
    public void setup() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
        handler = new TruckDBHandler(connection);

        list = new LinkedList<>();
        list.add(new Truck(LicenseType.A,"123456789","Mikubifli","Hering red",123d,200d));
        list.add(new Truck(LicenseType.B,"987654321","Bently","Shaved blue",70d,1000d));
        list.add(new Truck(LicenseType.C,"777213478","Limolimo","Earth brown",75d,80d));
        list.add(new Truck(LicenseType.A,"213213546","Fooblydoo","Greyed out Grey",1d,2d));
        list.add(new Truck(LicenseType.B,"654842314","Shuglyablegarg","Mitochondria yellow",5654d,65015d));
        for(Truck t : list)
            handler.insert(t);
    }

    @Test
    public void testNonExistentGet(){
        assertNull(handler.get("2132d3546"));
    }

    @Test
    public void testGet(){
        Truck t = handler.get("777213478");
        assertEquals(t,list.get(2));
    }

    @Test
    public void testSelect(){
        List<Truck> l = handler.select("LicenceType = ? AND NetWeight > ?" , LicenseType.A , 2);
        for(Truck t : list)
            if(t.getLicenseType() == LicenseType.A && t.getNetWeight() > 2)
                assertTrue(l.contains(t));
            else
                assertFalse(l.contains(t));
    }

    @Test
    public void testDelete(){
        handler.delete("LicenceType = ? AND NetWeight > ?" , LicenseType.A , 2);
        for(Truck t : list)
            if(t.getLicenseType() == LicenseType.A && t.getNetWeight() > 2)
                assertNull(handler.get(t.getLicenseNumber()));
            else
                assertNotNull(handler.get(t.getLicenseNumber()));
    }

    @Test
    public void testUpdate(){
        Truck updated = new Truck(LicenseType.B,"123456789","Sumicobi","Hering blue",321d,500d);
        handler.update(updated);
        assertEquals(updated,handler.get("123456789"));
    }

    @Test
    public void testDoubleInsert(){
        try {
            Truck truck = new Truck(LicenseType.A,"123123123","","",5,6);
            handler.insert(truck);
            handler.insert(truck);
            fail();
        } catch (AlreadyExistsException e) {
            return;
        }
    }

    @After
    public void teardown() throws IOException, SQLException {
        connection.close();
        new File("test.db").delete();
    }
}
