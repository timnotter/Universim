package universim.generalClasses;

public class Date {
	private int centiSeconds;
	private int deciSeconds;
	private int seconds;
	private int minutes;
	private int hours;
	private int day;
	private int month;
	private int year;
	
	public Date(int day, int month, int year) {
		centiSeconds = 0;
		deciSeconds = 0;
		seconds = 0;
		minutes = 0;
		hours = 0;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public int day() {
		return day;
	}
	
	public int month() {
		return month;
	}
	
	public int year() {
		return year;
	}
	
	public String toString() {
		String day= Integer.toString(this.day);
		String month = Integer.toString(this.month);
		String year = Integer.toString(this.year);
		if(this.day<10)
			day = "0" + day;
		if(this.month<10)
			month = "0" + month;
		if(this.year<10)
			year = "000" + year;
		else if(this.year<100)
			year = "00" + year;
		else if(this.year<1000)
			year = "0" + year;
		return(day + "." + month + "." + year);
	}
	
	public void nextCentiSecond() {
		if(centiSeconds==9) {
			centiSeconds = 0;
			nextDeciSecond();
		}
		else
			centiSeconds++;
	}
	
	public void nextCentiSecond(int times) {
		for(int i=0;i<times;i++) {
			if(centiSeconds==9) {
				centiSeconds = 0;
				nextDeciSecond();
			}
			else
				centiSeconds++;
		}
	}
	
	public void nextDeciSecond() {
		if(deciSeconds==9) {
			deciSeconds = 0;
			nextSecond();
		}
		else
			deciSeconds++;
	}
	
	public void nextDeciSecond(int times) {
		for(int i=0;i<times;i++) {
			if(deciSeconds==9) {
				deciSeconds = 0;
				nextSecond();
			}
			else
				deciSeconds++;
		}
	}
	
	public void nextSecond() {
		if(seconds<59) {
			seconds++;
		}
		else if(minutes<59) {
//			System.out.println("New minute");
			seconds = 0;
			minutes++;
		}
		else if(hours<23) {
			seconds = 0;
			minutes = 0;
			hours++;
		}
		else {
			seconds = 0;
			minutes = 0;
			hours = 0;
			nextDay();
		}
	}
	
	public void nextDay() {
		if(checkDate(day+1, month, year))
			day++;
		else if(checkDate(1, month+1, year)) {
			day = 1;
			month++;
		}
		else {
			day = 1;
			month = 1;
			year++;
		}
	}
	
	public boolean checkLeapYear() {
		return checkLeapYear(year);
	}
	
	public boolean checkLeapYear(int year) {
		assert year >= 0: "NegativeYear";
		if (year % 4 != 0) {
		    return false;
		  } 
		else if (year % 400 == 0) {
		    return true;
		  } 
		else if (year % 100 == 0) {
		    return false;
		  } 
		return true;
	}
	
	public boolean checkDate(int day, int month, int year) {
		//Initial checks
		if(day<0||day>31||month<1||month>12||year<0) {
			return false;
		}
		switch(month) {
			case 2:
				if(checkLeapYear(year)) {
					if(day>29)
						return false;
					return true;
				}
				else if(day>28)
					return false;
				return true;
			case 4:
			case 6:
			case 9:
			case 11:
				if(day>30)
					return false;
				return true;
			default: 
				return true;
		}
	}
}
