package com.Workers.JunitTests;

import com.Workers.DatabaseObjects.DAL;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class ConnectionTest {

	@Test
	public void test() {
		DAL dal = null;
		try {
			 dal = DAL.getDAL();
		} catch (SQLException e) {
			fail("connection Error");
		} catch (ClassNotFoundException e) {
			fail("connection Error");
		}

		assertNotNull(dal);
		assertNotNull(dal.getConnection());
	}

}
