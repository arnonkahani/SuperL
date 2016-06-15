package com.Workers.Objects;


import java.time.DateTimeException;

public class Shift extends WorkerSchedule {
	Date _date;
	Worker.JobEnum _job;


	public Shift(String day, TypeEnum type, Date date, Worker.JobEnum job) throws DateTimeException, NotValidDayException {
		super(day, type);
		_date = date;
		_job = job;

	}
	
	@Override
	public String toString(){
		return " Day = " +_day+"| Type= " + _type.toString() + "| date = " + _date.toString();
	}
	
	public Date get_date(){
		return _date;
	}
	
	public void setJob(Worker.JobEnum job){
		_job = job;
	}
	
	public Worker.JobEnum getJob(){
		return _job;
	}
}
