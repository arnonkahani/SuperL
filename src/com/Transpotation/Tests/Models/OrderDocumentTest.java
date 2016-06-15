package com.Transpotation.Tests.Models;

import com.Transpotation.Models.OrderDocument;
import com.Transpotation.Models.Place;
import com.Transpotation.Models.ValidationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class OrderDocumentTest {
    OrderDocument orderDocument;

    @Before
    public void setup(){
        orderDocument = new OrderDocument(1,null,null,null);
    }

    @Test
    public void testInvalidSource(){
        try {
            orderDocument.setSource(new Place(",",null,"",""));
            fail();
        } catch (ValidationException e) {
        }
    }

    @Test
    public void testInvalidDestination(){
        try {
            orderDocument.setDestination(new Place(",",null,"",""));
            fail();
        } catch (ValidationException e) {
        }
    }
}
