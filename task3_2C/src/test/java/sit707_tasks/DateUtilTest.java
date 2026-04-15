package sit707_tasks;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author Ahsan Habib
 */
public class DateUtilTest {

	private static final String STUDENT_ID = "Gaurav Myana";
	private static final String STUDENT_NAME = "225108954";
	
	@Test
	public void testStudentIdentity() {
		String studentId = STUDENT_ID;
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = STUDENT_NAME;
		Assert.assertNotNull("Student name is null", studentName);
	}

	@Test
	public void testMaxJanuary31ShouldIncrementToFebruary1() {
		// January max boundary area: max+1
		DateUtil date = new DateUtil(31, 1, 2024);
        System.out.println("january31ShouldIncrementToFebruary1 > " + date);
        date.increment();
        System.out.println(date);
        Assert.assertEquals(2, date.getMonth());
        Assert.assertEquals(1, date.getDay());
	}
	
	@Test
	public void testMaxJanuary31ShouldDecrementToJanuary30() {
		// January max boundary area: max-1
		DateUtil date = new DateUtil(31, 1, 2024);
        System.out.println("january31ShouldDecrementToJanuary30 > " + date);
        date.decrement();
        System.out.println(date);
        Assert.assertEquals(30, date.getDay());
        Assert.assertEquals(1, date.getMonth());
	}
	
	@Test
	public void testNominalJanuary() {
		int rand_day_1_to_31 = 1 + new Random().nextInt(31);
        DateUtil date = new DateUtil(rand_day_1_to_31, 1, 2024);
        System.out.println("testJanuaryNominal > " + date);
        date.increment();
        System.out.println(date);
	}
	
	/*
	 * Complete below test cases.
	 */
	
	@Test
	public void testMinJanuary1ShouldIncrementToJanuary2() {
		DateUtil date = new DateUtil(1, 1, 2024);
		System.out.println("january1ShouldIncrementToJanuary2 > " + date);
		date.increment();
		System.out.println(date);
		Assert.assertEquals(2, date.getDay());
		Assert.assertEquals(1, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}
	
	@Test
	public void testMinJanuary1ShouldDecrementToDecember31() {
		DateUtil date = new DateUtil(1, 1, 2024);
		System.out.println("january1ShouldDecrementToDecember31 > " + date);
		date.decrement();
		System.out.println(date);
		Assert.assertEquals(31, date.getDay());
		Assert.assertEquals(12, date.getMonth());
		Assert.assertEquals(2023, date.getYear());
	}
	
	/*
	 * Write tests for rest months of year 2024.
	 */

	@Test
	public void testDayClassD1ShouldIncrementWithinSameMonth() {
		DateUtil date = new DateUtil(15, 5, 2024);
		date.increment();
		Assert.assertEquals(16, date.getDay());
		Assert.assertEquals(5, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testDayClassD1ShouldDecrementWithinSameMonth() {
		DateUtil date = new DateUtil(15, 5, 2024);
		date.decrement();
		Assert.assertEquals(14, date.getDay());
		Assert.assertEquals(5, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testLeapYearFebruary28ShouldIncrementToFebruary29() {
		DateUtil date = new DateUtil(28, 2, 2024);
		date.increment();
		Assert.assertEquals(29, date.getDay());
		Assert.assertEquals(2, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testLeapYearFebruary29ShouldIncrementToMarch1() {
		DateUtil date = new DateUtil(29, 2, 2024);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(3, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testLeapYearMarch1ShouldDecrementToFebruary29() {
		DateUtil date = new DateUtil(1, 3, 2024);
		date.decrement();
		Assert.assertEquals(29, date.getDay());
		Assert.assertEquals(2, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testNonLeapYearFebruary28ShouldIncrementToMarch1() {
		DateUtil date = new DateUtil(28, 2, 2023);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(3, date.getMonth());
		Assert.assertEquals(2023, date.getYear());
	}

	@Test
	public void testNonLeapYearMarch1ShouldDecrementToFebruary28() {
		DateUtil date = new DateUtil(1, 3, 2023);
		date.decrement();
		Assert.assertEquals(28, date.getDay());
		Assert.assertEquals(2, date.getMonth());
		Assert.assertEquals(2023, date.getYear());
	}

	@Test
	public void testThirtyDayMonthShouldIncrementToNextMonth() {
		DateUtil date = new DateUtil(30, 4, 2024);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(5, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testMonthAfterThirtyDayMonthShouldDecrementToDay30() {
		DateUtil date = new DateUtil(1, 5, 2024);
		date.decrement();
		Assert.assertEquals(30, date.getDay());
		Assert.assertEquals(4, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testThirtyOneDayMonthShouldIncrementToNextMonth() {
		DateUtil date = new DateUtil(31, 7, 2024);
		date.increment();
		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(8, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test
	public void testMonthAfterThirtyOneDayMonthShouldDecrementToDay31() {
		DateUtil date = new DateUtil(1, 8, 2024);
		date.decrement();
		Assert.assertEquals(31, date.getDay());
		Assert.assertEquals(7, date.getMonth());
		Assert.assertEquals(2024, date.getYear());
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidThirtyFirstDayInThirtyDayMonthShouldThrowException() {
		new DateUtil(31, 4, 2024);
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidFebruary29InNonLeapYearShouldThrowException() {
		new DateUtil(29, 2, 2023);
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidMonthShouldThrowException() {
		new DateUtil(10, 13, 2024);
	}

	@Test(expected = RuntimeException.class)
	public void testInvalidYearShouldThrowException() {
		new DateUtil(10, 10, 1699);
	}
}
