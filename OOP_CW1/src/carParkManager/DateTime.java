package carParkManager;

import java.io.Serializable;
import java.time.Year;
import java.util.Scanner;

public class DateTime implements Serializable {

	private static final long serialVersionUID = -1418987815774693577L;

	// Declaring private variables
	private int year = -1;
	private int month = -1;
	private int day = -1;
	private int hour = -1;
	private int min = -1;
	private int sec = -1;
	private boolean isLeapYear = false;

	static Scanner sc = new Scanner(System.in);

	// All input validations are done in the setters.
	public void setYear(int y) {
		if (y > 1900 && y <= (Year.now().getValue())) {
			this.year = y;
			if (y % 400 == 0) {
				isLeapYear = true;
			}
		} else {
			throw new IllegalArgumentException("Invalid minute!");
		}
	}

	// Declaring setter methods for private variables with input validation

	public void setMonth(int m) {
		if (m >= 1 && m <= 12) {
			this.month = m;
		} else {
			throw new IllegalArgumentException("Invalid minute!");
		}
	}

	public void setDay(int d) {
		if (d >= 1 && d <= 31) {
			switch (d) {
			case 29:
				if (!isLeapYear && this.month == 2) {
					throw new IllegalArgumentException("Invalid minute!");
				} else {
					this.day = d;
				}
				break;
			case 30:
				if (this.month == 2) {
					throw new IllegalArgumentException("Invalid minute!");
				} else {
					this.day = d;
				}
				break;
			case 31:
				if (this.month == 2 || this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) {
					throw new IllegalArgumentException("Invalid minute!");
				} else {
					this.day = d;
				}
				break;
			default:
				this.day = d;
				break;
			}
		} else {
			throw new IllegalArgumentException("Invalid minute!");
		}
	}

	public void setHour(int h) {
		if (h >= 0 && h <= 23) {
			this.hour = h;
		} else {
			throw new IllegalArgumentException("Invalid minute!");
		}

	}

	public void setMin(int m) {
		if (m >= 0 && m <= 59) {
			this.min = m;
		} else {
			throw new IllegalArgumentException("Invalid minute!");
		}
	}

	public void setSec(int s) {
		if (s >= 0 && s <= 59) {
			this.sec = s;
		} else {
			throw new IllegalArgumentException("Invalid minute!");
		}
	}

	// Declaring getter methods for private variables
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMin() {
		return min;
	}

	public int getSec() {
		return sec;
	}

	@Override
	// Return "yyyy-mm-dd hh:mm:ss" with leading zero for mm,dd,hh,mm,ss.
	public String toString() {
		// Use built-in function String.format() to form a formatted String
		return String.format("%4d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, min, sec);
		// Specifier "0" to print leading zeros, if available.
	}

	// Return "yyyy-mm-dd" with leading zero for mm,dd.
	public String toString2() {
		// Use built-in function String.format() to form a formatted String
		return String.format("%4d-%02d-%02d", year, month, day);
		// Specifier "0" to print leading zeros, if available.
	}

}
