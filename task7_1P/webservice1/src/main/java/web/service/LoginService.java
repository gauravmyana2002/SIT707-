package web.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Business logic to handle login functions.
 * 
 * @author Ahsan.
 */
public class LoginService {

	private static final String VALID_USERNAME = "ahsan";
	private static final String VALID_PASSWORD = "ahsan_pass";
	private static final String VALID_DOB = "1990-01-01";

	/**
	 * Static method returns true for successful login, false otherwise.
	 * @param username
	 * @param password
	 * @param dob date of birth in yyyy-mm-dd format
	 * @return
	 */
	public static boolean login(String username, String password, String dob) {
		if (isBlank(username) || isBlank(password) || isBlank(dob)) {
			return false;
		}

		if (!isValidIsoDate(dob)) {
			return false;
		}

		return VALID_USERNAME.equals(username)
				&& VALID_PASSWORD.equals(password)
				&& VALID_DOB.equals(dob);
	}
	
	private static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
	
	private static boolean isValidIsoDate(String dob) {
		try {
			LocalDate.parse(dob);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
}
