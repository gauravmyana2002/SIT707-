package web.service;

import org.junit.Assert;
import org.junit.Test;

public class TestMathQuestionService {

	@Test
	public void testTrueAdd() {
		Assert.assertEquals(MathQuestionService.q1Addition("1", "2"), 3, 0);
	}

	@Test
	public void testAddNumber1Empty() {
		Assert.assertNull(MathQuestionService.q1Addition("", "2"));
	}
	
	@Test
	public void testAddNumber2Empty() {
		Assert.assertNull(MathQuestionService.q1Addition("1", ""));
	}
	
	@Test
	public void testAddNonNumericInput() {
		Assert.assertNull(MathQuestionService.q1Addition("abc", "2"));
	}
	
	@Test
	public void testAddTrimsInput() {
		Assert.assertEquals(MathQuestionService.q1Addition(" 1.5 ", "2.5"), 4, 0);
	}
	
	@Test
	public void testTrueSubtraction() {
		Assert.assertEquals(MathQuestionService.q2Subtraction("8", "3"), 5, 0);
	}
	
	@Test
	public void testSubtractionNegativeResult() {
		Assert.assertEquals(MathQuestionService.q2Subtraction("3", "8"), -5, 0);
	}
	
	@Test
	public void testSubtractionInvalidInput() {
		Assert.assertNull(MathQuestionService.q2Subtraction("3", "ten"));
	}
	
	@Test
	public void testTrueMultiplication() {
		Assert.assertEquals(MathQuestionService.q3Multiplication("4", "6"), 24, 0);
	}
	
	@Test
	public void testMultiplicationWithDecimal() {
		Assert.assertEquals(MathQuestionService.q3Multiplication("2.5", "4"), 10, 0);
	}
	
	@Test
	public void testMultiplicationInvalidInput() {
		Assert.assertNull(MathQuestionService.q3Multiplication(null, "4"));
	}
}
