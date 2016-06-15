package com.Workers.JunitTests;
import com.Workers.DatabaseObjects.DAL;
import com.Workers.Objects.Date;
import com.Workers.Objects.Worker;
import com.Workers.Objects.WorkerSchedule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DeleteCascadeJunit {
	DAL dal = null;
	Worker worker = null;
	String id = "123456789";
	String bankNO = "123";
	String name = "roni";

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
	}
	@After
	public void after(){
		assertEquals(true, dal.deleteWorkerByID(id));
	//	dal.getWorkerByID(id);
	}


	@Test
	public void UserOfWorkerCascade(){
		String username = "barak";
		String password = "menacheme";
		assertEquals(true, dal.addUser(id, username, password));
		assertEquals(true, dal.isUserExistByWorkerID(id));
		assertEquals(true, dal.deleteWorkerByID(id));
		assertEquals(false, dal.isUserExistByWorkerID(id));
	}
	@Test
	public void WorkerScheduleCascade(){
		LinkedList<WorkerSchedule> listTimes = new LinkedList<>();
		try {
			listTimes.add(new WorkerSchedule("Sunday", WorkerSchedule.TypeEnum.Evening));
			listTimes.add(new WorkerSchedule("Monday", WorkerSchedule.TypeEnum.Evening));
			assertEquals(true, dal.addWorkerScheduleByID(id, listTimes));
			assertEquals(true, dal.isWorkerSchedulesExistsByID(id));
			assertEquals(true, dal.deleteWorkerByID(id));
			assertEquals(false, dal.isWorkerSchedulesExistsByID(id));
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (WorkerSchedule.NotValidDayException e) {
			fail(e.getMessage());

		}
	}
	
	@Test
	public void WorkerAbilitiesCascade() {
		try {
			LinkedList<Worker.JobEnum> lst = new LinkedList<Worker.JobEnum>();
			lst.add(Worker.JobEnum.ShiftManager);
			assertEquals(true, dal.addJobsByID(id, lst));
			assertEquals(true, dal.isWorkerAbilitiesExistsByID(id));
			assertEquals(true, dal.deleteWorkerByID(id));
			assertEquals(false, dal.isWorkerAbilitiesExistsByID(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
	
		
	}

}
