package com.Transpotation.Tests.DB;

import com.Transpotation.DB.AreaDBHandler;
import com.Common.DB.IDBHandler;
import com.Transpotation.Models.Area;
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
import java.util.Objects;

import static org.junit.Assert.*;

public class AreaDBHandlerTest {
    IDBHandler<Area> handler;
    List<Area> list;
    Connection connection;
    @Before
    public void setup() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
        handler = new AreaDBHandler(connection);

        list = new LinkedList<>();
        list.add(new Area("San Francisco",1));
        list.add(new Area("Dallas",2));
        list.add(new Area("California",3));
        list.add(new Area("Texas",4));
        for(Area t : list)
            handler.insert(t);
    }

    @Test
    public void testNonExistentGet(){
        assertNull(handler.get(5));
    }

    @Test
    public void testGet(){
        Area t = handler.get(1);
        assertEquals(t,list.get(0));
    }

    @Test
    public void testSelect(){
        List<Area> l = handler.select("name = ?" , "Dallas");
        for(Area t : list)
            if(Objects.equals(t.getName(), "Dallas"))
                assertTrue(l.contains(t));
            else
                assertFalse(l.contains(t));
    }

    @Test
    public void testDelete(){
        handler.delete("name = ?" , "Dallas");
        for(Area t : list)
            if(Objects.equals(t.getName(), "Dallas"))
                assertNull(handler.get(t.getAreaID()));
            else
                assertNotNull(handler.get(t.getAreaID()));
    }

    @Test
    public void testUpdate(){
        Area updated = new Area("Chicago",2);
        handler.update(updated);
        assertEquals(updated,handler.get(2));
    }

    @After
    public void teardown() throws IOException, SQLException {
        connection.close();
        new File("test.db").delete();
    }
}
