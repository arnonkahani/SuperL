package com.Transpotation.Tests.Models;

import com.Transpotation.Models.Product;
import com.Transpotation.Models.ValidationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class ProductTest {
    Product product;

    @Before
    public void setup(){
        product = new Product(1,"","",2,null);
    }

    @Test
    public void testNegativeWeight(){
        try {
            product.setWeight(-1);
            fail();
        } catch (ValidationException e) {

        }
    }
}
