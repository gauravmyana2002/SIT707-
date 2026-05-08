package web.service;

import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class LoginServiceTest {
	
	private WebDriver driver;
	
	@Before
	public void setUp() {
		Assume.assumeTrue("Run Selenium tests with -Dselenium.tests=true after starting MyServer.",
				Boolean.getBoolean("selenium.tests"));
		driver = new ChromeDriver();
	}
	
	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testLoginSuccess() {
		submitLoginForm("ahsan", "ahsan_pass", "1990-01-01");
		assertLoginResponse("success", "Login status: success");
	}
	
	@Test
	public void testLoginFailsForWrongPassword() {
		submitLoginForm("ahsan", "wrong_pass", "1990-01-01");
		assertLoginResponse("fail", "Login status: fail");
	}
	
	@Test
	public void testLoginFailsForWrongDob() {
		submitLoginForm("ahsan", "ahsan_pass", "1990-01-02");
		assertLoginResponse("fail", "Login status: fail");
	}
	
	@Test
	public void testLoginFailsForMissingDob() {
		submitLoginForm("ahsan", "ahsan_pass", "");
		assertLoginResponse("fail", "Login status: fail");
	}
	
	private void submitLoginForm(String username, String password, String dob) {
		driver.navigate().to(loginPageUrl());
		
		type(By.id("username"), username);
		type(By.id("passwd"), password);
		type(By.id("dob"), dob);
		
		driver.findElement(By.cssSelector("[type=submit]")).submit();
	}
	
	private void type(By locator, String value) {
		WebElement element = driver.findElement(locator);
		element.clear();
		if (value != null && !value.isEmpty()) {
			element.sendKeys(value);
		}
	}
	
	private void assertLoginResponse(String expectedTitle, String expectedStatus) {
		Assert.assertEquals(expectedTitle, driver.getTitle());
		Assert.assertEquals(expectedStatus, driver.findElement(By.id("status")).getText());
	}
	
	private String loginPageUrl() {
		return Paths.get("..", "pages", "login.html")
				.toAbsolutePath()
				.normalize()
				.toUri()
				.toString();
	}
}
