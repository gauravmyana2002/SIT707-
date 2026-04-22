package sit707_week5;

import org.junit.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class WeatherControllerTest {

	private static WeatherController weatherController;
	private static double[] todaysTemperatures;

	@BeforeClass
	public static void setUpOnce() {
		weatherController = WeatherController.getInstance();

		int totalHours = weatherController.getTotalHours();
		todaysTemperatures = new double[totalHours];
		for (int i = 0; i < totalHours; i++) {
			todaysTemperatures[i] = weatherController.getTemperatureForHour(i + 1);
		}
	}

	@AfterClass
	public static void tearDownOnce() {
		if (weatherController != null) {
			weatherController.close();
		}
	}

	@Test
	public void testStudentIdentity() {
		String studentId = "225108954";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Gaurav Myana";
		Assert.assertNotNull("Student name is null", studentName);
	}

	@Test
	public void testTemperatureMin() {
		System.out.println("+++ testTemperatureMin +++");

		double minTemperature = Double.MAX_VALUE;
		for (double temperatureVal : todaysTemperatures) {
			if (minTemperature > temperatureVal) {
				minTemperature = temperatureVal;
			}
		}

		Assert.assertEquals(minTemperature, weatherController.getTemperatureMinFromCache(), 0.0);
	}
	
	@Test
	public void testTemperatureMax() {
		System.out.println("+++ testTemperatureMax +++");

		double maxTemperature = Double.MIN_VALUE;
		for (double temperatureVal : todaysTemperatures) {
			if (maxTemperature < temperatureVal) {
				maxTemperature = temperatureVal;
			}
		}

		Assert.assertEquals(maxTemperature, weatherController.getTemperatureMaxFromCache(), 0.0);
	}

	@Test
	public void testTemperatureAverage() {
		System.out.println("+++ testTemperatureAverage +++");

		double sumTemp = 0;
		for (double temperatureVal : todaysTemperatures) {
			sumTemp += temperatureVal;
		}
		double averageTemp = sumTemp / todaysTemperatures.length;

		Assert.assertEquals(averageTemp, weatherController.getTemperatureAverageFromCache(), 0.0);
	}
	
	@Test
	public void testTemperaturePersist() {
		/*
		 * Remove below comments ONLY for 5.3C task.
		 */
//		System.out.println("+++ testTemperaturePersist +++");
//		
//		// Initialise controller
//		WeatherController wController = WeatherController.getInstance();
//		
//		String persistTime = wController.persistTemperature(10, 19.5);
//		String now = new SimpleDateFormat("H:m:s").format(new Date());
//		System.out.println("Persist time: " + persistTime + ", now: " + now);
//		
//		Assert.assertTrue(persistTime.equals(now));
//		
//		wController.close();
	}
}
