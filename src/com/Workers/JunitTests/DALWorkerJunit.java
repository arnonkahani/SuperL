package com.Workers.JunitTests;
import com.Workers.DatabaseObjects.DAL;
import com.Workers.Objects.Date;
import com.Workers.Objects.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DALWorkerJunit {
	

	DAL dal = null;
	Worker worker = null;
	String id = "123456789";
	String bankNO = "123";
	String name = "roni";
	LinkedList<Worker.JobEnum> abilities = new LinkedList<>();
	@Before
	public void before(){
		try {
			dal = DAL.getDAL();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Date d;
		try {
			d = new Date(3, 1, 1999);
			assertEquals(true, dal.addWorker(id, bankNO, d, name));
		} catch (Exception e) {
			fail(e.getMessage());

		}


		abilities.add(Worker.JobEnum.Driver);
		abilities.add(Worker.JobEnum.Cleaner);
		assertEquals(true, dal.addJobsByID(id, abilities));
	}


	@After
	public void after(){
        assertEquals(true, dal.deleteWorkerByID(id));
	}
	

	@Test
	public void addRemoveWorkerTest() {
		Date d;
		String ID = "987654321";
		String bank = "987";
		try {
			d = new Date(3, 1, 1999);
			assertEquals(true, dal.addWorker(ID, bank, d, name));
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertEquals(true, dal.deleteWorkerByID(ID));
		assertEquals(false, dal.isWorkerExistByID(ID));

	}

	@Test
	public void updateWorkerTest() {
		String bank = "321";
		assertEquals(true, dal.updateBankNOByID(id, bank));

	}

	@Test
	public void getWorkerTest() {
		Worker w = dal.getWorkerByID(id);
		assertEquals(id, w.getID());
		assertEquals(bankNO, w.getBankNO());
		assertEquals(name, w.getName());
		for(Worker.JobEnum je : w.getAbilities()){
			if(!abilities.contains(je))
				fail("no much job found");
		}


	}

}
