package com.Workers.Objects;

import java.util.LinkedList;

public class WorkerSchedule {
	protected String _day;
	protected TypeEnum _type;
	
	public enum TypeEnum {
		Morning,
		Evening
	}
	public enum WeekDaysEnum {
		Sunday,
		Monday,
		Tuesday,
		Wednesday,
		Thursday,
		Friday,
		Saturday
	}

	public WorkerSchedule(String day, TypeEnum type) throws NotValidDayException {
		super();

		LinkedList<String> lst = new LinkedList<>();
		lst.add(WeekDaysEnum.Sunday.name());
		lst.add(WeekDaysEnum.Monday.name());
		lst.add(WeekDaysEnum.Tuesday.name());
		lst.add(WeekDaysEnum.Wednesday.name());
		lst.add(WeekDaysEnum.Thursday.name());
		lst.add(WeekDaysEnum.Friday.name());
		lst.add(WeekDaysEnum.Saturday.name());
		_type = type;
		
		if(lst.contains(day))
			this._day = day;
		else
			throw new NotValidDayException();

	
		
	}

	public String get_day() {
		return _day;
	}

	public TypeEnum get_type() {
		return _type;
	}

    public boolean equals(WorkerSchedule other){
		return other.get_day().equals(this.get_day()) &&
				other.get_type().equals(this.get_type());
	}



	public class NotValidDayException extends Throwable {
		public NotValidDayException() {
			System.out.println("Error: Not valid day in week.");
		}
	}


    public static TypeEnum getTypeByTime(java.util.Date time){
            int hour = Integer.parseInt(Date.hourf.format(time));
            if(hour < 16 )
                return TypeEnum.Morning;
            else
                return TypeEnum.Evening;
    }
}
