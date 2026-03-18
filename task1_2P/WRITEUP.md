# SIT707 Task 1.2P Write-Up

**Student Name:** Gaurav Myana  
**Student ID:** 225108954  
**Unit:** SIT707 Software Quality and Testing  
**Task:** 1.2P  

## Objective

The objective of this task was to use Selenium WebDriver in a Java Maven project to automate basic browser operations. The required activities included launching Chrome, loading a web page, resizing the browser window, and closing the browser after execution.

## Development Setup

The project was implemented in Java using Maven. Selenium WebDriver was used for browser automation, and WebDriverManager was added so that ChromeDriver could be managed automatically instead of using a hardcoded local driver path.

Technologies used:

- Java
- Maven
- Selenium WebDriver
- WebDriverManager
- Google Chrome

## Implementation Summary

The main implementation was completed in the method `open_chrome_loadpage_resize_close()` in `SeleniumOperations.java`.

The method performs the following steps:

1. Prints the student details to the console.
2. Uses WebDriverManager to set up ChromeDriver automatically.
3. Opens a Chrome browser.
4. Loads the Google home page.
5. Resizes the browser window to `640 x 480`.
6. Resizes the browser window again to `1280 x 960`.
7. Waits briefly between each action so the changes can be observed.
8. Closes the browser.

The `Main.java` file was also updated so that option `4` runs this completed task directly.

## Code Snippet

```java
public static void open_chrome_loadpage_resize_close() {
    System.out.println("Hello from 225108954, Gaurav Myana");

    WebDriverManager.chromedriver().setup();

    System.out.println("Fire up chrome browser.");
    WebDriver driver = new ChromeDriver();

    System.out.println("Driver info: " + driver);

    sleep(2);

    driver.get("https://www.google.com");
    sleep(2);

    driver.manage().window().setSize(new Dimension(640, 480));
    sleep(2);

    driver.manage().window().setSize(new Dimension(1280, 960));
    sleep(2);

    driver.close();
}
```

## Execution Steps

To run the program:

1. Open the project in Eclipse.
2. Right click the project and select `Maven -> Update Project`.
3. Wait for Maven to download the required dependencies.
4. Open `Main.java`.
5. Run the file as `Java Application`.
6. Observe the browser opening, loading Google, resizing twice, and then closing.

## Output and Observations

When the program runs successfully, Chrome opens and loads the Google page. The browser window is then resized to `640 x 480`, followed by `1280 x 960`. The browser closes after the final delay. This confirms that Selenium can control browser navigation and window management through Java code.

## Screenshots

### Screenshot 1: Project opened in Eclipse

`[Insert screenshot here]`

### Screenshot 2: Program execution started

`[Insert screenshot here]`

### Screenshot 3: Google page loaded in Chrome

`[Insert screenshot here]`

### Screenshot 4: Browser resized to 640 x 480

`[Insert screenshot here]`

### Screenshot 5: Browser resized to 1280 x 960

`[Insert screenshot here]`

### Screenshot 6: Console output in Eclipse

`[Insert screenshot here]`

## Challenges Faced

Initially, the template project used a hardcoded Linux ChromeDriver path, which caused execution to fail on Windows. This issue was resolved by replacing the hardcoded path with WebDriverManager so that the correct driver could be configured automatically.

Another issue was ensuring that the correct task method was executed. This was resolved by updating `Main.java` to run option `4`.

## Conclusion

This task demonstrated how Selenium WebDriver can be used in a Java application to automate browser-based actions. The task was successfully completed by launching Chrome, loading a website, resizing the window to required dimensions, and closing the browser. The use of WebDriverManager also improved portability and simplified setup.
