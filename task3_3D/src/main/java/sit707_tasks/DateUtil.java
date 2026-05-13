package sit707_tasks;

/**
 * SIT707 Software Quality and Testing
 * Updated DateUtil class for Boundary Value Analysis and Equivalence Class Testing.
 */
public class DateUtil {

	// Months in order 0-11 maps to January-December.
	private static String[] MONTHS = new String[] {
			"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"
	};

	public static final int MIN_YEAR = 1700;
	public static final int MAX_YEAR = 2024;

	private int day, month, year;

	/*
	 * Constructs object from given day, month and year.
	 */
	public DateUtil(int day, int month, int year) {
		validateDate(day, month, year);

		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	/**
	 * Checks whether the supplied year is a leap year.
	 */
	public static boolean isLeapYear(int year) {
		if (year % 400 == 0) {
			return true;
		}

		if (year % 100 == 0) {
			return false;
		}

		return year % 4 == 0;
	}

	/**
	 * Checks whether the supplied date is valid.
	 */
	public static boolean isValidDate(int day, int month, int year) {
		if (year < MIN_YEAR || year > MAX_YEAR) {
			return false;
		}

		if (month < 1 || month > 12) {
			return false;
		}

		if (day < 1 || day > monthDuration(month, year)) {
			return false;
		}

		return true;
	}

	/**
	 * Validates the supplied date and throws an exception if invalid.
	 */
	private static void validateDate(int day, int month, int year) {
		if (year < MIN_YEAR || year > MAX_YEAR) {
			throw new RuntimeException("Invalid year: " + year + ", expected range " + MIN_YEAR + "-" + MAX_YEAR);
		}

		if (month < 1 || month > 12) {
			throw new RuntimeException("Invalid month: " + month + ", expected range 1-12");
		}

		if (day < 1 || day > 31) {
			throw new RuntimeException("Invalid day: " + day + ", expected range 1-31");
		}

		if (day > monthDuration(month, year)) {
			throw new RuntimeException("Invalid day: " + day + ", max day: " + monthDuration(month, year));
		}
	}

	/**
	 * Increment one day.
	 */
	public void increment() {
		int newDay = day;
		int newMonth = month;
		int newYear = year;

		if (newDay < monthDuration(newMonth, newYear)) {
			newDay++;
		} else if (newMonth < 12) {
			newDay = 1;
			newMonth++;
		} else {
			newDay = 1;
			newMonth = 1;
			newYear++;
		}

		validateDate(newDay, newMonth, newYear);

		this.day = newDay;
		this.month = newMonth;
		this.year = newYear;
	}

	/**
	 * Decrement one day from current date.
	 */
	public void decrement() {
		int newDay = day;
		int newMonth = month;
		int newYear = year;

		if (newDay > 1) {
			newDay--;
		} else if (newMonth > 1) {
			newMonth--;
			newDay = monthDuration(newMonth, newYear);
		} else {
			newMonth = 12;
			newYear--;
			newDay = monthDuration(newMonth, newYear);
		}

		validateDate(newDay, newMonth, newYear);

		this.day = newDay;
		this.month = newMonth;
		this.year = newYear;
	}

	/**
	 * Calculate duration of current month of year.
	 */
	public static int monthDuration(int month, int year) {
		if (month < 1 || month > 12) {
			throw new RuntimeException("Invalid month: " + month + ", expected range 1-12");
		}

		if (month == 2 && isLeapYear(year)) {
			return 29;
		} else if (month == 2) {
			return 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}

		return 31;
	}

	/**
	 * User friendly output.
	 */
	@Override
	public String toString() {
		return day + " " + MONTHS[month - 1] + " " + year;
	}
}