package web.service;

public class MathQuestionService {

	public static Double parseNumber(String number) {
		if (number == null || number.trim().isEmpty()) {
			return null;
		}
		
		try {
			return Double.valueOf(number.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * Calculate Q1 result.
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static Double q1Addition(String number1, String number2) {
		Double firstNumber = parseNumber(number1);
		Double secondNumber = parseNumber(number2);
		
		if (firstNumber == null || secondNumber == null) {
			return null;
		}
		
		return firstNumber + secondNumber;
	}
	
	/**
	 * Calculate Q2 result.
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static Double q2Subtraction(String number1, String number2) {
		Double firstNumber = parseNumber(number1);
		Double secondNumber = parseNumber(number2);
		
		if (firstNumber == null || secondNumber == null) {
			return null;
		}
		
		return firstNumber - secondNumber;
	}
	
	/**
	 * Calculate Q3 result.
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static Double q3Multiplication(String number1, String number2) {
		Double firstNumber = parseNumber(number1);
		Double secondNumber = parseNumber(number2);
		
		if (firstNumber == null || secondNumber == null) {
			return null;
		}
		
		return firstNumber * secondNumber;
	}
}
