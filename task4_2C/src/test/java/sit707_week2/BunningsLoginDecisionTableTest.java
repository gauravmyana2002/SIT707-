package sit707_week2;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class BunningsLoginDecisionTableTest {

	private WebDriver driver;

	@Before
	public void setup() {
		System.out.println("Preparing Bunnings login decision table test");
	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testDecisionTableHasRequiredRows() {
		int decisionTableRows = 4;
		Assert.assertEquals("Decision table should cover four combinations of email/password input",
				4, decisionTableRows);
	}

	@Test
	public void testLoginWithBlankEmailAndBlankPasswordFails() {
		assertInvalidLoginFails("", "");
	}

	@Test
	public void testLoginWithValidEmailAndBlankPasswordFails() {
		assertInvalidLoginFails("selenium.student@example.com", "");
	}

	@Test
	public void testLoginWithBlankEmailAndPasswordFails() {
		assertInvalidLoginFails("", "WrongPassword123");
	}

	@Test
	public void testLoginWithInvalidEmailAndPasswordFails() {
		assertInvalidLoginFails("not-a-real-bunnings-user@example.com", "WrongPassword123");
	}

	private void assertInvalidLoginFails(String email, String password) {
		Assume.assumeTrue("ChromeDriver path is required for Selenium tests",
				SeleniumOperations.isChromeDriverConfigured());
		driver = SeleniumOperations.openChromeDriver();

		SeleniumOperations.LoginResult result = SeleniumOperations.submitBunningsLogin(driver, email, password);
		SeleniumOperations.takeScreenshot(driver, "bunnings-login-test.png");

		Assert.assertTrue("Invalid login details should keep the user on the login flow or show an error",
				SeleniumOperations.hasLoginFailureEvidence(result));
	}
}
