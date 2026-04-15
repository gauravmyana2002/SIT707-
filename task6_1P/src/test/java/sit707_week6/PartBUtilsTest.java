package sit707_week6;

import org.junit.Assert;
import org.junit.Test;

public class PartBUtilsTest {

	@Test
	public void testSumUpToZero() {
		Assert.assertEquals(0, PartBUtils.sumUpTo(0));
	}

	@Test
	public void testSumUpToOne() {
		Assert.assertEquals(1, PartBUtils.sumUpTo(1));
	}

	@Test
	public void testSumUpToFive() {
		Assert.assertEquals(15, PartBUtils.sumUpTo(5));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSumUpToRejectsNegativeInput() {
		PartBUtils.sumUpTo(-1);
	}

	@Test
	public void testCountEvenNumbersUpToZero() {
		Assert.assertEquals(0, PartBUtils.countEvenNumbersUpTo(0));
	}

	@Test
	public void testCountEvenNumbersUpToOne() {
		Assert.assertEquals(0, PartBUtils.countEvenNumbersUpTo(1));
	}

	@Test
	public void testCountEvenNumbersUpToSix() {
		Assert.assertEquals(3, PartBUtils.countEvenNumbersUpTo(6));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCountEvenNumbersRejectsNegativeInput() {
		PartBUtils.countEvenNumbersUpTo(-2);
	}
}
