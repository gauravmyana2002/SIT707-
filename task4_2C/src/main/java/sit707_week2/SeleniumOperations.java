package sit707_week2;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * This class demonstrates Selenium locator APIs to identify HTML elements.
 * 
 * Details in Selenium documentation https://www.selenium.dev/documentation/webdriver/elements/locators/
 * 
 * @author Ahsan Habib
 */
public class SeleniumOperations {

	public static final String BUNNINGS_LOGIN_URL = "https://www.bunnings.com.au/login";

	public static void sleep(int sec) {
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static WebDriver openChromeDriver() {
		String chromeDriverPath = System.getProperty("webdriver.chrome.driver");
		if (chromeDriverPath == null || chromeDriverPath.trim().isEmpty()) {
			String environmentPath = System.getenv("CHROME_DRIVER");
			if (environmentPath != null && !environmentPath.trim().isEmpty()) {
				System.setProperty("webdriver.chrome.driver", environmentPath);
			}
		}

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		return new ChromeDriver(options);
	}

	public static boolean isChromeDriverConfigured() {
		String systemPropertyPath = System.getProperty("webdriver.chrome.driver");
		String environmentPath = System.getenv("CHROME_DRIVER");
		return hasText(systemPropertyPath) || hasText(environmentPath);
	}

	public static LoginResult submitBunningsLogin(WebDriver driver, String email, String password) {
		driver.get(BUNNINGS_LOGIN_URL);
		String startUrl = driver.getCurrentUrl();

		clickIfPresent(driver, By.xpath("//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'sign in with bunnings')]"));
		clickIfPresent(driver, By.xpath("//a[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'sign in with bunnings')]"));

		WebElement emailInput = findFirst(driver,
				By.cssSelector("input[type='email']"),
				By.xpath("//input[contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'email')]"),
				By.xpath("//input[contains(translate(@id, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'email')]"),
				By.cssSelector("input[autocomplete='username']"));

		if (emailInput != null) {
			emailInput.clear();
			if (email != null) {
				emailInput.sendKeys(email);
			}
		}

		WebElement passwordInput = findFirst(driver,
				By.cssSelector("input[type='password']"),
				By.xpath("//input[contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'password')]"),
				By.xpath("//input[contains(translate(@id, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'password')]"),
				By.cssSelector("input[autocomplete='current-password']"));

		if (passwordInput != null) {
			passwordInput.clear();
			if (password != null) {
				passwordInput.sendKeys(password);
			}
		}

		WebElement submitButton = findFirst(driver,
				By.cssSelector("button[type='submit']"),
				By.cssSelector("input[type='submit']"),
				By.xpath("//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'sign in')]"),
				By.xpath("//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'log in')]"),
				By.xpath("//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'continue')]"));

		if (submitButton != null) {
			submitButton.click();
			sleep(2);
		}

		return new LoginResult(startUrl, driver.getCurrentUrl(), driver.getPageSource());
	}

	public static boolean hasLoginFailureEvidence(LoginResult result) {
		String currentUrl = result.getCurrentUrl().toLowerCase();
		String pageSource = result.getPageSource().toLowerCase();

		return currentUrl.contains("/login")
				|| pageSource.contains("required")
				|| pageSource.contains("invalid")
				|| pageSource.contains("incorrect")
				|| pageSource.contains("error");
	}

	public static File takeScreenshot(WebDriver driver, String fileName) {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File target = new File("target/screenshots/" + fileName);
		target.getParentFile().mkdirs();
		try {
			Files.copy(screenshot.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
			return target;
		} catch (IOException e) {
			throw new RuntimeException("Could not save screenshot", e);
		}
	}

	private static WebElement findFirst(WebDriver driver, By... locators) {
		long endTime = System.currentTimeMillis() + 5000;
		while (System.currentTimeMillis() < endTime) {
			for (By locator : locators) {
				List<WebElement> elements = driver.findElements(locator);
				if (!elements.isEmpty()) {
					return elements.get(0);
				}
			}
			sleep(1);
		}
		return null;
	}

	private static void clickIfPresent(WebDriver driver, By locator) {
		WebElement element = findFirst(driver, locator);
		if (element != null) {
			element.click();
			sleep(2);
		}
	}

	private static boolean hasText(String value) {
		return value != null && !value.trim().isEmpty();
	}

	public static class LoginResult {
		private final String startUrl;
		private final String currentUrl;
		private final String pageSource;

		public LoginResult(String startUrl, String currentUrl, String pageSource) {
			this.startUrl = startUrl;
			this.currentUrl = currentUrl;
			this.pageSource = pageSource;
		}

		public String getStartUrl() {
			return startUrl;
		}

		public String getCurrentUrl() {
			return currentUrl;
		}

		public String getPageSource() {
			return pageSource;
		}
	}
	
	
}
