package sit707_week6;

import org.junit.Assert;
import org.junit.Test;

public class WeatherAndMathUtilsTest {
	
	@Test
	public void testStudentIdentity() {
		String studentId = "Gaurav Myana";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "225108954";
		Assert.assertNotNull("Student name is null", studentName);
	}
	
	@Test
	public void testFalseNumberIsEven() {
		Assert.assertFalse(WeatherAndMathUtils.isEven(5));
	}
	
	@Test
	public void testTrueNumberIsEven() {
		Assert.assertTrue(WeatherAndMathUtils.isEven(8));
	}

	@Test
	public void testCancelWeatherAdviceForDangerousWind() {
		Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(70.1, 0.0));
	}

	@Test
	public void testCancelWeatherAdviceForDangerousRainfall() {
		Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(0.0, 6.1));
	}

	@Test
	public void testCancelWeatherAdviceForCombinedConcerningConditions() {
		Assert.assertEquals("CANCEL", WeatherAndMathUtils.weatherAdvice(45.1, 4.1));
	}

	@Test
	public void testWarnWeatherAdviceForConcerningWindOnly() {
		Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(46.0, 0.0));
	}

	@Test
	public void testWarnWeatherAdviceForConcerningRainOnly() {
		Assert.assertEquals("WARN", WeatherAndMathUtils.weatherAdvice(0.0, 4.1));
	}

	@Test
	public void testAllClearWeatherAdvice() {
		Assert.assertEquals("ALL CLEAR", WeatherAndMathUtils.weatherAdvice(20.0, 1.0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWeatherAdviceRejectsNegativeValues() {
		WeatherAndMathUtils.weatherAdvice(-1.0, 0.0);
	}

	@Test
	public void testPrimeNumber() {
		Assert.assertTrue(WeatherAndMathUtils.isPrime(13));
	}

	@Test
	public void testNonPrimeNumber() {
		Assert.assertFalse(WeatherAndMathUtils.isPrime(9));
	}

	@Test
	public void testOneIsNotPrime() {
		Assert.assertFalse(WeatherAndMathUtils.isPrime(1));
	}
}
