package com.Workers.Objects;

import java.util.LinkedList;

public class User extends Worker {
	public String userName;
	public String password;

	public User(String ID, String Name, String BankNO, String EmpTerms, String EmpDate, LinkedList<JobEnum> abilities, String userName, String password){
		super(ID, Name, BankNO, EmpTerms, EmpDate, abilities);
		this.userName = userName;
		this.password = password;
	}


	public User(Worker worker, String userName, String password){
		super(worker.ID, worker.Name, worker.BankNO, worker.EmpTerms, worker.EmpDate, worker.abilities);
		this.userName = userName;

		this.password = password;
	}


	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
}
