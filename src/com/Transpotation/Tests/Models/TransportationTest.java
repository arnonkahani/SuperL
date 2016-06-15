package com.Transpotation.Tests.Models;

import com.Transpotation.Models.Transportation;
import com.Transpotation.Models.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.fail;

public class TransportationTest {

    Transportation transportation;

    @Before
    public void setup(){
        transportation = new Transportation(1,"desc",null,null,null,null,null);
    }

    @Test
    public void testPastStartTime(){
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 1988);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            transportation.setStartTime(cal.getTime());
            fail();
        } catch (ValidationException e) {

        }
    }

    @Test
    public void testEndTimeBeforeStartTime(){
        try {
            transportation.setStartTime(new Date(2016,12,12));
            transportation.setEndTime(new Date(2015,12,12));
            fail();
        } catch (ValidationException e) {

        }
    }

}
