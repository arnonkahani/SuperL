package com.Workers.JunitTests;

import com.Workers.Objects.Date;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DateTest {

	@Test
	public void test() {
		Date date1 = null;
		try {
			date1 = new Date(1, 5, 2016);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(date1.toString().compareTo("01-05-2016") == 0);
		assertTrue(date1.toString().compareTo("01-05-2016") == 0);
		
		Date date2 = null;
		try {
			date2 = new Date("01/05/2016");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(date2.toString().compareTo("01-05-2016") == 0);
		assertTrue(date2.toString().compareTo("01-05-2016") == 0);
		
		Date date3 = null;
		Date date4 = null;
		try {
			date3 = new Date("01/05/2016");
			date4 = new Date("01/05/2016");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(date3.toString().compareTo("01-05-2016") == 0);
		assertTrue(date3.toString().compareTo("01-05-2016") == 0);
		assertTrue(date4.toString().compareTo("01-05-2016") == 0);
		assertTrue(date4.toString().compareTo("01-05-2016") == 0);
		assertTrue(date3.toString().compareTo(date3.toString()) == 0);
	}

}
