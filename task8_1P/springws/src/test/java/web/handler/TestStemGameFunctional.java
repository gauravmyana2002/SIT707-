package web.handler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import web.MyServer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyServer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestStemGameFunctional {

	@LocalServerPort
	private int port;
	
	private WebDriver driver;
	
	@Before
	public void setUp() {
		driver = new HtmlUnitDriver(true);
	}
	
	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	@Test
	public void testInvalidLoginShowsErrorMessage() {
		open("/login");
		driver.findElement(By.id("username")).sendKeys("wrong");
		driver.findElement(By.id("passwd")).sendKeys("bad");
		driver.findElement(By.id("dob")).sendKeys("2000-01-01");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		
		Assert.assertTrue(driver.getCurrentUrl().endsWith("/login"));
		Assert.assertTrue(driver.getPageSource().contains("Incorrect credentials."));
	}
	
	@Test
	public void testCorrectAnswersNavigateThroughQuiz() {
		loginSuccessfully();
		Assert.assertTrue(driver.getCurrentUrl().endsWith("/q1"));
		
		answerQuestion("2", "3", "5");
		Assert.assertTrue(driver.getCurrentUrl().endsWith("/q2"));
		
		answerQuestion("9", "4", "5");
		Assert.assertTrue(driver.getCurrentUrl().endsWith("/q3"));
		
		answerQuestion("6", "7", "42");
		Assert.assertTrue(driver.getCurrentUrl().endsWith("/"));
		Assert.assertTrue(driver.getPageSource().contains("Well done, you completed the quiz."));
	}
	
	@Test
	public void testWrongQ1AnswerStaysOnQ1() {
		loginSuccessfully();
		answerQuestion("2", "3", "6");
		
		Assert.assertTrue(driver.getCurrentUrl().endsWith("/q1"));
		Assert.assertTrue(driver.getPageSource().contains("Wrong answer, try again."));
	}
	
	@Test
	public void testEmptyQ1InputStaysOnQ1WithoutErrorPage() {
		loginSuccessfully();
		answerQuestion("", "3", "3");
		
		Assert.assertTrue(driver.getCurrentUrl().endsWith("/q1"));
		Assert.assertTrue(driver.getPageSource().contains("Please enter valid numbers and an answer."));
	}
	
	private void loginSuccessfully() {
		open("/login");
		driver.findElement(By.id("username")).sendKeys("ahsan");
		driver.findElement(By.id("passwd")).sendKeys("ahsan_pass");
		driver.findElement(By.id("dob")).sendKeys("2000-01-01");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
	}
	
	private void answerQuestion(String number1, String number2, String result) {
		driver.findElement(By.id("number1")).sendKeys(number1);
		driver.findElement(By.id("number2")).sendKeys(number2);
		driver.findElement(By.id("result")).sendKeys(result);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
	}
	
	private void open(String path) {
		driver.get("http://localhost:" + port + path);
	}
}
