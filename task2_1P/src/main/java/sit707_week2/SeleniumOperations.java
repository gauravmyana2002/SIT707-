package sit707_week2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/**
 * This class demonstrates Selenium locator APIs to identify HTML elements.
 * 
 * Details in Selenium documentation https://www.selenium.dev/documentation/webdriver/elements/locators/
 * 
 * @author Ahsan Habib
 */
public class SeleniumOperations {

	public static void sleep(int sec) {
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void takeFullPageScreenshot(WebDriver driver, String fileName) {
		try {
			Screenshot screenshot = new AShot()
					.shootingStrategy(ShootingStrategies.viewportPasting(1000))
					.takeScreenshot(driver);
			BufferedImage image = screenshot.getImage();
			ImageIO.write(image, "PNG", new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void officeworks_registration_page(String url) {
		// Step 1: Locate chrome driver folder in the local drive.
		WebDriverManager.chromedriver().setup();
		
		// Step 2: Use above chrome driver to open up a chromium browser.
		System.out.println("Fire up chrome browser.");
		WebDriver driver = new ChromeDriver();
		
		System.out.println("Driver info: " + driver);
		
		sleep(2);
	
		// Load a webpage in chromium browser.
		driver.get(url);
		
		/*
		 * How to identify a HTML input field -
		 * Step 1: Inspect the webpage, 
		 * Step 2: locate the input field, 
		 * Step 3: Find out how to identify it, by id/name/...
		 */
		
		// Find first input field which is firstname
		WebElement element = driver.findElement(By.id("firstname"));
		System.out.println("Found element: " + element);
		// Send first name
		element.sendKeys("Gaurav");
		
		/*
		 * Find following input fields and populate with values
		 */
		// Write code
		WebElement lastName = driver.findElement(By.id("lastname"));
		lastName.sendKeys("Myana");
		
		WebElement email = driver.findElement(By.id("email"));
		email.sendKeys("gauravmyana@example.com");
		
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys("123");
		
		WebElement confirmPassword = driver.findElement(By.id("confirmPassword"));
		confirmPassword.sendKeys("123");
		
		
		/*
		 * Identify button 'Create account' and click to submit using Selenium API.
		 */
		// Write code
		WebElement createAccountButton = driver.findElement(By.xpath("//button[contains(text(),'Create account')]"));
		createAccountButton.click();
		
		/*
		 * Take screenshot using selenium API.
		 */
		// Write code
		takeFullPageScreenshot(driver, "officeworks-registration.png");
		
		
		// Sleep a while
		sleep(2);
		
		// close chrome driver
		driver.close();	
	}

	public static void alternative_registration_page(String url) {
		// Open browser for the second website and fill a simple form.
		WebDriverManager.chromedriver().setup();
		System.out.println("Fire up chrome browser for alternative website.");
		WebDriver driver = new ChromeDriver();
		
		try {
			driver.manage().window().maximize();
			driver.get(url);
			sleep(2);
			
			WebElement firstName = driver.findElement(By.id("firstName"));
			firstName.clear();
			firstName.sendKeys("Gaurav");
			
			WebElement lastName = driver.findElement(By.id("lastName"));
			lastName.clear();
			lastName.sendKeys("Myana");
			
			WebElement email = driver.findElement(By.id("userEmail"));
			email.clear();
			email.sendKeys("gauravmyana@example.com");
			
			WebElement gender = driver.findElement(By.xpath("//label[@for='gender-radio-1']"));
			gender.click();
			
			// Intentionally use an invalid mobile number so the form does not submit successfully.
			WebElement mobile = driver.findElement(By.id("userNumber"));
			mobile.clear();
			mobile.sendKeys("12345");
			
			WebElement address = driver.findElement(By.id("currentAddress"));
			address.clear();
			address.sendKeys("Melbourne, Victoria");
			
			WebElement submitButton = driver.findElement(By.id("submit"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
			sleep(1);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
			sleep(2);
			
			takeFullPageScreenshot(driver, "alternative-registration.png");
		} finally {
			driver.close();
		}
	}
	
	
}
