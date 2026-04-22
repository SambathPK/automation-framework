package Utils;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v125.network.Network;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.awt.SystemColor.text;

public class Base extends ExtentReportListener {

    public static RemoteWebDriver driver;

    public static ExtentReports extentReports;

    ExtentSparkReporter sparkReporter;
    static ExtentTest test;

    public static DevTools devTools;

    static boolean value = false;


    public RemoteWebDriver launchUrl(String browser, String url) throws MalformedURLException {
        try {
            // Initialize ChromeOptions
            ChromeOptions chromeOptions = new ChromeOptions();
            //chromeOptions.addArguments("--start-maximized", "--window-size=1920,1080");
            chromeOptions.addArguments("--headless=new", "--window-size=1920,1080");
            chromeOptions.addArguments("--deny-permission-prompts");


            // Add other optional arguments if needed
            chromeOptions.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");

            // Combine ChromeOptions with DesiredCapabilities
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

            // Initialize RemoteWebDriver with combined capabilities
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions.merge(capabilities));
            // driver.manage().window().maximize();
            // Open the URL and configure implicit waits
            driver.manage().window().maximize();
            driver.get(url);
            driver.manage().deleteAllCookies(); // Clear cookies to avoid session issues

            System.out.println("Browser launched successfully with window size 1920x1080.");
            // Uncomment below if using a logging framework
            // test.log(Status.PASS, "Browser launched successfully");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to launch browser.");
            // Uncomment below if using a logging framework
            // test.log(Status.FAIL, browser + ": Browser launch failed");
            // test.log(Status.FAIL, e);
        }
        return driver;
    }

//    public static RemoteWebDriver launchUrl(String browser, String url) throws MalformedURLException {
//
//        try {
//
//            staticWait(6000);
//            // Configure ChromeOptions for headless mode, and performance optimization
//            ChromeOptions chromeOptions = new ChromeOptions();
//
//            // Common arguments for both Windows and Linux
//            chromeOptions.addArguments(
//                    "--headless=new",              // Use new headless mode for better compatibility
//                    "--no-sandbox",                // Disable the sandbox for compatibility in Linux
//                    "--disable-dev-shm-usage",     // Use /tmp instead of /dev/shm for shared memory
//                    "--disable-gpu",               // Disable GPU usage (not needed in headless mode)
//                    "--disable-extensions",        // Disable extensions for improved stability
//                    "--disable-background-networking", // Reduce background resource usage
//                    "--disable-software-rasterizer", // Use hardware acceleration if available
//                    "--mute-audio",                // Mute audio to avoid unnecessary processing
//                    "--remote-allow-origins=*",    // Handle potential CORS issues
//                    "--disable-browser-side-navigation", // Improves performance in some cases
//                    "--window-size=1920,1080",     // Set a specific resolution for proper rendering
//                    "--enable-automation",         // Indicate that this browser is controlled by automation
//                    "--disable-infobars",          // Disable infobars (e.g., "Chrome is being controlled by automated software")
//                    "--disable-popup-blocking",    // Prevent popups from interfering
//                    "--disable-translate",         // Disable Google Translate popup
//                    "--disable-background-timer-throttling", // Prevent throttling of JavaScript timers
//                    "--disable-renderer-backgrounding", // Keep rendering active in background
//                    "--dns-prefetch-disable",      // Prevent DNS prefetching to avoid resource contention
//                    "--disable-strict-file-interactability",
//                    "--disable-blink-features=AutomationControlled" // Prevent detection as automated software
//            );
//
//            // Linux-specific configurations
//            if (System.getProperty("os.name").toLowerCase().contains("linux")) {
//                chromeOptions.addArguments(
//                        "--disable-features=VizDisplayCompositor", // Ensure stability in headless mode
//                        "--single-process"                         // Use a single process (reduces resource contention)
//                );
//            }
//
//            // Add DPI scaling support for Windows
//            if (System.getProperty("os.name").toLowerCase().contains("win")) {
//                chromeOptions.addArguments("--force-device-scale-factor=1", "--high-dpi-support=1");
//            }
//
//            // Set the browser name in DesiredCapabilities
//            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
//
//            // Merge ChromeOptions into DesiredCapabilities
//            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
//
//            // Initialize RemoteWebDriver
//            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
//
//            // Open the URL and configure timeouts
//            driver.get(url);
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
//
//            // Clear local and session storage (important to avoid cookie issues)
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("window.localStorage.clear();");
//            js.executeScript("window.sessionStorage.clear();");
//
//            // Log that the browser has been launched successfully
//            System.out.println("Browser launched successfully in headless mode without incognito and cookies cleared.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to launch browser in headless mode", e);
//        }
//
//        return driver;
//    }

//    public static RemoteWebDriver launchUrl(String browser, String url) throws MalformedURLException {
//
//        try {
//            // Wait for resources to settle if necessary (optional)
//            // staticWait(10000);
//
//            // Configure ChromeOptions for headless mode
//            ChromeOptions chromeOptions = new ChromeOptions();
//            chromeOptions.addArguments(
//                    "--headless",                    // Headless mode
//                    "--no-sandbox",                  // Disable sandboxing (for CI environments)
//                    "--disable-dev-shm-usage",       // Reduce memory usage
//                    "--disable-gpu",                 // Disable GPU for better stability in headless mode
//                    "--window-size=1920,1080",       // Set the window size to full HD
//                    "--incognito",                   // Launch in incognito mode
//                    "--remote-allow-origins=*");     // Allow all origins for remote scripts (useful in some cloud grids)
//
//            // Set up DesiredCapabilities for the browser
//            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
//            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
//
//            // Initialize RemoteWebDriver with the remote hub URL
//            driver = new RemoteWebDriver(new URL("https://selenium.akku.work/wd/hub"), capabilities);
//
//            // Set timeouts for loading and implicit waits
//            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));  // Page load timeout
//            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));  // Implicit wait timeout
//
//            // Load the provided URL
//            driver.get(url);
//            driver.navigate().refresh();
//
//            // Clear local and session storage using JavaScript
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("window.localStorage.clear();");
//            js.executeScript("window.sessionStorage.clear();");
//
//            System.out.println("Browser launched successfully in headless mode.");
//
//        } catch (Exception e) {
//            // Handle any exceptions with detailed error output
//            e.printStackTrace();
//            throw new RuntimeException("Failed to launch browser in headless mode", e);
//        }
//
//        return driver;
//    }

    //
//    public static WebDriver browserr(String browser, String url) {
//        if (browser.equalsIgnoreCase("chrome")) {
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
//        } else if (browser.equalsIgnoreCase("firefox")) {
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
//        } else {
//            System.out.println("Not into the correct browser");
//        }
//        driver.get(url);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.manage().deleteAllCookies();
//        driver.manage().window().maximize();
//        driver.manage().window().getSize();
//        return driver;
//    }
//
    public void initialiseExtentReportsSuite() {
        extentReports = new ExtentReports();
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("User", System.getProperty("user.name"));
    }

    public void initialiseExtentReportsMethod(ITestResult result) throws IOException {
        // Retrieve the test method's description. If no description is provided, fallback to the method name.
        String testDescription = result.getMethod().getDescription();
        if (testDescription == null || testDescription.isEmpty()) {
            testDescription = result.getMethod().getMethodName(); // Fallback to method name if description is absent.
        }

        // Use the description for the ExtentReport test entry
        test = extentReports.createTest(testDescription);
    }

    public void initialiseExtentReportsMethodNew(ITestResult result, ITestContext context) throws IOException {
        // Get URL from current tenant context
        String tenantUrl = TestContext.getTenant();

        // Extract tenant name from URL
        // Example: https://releasetest.akku.work/ -> Releasetest
        String tenantName = "UnknownTenant";
        if (tenantUrl != null && !tenantUrl.isEmpty()) {
            try {
                tenantName = tenantUrl.replace("https://", "")
                        .replace("http://", "")
                        .split("\\.")[0];
                // Capitalize first letter
                tenantName = tenantName.substring(0, 1).toUpperCase() + tenantName.substring(1);
            } catch (Exception e) {
                // fallback
                tenantName = tenantUrl;
            }
        }

        // Get test description
        String testDescription = result.getMethod().getDescription();
        if (testDescription == null || testDescription.isEmpty()) {
            testDescription = result.getMethod().getMethodName();
        }

        // Get suite name
        String suite = context.getName();

        // Get Extent report for this tenant
        ExtentReports extentReports = ExtentManager.getExtent(tenantUrl);

        // Create test with formatted name: Tenant | Test Description | Suite
        test = extentReports.createTest(
                testDescription + " | " + tenantName + " - " + suite
        );

        // Log tenant URL immediately
        test.info("Tenant URL: " + tenantUrl);
    }


    // AfterMethod reporting
    public void extentReportAfterMethod(ITestResult result) {
        if (test == null) return; // safety

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test skipped");
        }

        if (extentReports != null) extentReports.flush();
    }


    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void scriptTimeout() {
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(5));
    }

    public void extentReportAfter() throws IOException {
        extentReports.flush();
    }

    public static void enter(By locator, String text) {
        try {
            // Increase timeout to 60 seconds and improve conditions
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            // Wait for the element to be present and visible, then clickable
            wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(locator), ExpectedConditions.visibilityOfElementLocated(locator), ExpectedConditions.elementToBeClickable(locator)));

            // Proceed with sending the text
            findElement(locator).sendKeys(text);

            // Log success
            test.log(Status.PASS, "Entered text on element with locator: " + locator);

        } catch (Exception e) {
            // Log failure and capture a screenshot if an error occurs
            test.log(Status.FAIL, e);
            test.log(Status.FAIL, "Failed to enter text '" + text + "' on element with locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }

    public static void jsEnter(By locator, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            Thread.sleep(300);

            js.executeScript(
                    "var el = arguments[0];" +
                            "el.focus();" +
                            "el.value = '';" +
                            "el.value = arguments[1];" +
                            "el.dispatchEvent(new Event('input',{bubbles:true}));" +
                            "el.dispatchEvent(new Event('change',{bubbles:true}));",
                    element, text
            );

            test.log(Status.PASS, "JS Entered text '" + text + "' on element with locator: " + locator);

        } catch (Exception e) {
            test.log(Status.FAIL, e);
            test.log(Status.FAIL,
                    "Failed to JS enter text '" + text + "' on element with locator: " + locator,
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }

    public static void jsTypeSlowly(By locator, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            Thread.sleep(300);

            // Clear first
            js.executeScript("arguments[0].focus(); arguments[0].value='';", element);

            for (char c : text.toCharArray()) {
                String s = String.valueOf(c);

                js.executeScript(
                        "var el = arguments[0];" +
                                "el.value = el.value + arguments[1];" +
                                "el.dispatchEvent(new Event('input', {bubbles:true}));",
                        element, s
                );

                Thread.sleep(80); // typing delay
            }

            js.executeScript("arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", element);

            test.log(Status.PASS, "JS Typed text '" + text + "' on element with locator: " + locator);

        } catch (Exception e) {
            test.log(Status.FAIL, e);
            test.log(Status.FAIL,
                    "Failed to JS type text '" + text + "' on element with locator: " + locator,
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }


    public void clearEnter(By locator, String text) {
        try {
            // Increase timeout to 60 seconds and improve conditions
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            // Wait for the element to be present, visible, and clickable
            wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(locator), ExpectedConditions.visibilityOfElementLocated(locator), ExpectedConditions.elementToBeClickable(locator)));

            // Clear and enter text into the element
            findElement(locator).clear();
            findElement(locator).sendKeys(text);

            // Log success
            test.log(Status.PASS, "Entered text '" + text + "' on element with locator: " + locator);
        } catch (Exception e) {
            // Log failure and capture a screenshot if an error occurs
            test.log(Status.FAIL, e);
            test.log(Status.FAIL, "Failed to enter text '" + text + "' on element with locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }

    public void keysEnter(By locator) {
        try {
            // Increase timeout to 60 seconds and improve conditions
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            // Wait for the element to be visible and clickable before performing action
            wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(locator), ExpectedConditions.visibilityOfElementLocated(locator), ExpectedConditions.elementToBeClickable(locator)));

            // Perform the ENTER key action
            findElement(locator).sendKeys(Keys.ENTER);

            // Log success
            test.log(Status.PASS, "Entered 'ENTER' key on element with locator: " + locator);
        } catch (Exception e) {
            // Log failure and capture a screenshot if an error occurs
            test.log(Status.FAIL, e);
            test.log(Status.FAIL, "Failed to enter 'ENTER' key on element with locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }

    public static WebElement findElement(By locator) {

        return driver.findElement(locator);
    }

    public static void click(By locator) {
        int attempts = 0;

        while (attempts < 3) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));

                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                wait.until(ExpectedConditions.visibilityOf(element));

                // Scroll for headless safety
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

                // Re-fetch before click to avoid stale
                element = driver.findElement(locator);

                element.click();

                test.log(Status.PASS, "Click action passed on element with locator: " + locator);
                return;

            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts == 3) {
                    test.log(Status.FAIL, e);
                    test.log(Status.FAIL,
                            "Click failed due to stale element on locator: " + locator,
                            MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                }
            } catch (Exception e) {
                test.log(Status.FAIL, e);
                test.log(Status.FAIL,
                        "Click action failed on element with locator: " + locator,
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                return;
            }
        }
    }


    //Browser Launch
    public static void windowMaximize() {
        try {
            driver.manage().window().maximize();
//            test.log(Status.PASS, " Window Maximize Success");
        } catch (Exception e) {
//            test.log(Status.FAIL, " Window Maximize Failed");
//            test.log(Status.FAIL, e);
        }
    }

    public static void assertEquals(String actual, String expected) {
        try {
            // Increase timeout to 60 seconds for more flexibility
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));

            // Wait for the text to be available in the DOM for comparison (adjust the locator to match context)
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*"), actual)); // Modify as necessary

            // Compare the actual and expected values
            if (actual.equalsIgnoreCase(expected)) {
                Assert.assertEquals(actual, expected);
                test.log(Status.PASS, "Assert Passed : Actual : " + actual + " ; Expected : " + expected);
            } else {
                test.log(Status.FAIL, "Assert Failed : Actual : " + actual + " ; Expected : " + expected, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                Assert.assertEquals(actual, expected); // This will throw the AssertionError
            }
        } catch (TimeoutException te) {
            // Handle timeout exception, log it with a screenshot
            test.log(Status.FAIL, "Timeout while waiting for the expected text", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te; // Rethrow to fail the test
        } catch (Exception e) {
            // Log other exceptions with a screenshot
            test.log(Status.FAIL, "Exception occurred during assertion", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
            throw e; // Rethrow to ensure test failure
        }
    }

    public static void assertEqualsnew(By locator, String expected) {
        try {
            // Increase timeout to 120 seconds (optional, can be adjusted as needed)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));

            // Wait until the element is present and visible (with additional logging for headless mode)
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            // Fetch the actual text from the element
            WebElement element = driver.findElement(locator);
            String actual = element.getText();

            // Wait for the text to be present in the element (use a more flexible check)
            if (actual == null || actual.isEmpty()) {
                test.log(Status.WARNING, "Text is empty or null for the element: " + locator.toString());
            }

            boolean textMatched = actual.equals(expected);

            // If the text matches the expected value, pass the assertion
            if (textMatched) {
                Assert.assertEquals(actual, expected);
                test.log(Status.PASS, "Assert Passed: Actual: " + actual + " ; Expected: " + expected);
            } else {
                test.log(Status.FAIL, "Assert Failed: Actual: " + actual + " ; Expected: " + expected,
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                Assert.fail("Assertion failed: Actual: " + actual + " ; Expected: " + expected);
            }

        } catch (TimeoutException te) {
            // Handling TimeoutException with detailed logging
            test.log(Status.FAIL, "Timeout while waiting for text to be present: " + locator.toString(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te;
        } catch (NoSuchElementException nse) {
            // Handling NoSuchElementException for better debugging
            test.log(Status.FAIL, "Element not found for locator: " + locator.toString(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw nse;
        } catch (Exception e) {
            // General exception handling for debugging
            test.log(Status.FAIL, "Exception occurred during assertion",
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
            throw e;
        }
    }


    public static void assertTextContains(By locator, String expectedSubstring) {
        try {
            // Increase timeout to 60 seconds for more flexibility
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));

            // Wait for the expected substring to be present in the specified element's text
            wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedSubstring));

            // Fetch the actual text from the element
            String actual = driver.findElement(locator).getText();

            // Perform the assertion using contains logic
            if (actual.contains(expectedSubstring)) {
                Assert.assertTrue(actual.contains(expectedSubstring), "Text contains expected substring.");
                test.log(Status.PASS, "Assert Passed : Actual contains Expected Substring : " + "Actual : " + actual + " ; Expected Substring : " + expectedSubstring);
            } else {
                // Log failure details with a screenshot
                test.log(Status.FAIL, "Assert Failed : Actual does not contain Expected Substring : " + "Actual : " + actual + " ; Expected Substring : " + expectedSubstring, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                Assert.fail("Assertion failed: Actual does not contain Expected Substring: " + "Actual: " + actual + " ; Expected Substring: " + expectedSubstring);
            }
        } catch (TimeoutException te) {
            // Handle timeout exception when text doesn't appear within the wait time
            test.log(Status.FAIL, "Timeout while waiting for text to be present in element: " + locator.toString(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te; // Rethrow to fail the test
        } catch (Exception e) {
            // Handle other exceptions and log details
            test.log(Status.FAIL, "Exception occurred during assertion", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
            throw e; // Rethrow to ensure test failure
        }
    }

    public static void assertTextFromMultipleElements(By locator, String expectedText) {
        try {
            // Increase timeout to 70 seconds for flexibility
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));

            // Wait for all elements to be visible
            List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

            if (elements.isEmpty()) {
                test.log(Status.FAIL, "No elements found with the locator: " + locator.toString());
                throw new AssertionError("No elements found with the locator: " + locator.toString());
            }

            boolean isMatchFound = false;

            for (WebElement element : elements) {
                String actualText = element.getText();

                // Perform validation for the expected text
                if (actualText.equalsIgnoreCase(expectedText)) {
                    Assert.assertEquals(actualText, expectedText);
                    test.log(Status.PASS, "Assert Passed: Actual: " + actualText + " ; Expected: " + expectedText);
                    isMatchFound = true;
                    break; // Exit loop as a match is found
                }
            }

            if (!isMatchFound) {
                // Log failure details with a screenshot
                test.log(Status.FAIL, "No matching text found. Expected: " + expectedText + " ; Checked elements: " + elements.size(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                throw new AssertionError("No matching text found for Expected: " + expectedText);
            }
        } catch (TimeoutException te) {
            // Handle timeout exception when elements are not found within the wait time
            test.log(Status.FAIL, "Timeout while waiting for elements with locator: " + locator.toString(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te; // Rethrow to fail the test
        } catch (Exception e) {
            // Handle other exceptions and log details
            test.log(Status.FAIL, "Exception occurred during text validation", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
            throw e; // Rethrow to ensure test failure
        }
    }

    public static boolean isTextPresentInMultipleElements(By locator, String expectedText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));

            List<WebElement> elements = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(locator)
            );

            if (elements.isEmpty()) {
                test.log(Status.INFO, "No elements found with locator: " + locator);
                return false;
            }

            for (WebElement element : elements) {
                String actualText = element.getText().trim();

                if (actualText.equalsIgnoreCase(expectedText)) {
                    test.log(Status.PASS,
                            "Text found. Actual: " + actualText + " ; Expected: " + expectedText);
                    return true;
                }
            }

            test.log(Status.INFO,
                    "Text not found. Expected: " + expectedText + " ; Checked elements: " + elements.size());
            return false;

        } catch (TimeoutException te) {
            test.log(Status.INFO,
                    "Timeout while waiting for elements with locator: " + locator);
            return false;

        } catch (Exception e) {
            test.log(Status.INFO,
                    "Exception while checking text: " + e.getMessage());
            return false;
        }
    }


    public static void waitForElementVisible(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            test.log(Status.PASS, "Element is visible: " + locator.toString());
        } catch (TimeoutException e) {
            test.log(Status.FAIL, "Timeout while waiting for element to be visible: " + locator.toString(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw e; // Rethrow to ensure the test case fails
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to wait for element visibility: " + locator.toString(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw e; // Rethrow for failure handling
        }
    }


    public static String getValue(By locator) {
        String value = findElement(locator).getAttribute("value");
        return value;
    }

    public static String getText(By locator) {
        // Increase timeout to ensure sufficient time for headless execution
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        try {
            // Wait for the element to be present in the DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Wait for the element to be visible, ensuring it's interactable
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            // Fetch the text from the element
            String value = findElement(locator).getText();
            test.log(Status.PASS, "Successfully retrieved text from the locator: " + locator + " with value: " + value);
            return value;
        } catch (TimeoutException te) {
            // Log timeout-specific failure and capture a screenshot
            test.log(Status.FAIL, "Timeout while waiting for the element: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te; // Rethrow to fail the test
        } catch (Exception e) {
            // Handle all other exceptions with logging and screenshot
            test.log(Status.FAIL, "Failed to retrieve text from the locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
            throw e;  // Re-throw the exception to ensure the test case fails
        }
    }

    public static String captureScreenshot() {
        // Capture screenshot
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Define paths
        String projectDir = System.getProperty("user.dir");
        String screenshotDirPath = Paths.get(projectDir, "Output/Akku_2.0/Report/Images").toString();
        File screenshotDir = new File(screenshotDirPath);
        if (!screenshotDir.exists() && !screenshotDir.mkdirs()) {
            System.out.println("Failed to create screenshot directory!");
        }

        // Create unique filename
        String screenshotName = "screenshot_" + System.currentTimeMillis() + ".png";
        String screenshotPath = Paths.get(screenshotDirPath, screenshotName).toString();

        // Copy file
        try {
            Files.copy(src.toPath(), new File(screenshotPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved to: " + screenshotPath);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }

        // Return path for ExtentReports (HTML-friendly)
        return "Output/Akku_2.0/Report/Images/" + screenshotName;
    }


    public static void close() {
        driver.close();
    }

    public static void quit() {
        driver.quit();
    }

    public static void getTableDetails(By tableId, By locator, String role) throws InterruptedException {
        // Explicit wait for the table to be present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        try {
            // Wait until the table is present
            WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(tableId));

            // Wait until the tbody is available and then get all rows
            WebElement tbody = table.findElement(By.xpath(".//tbody"));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(".//tr")));

            List<WebElement> rows = tbody.findElements(By.xpath(".//tr"));
            boolean value = false;

            // Loop through each row in the table
            Outerloop:
            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                // Get all cells of the current row
                List<WebElement> cells = row.findElements(By.xpath(".//td"));

                // Iterate through each cell and check if the required text (role) is present
                for (int j = 0; j < cells.size(); j++) {
                    WebElement cell = cells.get(j);
                    String cellText = cell.getText();

                    // If the required text is found in the cell
                    if (cellText.equals(role)) {
                        driver.findElements(locator).get(i).click(); // Click the corresponding element
                        value = true;
                        break Outerloop; // Exit outer loop if role is found
                    }
                }
            }

            // Check if the required text was found
            if (value) {
                test.log(Status.PASS, "Created User is present in the table");
            } else {
                test.log(Status.FAIL, "Created User is NOT present in the table", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }

        } catch (TimeoutException te) {
            // Handle timeout exception, likely when table or rows are not found in time
            test.log(Status.FAIL, "Timeout while waiting for the table or rows to be available", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te; // Rethrow to fail the test
        } catch (Exception e) {
            // Catch all other exceptions with logging and screenshot
            test.log(Status.FAIL, "Exception occurred while processing table details", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
            throw e; // Rethrow to ensure the test fails
        }
    }

    public static void getTableDetailsWithPagination(
            By tableId,
            By actionLocatorInRow,
            By nextPageLocator,
            String role) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        try {
            boolean found = false;

            while (true) {

                // Wait for table presence
                WebElement table = wait.until(
                        ExpectedConditions.presenceOfElementLocated(tableId)
                );

                WebElement tbody = table.findElement(By.xpath(".//tbody"));
                List<WebElement> rows = tbody.findElements(By.xpath(".//tr"));

                for (WebElement row : rows) {

                    List<WebElement> cells = row.findElements(By.xpath(".//td"));

                    for (WebElement cell : cells) {

                        if (cell.getText().trim().equals(role)) {

                            // Click action inside SAME ROW
                            WebElement actionElement = row.findElement(actionLocatorInRow);
                            actionElement.click();

                            found = true;
                            break;
                        }
                    }

                    if (found) break;
                }

                if (found) {
                    test.log(Status.PASS, "User found and clicked: " + role);
                    return;
                }

                // Try clicking next page
                WebElement nextBtn = driver.findElement(nextPageLocator);

                if (!nextBtn.isEnabled() ||
                        nextBtn.getAttribute("class").contains("disabled")) {

                    break; // Last page reached
                }

                nextBtn.click();

                // Wait for table to refresh
                wait.until(ExpectedConditions.stalenessOf(rows.get(0)));
            }

            // If not found after all pages
            test.log(Status.FAIL,
                    "User NOT found in any page: " + role,
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());

        } catch (Exception e) {

            test.log(Status.FAIL,
                    "Exception occurred while processing paginated table",
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());

            throw e;
        }
    }


    public static void getTableDetailsAndJsClick(By tableId, By locator, String role) throws InterruptedException {
        // Explicit wait for the table to be present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        try {
            // Wait until the table is present
            WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(tableId));

            // Wait until the tbody is available and then get all rows
            WebElement tbody = table.findElement(By.xpath(".//tbody"));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(".//tr")));

            List<WebElement> rows = tbody.findElements(By.xpath(".//tr"));
            boolean value = false;

            // Loop through each row in the table
            Outerloop:
            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                // Get all cells of the current row
                List<WebElement> cells = row.findElements(By.xpath(".//td"));

                // Iterate through each cell and check if the required text (role) is present
                for (int j = 0; j < cells.size(); j++) {
                    WebElement cell = cells.get(j);
                    String cellText = cell.getText();

                    // If the required text is found in the cell
                    if (cellText.equals(role)) {
                        // Perform JavaScript click instead of normal click
                        WebElement elementToClick = driver.findElements(locator).get(i);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elementToClick);

                        value = true;
                        break Outerloop; // Exit outer loop if role is found
                    }
                }
            }

            // Check if the required text was found
            if (value) {
                test.log(Status.PASS, "Created User is present in the table");
            } else {
                test.log(Status.FAIL, "Created User is NOT present in the table",
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }

        } catch (TimeoutException te) {
            // Handle timeout exception
            test.log(Status.FAIL, "Timeout while waiting for the table or rows to be available",
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te; // Rethrow to fail the test
        } catch (Exception e) {
            // Catch all other exceptions
            test.log(Status.FAIL, "Exception occurred while processing table details",
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
            throw e; // Rethrow to ensure the test fails
        }
    }


    public static void getMultipleTableDetails(By tableLocator, By locator, String permissions) throws InterruptedException {

        Thread.sleep(3000);
        WebElement table = driver.findElement(tableLocator);
        WebElement tbody = table.findElement(By.xpath("//tbody"));
        List<WebElement> rows = tbody.findElements(By.xpath(".//tr"));

        Outerloop:
        for (int i = 0; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            // Get all cells of the current row
            List<WebElement> cells = row.findElements(By.xpath(".//td"));
            // Iterate through each cell of the row and check if the required text is present
            for (int j = 0; j < cells.size(); j++) {
                WebElement cell = cells.get(j);
                String cellText = cell.getText();
                // Check if the required text is present in the cell
                if (cellText.equals(permissions)) {
                    value = true;
                    break Outerloop; // Exit outer loop if text is found
                }
            }
            driver.findElements(locator).get(i).click();
        }

        // Check if the required text is found in the table
        if (value) {
            test.log(Status.PASS, "Created User is prsent in the table");
        } else {
            test.log(Status.FAIL, "Created User is prsent in the table : " + MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }

    }

    public static void wait(String time) throws InterruptedException {
        Thread.sleep(Long.parseLong(time));
    }

    public static void deleteAll(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement textField = wait.until(ExpectedConditions.elementToBeClickable(locator));

            // Select all text in the text field (Ctrl+A)
            textField.sendKeys(Keys.chord(Keys.CONTROL, "a"));

            // Delete the selected text by sending the Delete key
            textField.sendKeys(Keys.DELETE);
            test.log(Status.PASS, "Successfully cleared text field with locator: " + locator);
        } catch (Exception e) {
            // Log failure and capture screenshot if an error occurs
            test.log(Status.FAIL, "Failed to clear text field with locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }


    public static void clearAndEnterText(By locator, String newValue) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Wait until element is present in DOM
            WebElement element = wait.until(
                    ExpectedConditions.presenceOfElementLocated(locator)
            );

            // Scroll element to center of viewport
            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    element
            );

            // Wait until clickable
            wait.until(ExpectedConditions.elementToBeClickable(element));

            // Click to focus
            element.click();

            // Clear existing value
            element.clear();

            // Extra safety for React / masked inputs
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.DELETE);

            // Enter new value
            element.sendKeys(newValue);

            // Optional: verify value entered correctly
            String enteredValue = element.getAttribute("value");
            if (!enteredValue.equals(newValue)) {
                throw new RuntimeException("Value mismatch after entering text.");
            }

            test.log(Status.PASS,
                    "Cleared and entered value '" + newValue + "' in field: " + locator.toString());

        } catch (Exception e) {

            test.log(Status.FAIL,
                    "Unable to clear and enter value in field: " + locator.toString(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }

    public static void scrollByElement(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Scroll to the specific element using JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", element);
            test.log(Status.PASS, "Successfully scrolled to element with locator: " + locator);
        } catch (Exception e) {
            // Capture failure details and screenshot
            test.log(Status.FAIL, "Failed to scroll to element with locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }

    public static void scrollToBottom() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))); // Wait for body element to be visible

            // Scroll to the bottom of the page using JavaScript
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            test.log(Status.PASS, "Successfully scrolled to the bottom of the page");
        } catch (Exception e) {
            // Log failure and capture screenshot
            test.log(Status.FAIL, "Failed to scroll to the bottom of the page", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }

    public static void waitPresentOfElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }


    public static void scrollToSpecificPixel(By locator, String attribute, String state) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        boolean elementFound = false;
        while (!elementFound) {
            List<WebElement> elements = driver.findElements(locator);

            for (WebElement element : elements) {
                String att = element.getAttribute(attribute);

                if (att != null && att.contains(state)) {
                    element.click(); // Select the element if the attribute contains the desired state
                    elementFound = true; // Set the flag to true to exit the loop
                    test.log(Status.PASS, "Element found on the DropDown with the locator : " + locator);
                    break; // Exit the loop since the element is found
                }
            }

            if (!elementFound) {
                // Scroll to the bottom of the dropdown
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView();", elements.get(elements.size() - 1));
            }
        }
    }

    public static String getTitle() {
        String title = driver.getTitle();
        return title;
    }

    public static void sendImage(By locator, String path) {
        try {
            String getProperty = System.getProperty("user.dir");
            findElement(locator).sendKeys(getProperty + path);
            test.log(Status.PASS, "Image Uploaded Successfully");
        } catch (Exception e) {
            test.log(Status.FAIL, e.getMessage());
            test.log(Status.FAIL, "Image not uploaded : " + MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }

    public static void uploadFile(By fileInputLocator, String relativeFilePath) {
        try {
            // Locate the file within the project directory
            String projectDir = System.getProperty("user.dir");
            String filePath = Paths.get(projectDir, relativeFilePath).toAbsolutePath().toString();

            // Debugging logs
            System.out.println("Project Directory: " + projectDir);
            System.out.println("Computed File Path: " + filePath);

            // Ensure the file exists
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException("File not found at path: " + filePath);
            }

            // Check if using RemoteWebDriver and set FileDetector
            if (driver instanceof RemoteWebDriver) {
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            }

            // Wait for the file input element to be present
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(fileInputLocator));

            // Send the absolute file path to the file input element
            fileInput.sendKeys(file.getAbsolutePath());

            // Log success
            test.log(Status.PASS, "File uploaded successfully from project directory: " + filePath);
        } catch (Exception e) {
            // Capture screenshot for debugging in headless mode
            String screenshotPath = captureScreenshot();
            test.log(Status.FAIL, "File upload failed: " + relativeFilePath,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }


    public static void openSavedCourse(By clickCourse, By getCourseName, String courseName) {

        List<WebElement> getCourses = driver.findElements(getCourseName);
        for (int i = 0; i < getCourses.size(); i++) {
            WebElement course = getCourses.get(i);
            String name = course.getText();
            if (name.contains(courseName)) {
                driver.findElements(clickCourse).get(i).click();
                test.log(Status.PASS, "Created Course Found");
                break;
            } else {
                test.log(Status.FAIL, "Created Course not Found : " + MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }
        }
    }

    public static void getSavedCoursePresent(By getCourseName, String courseName) {
        List<WebElement> getCourses = driver.findElements(getCourseName);
        for (int i = 0; i < getCourses.size(); i++) {
            WebElement course = getCourses.get(i);
            String name = course.getText();
            if (name.contains(courseName)) {
                value = true;
                break;
            }
        }
        if (value) {
            test.log(Status.PASS, "Created course present in the list");
        } else {

            test.log(Status.FAIL, "Created course not present in the list : " + MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());

        }
    }

    public static void clickOnSpecificElementByText(By getName, By locator, String textName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // Adjust the timeout as needed

            // Wait for elements to be visible
            List<WebElement> getData = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getName));
            List<WebElement> getElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

            // Check if either list is empty
            if (getData.isEmpty() || getElements.isEmpty()) {
                test.log(Status.FAIL, "No elements found with the locator: " + locator);
                return;
            }

            boolean value = false;
            for (int i = 0; i < getData.size(); i++) {
                WebElement course = getData.get(i);
                String name = course.getText();

                if (name.contains(textName)) {  // You could use `.equals(textName)` if an exact match is required.
                    value = true;

                    // Wait until the specific element is clickable before clicking
                    WebElement elementToClick = getElements.get(i);
                    wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).click();
                    break;
                }
            }

            // Log result based on whether the element was found and clicked
            if (value) {
                test.log(Status.PASS, "Element clicked with the locator: " + locator);
            } else {
                test.log(Status.FAIL, "Element with text '" + textName + "' not found and clicked.", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }
        } catch (Exception e) {
            // Log failure and capture screenshot in case of exception
            test.log(Status.FAIL, "Failed to click on element with locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }

    public static void clickOnSpecificElementByTextNew(By getName, By locator, String textName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // Adjust the timeout as needed
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Wait for elements to be present
            List<WebElement> getData = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getName));
            List<WebElement> getElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

            if (getData.isEmpty() || getElements.isEmpty()) {
                test.log(Status.FAIL, "No elements found with the given locators.");
                return;
            }

            boolean value = false;
            for (int i = 0; i < getData.size(); i++) {
                WebElement course = getData.get(i);

                // Ensure the element is scrolled into view and visible
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", course);
                wait.until(ExpectedConditions.visibilityOf(course)); // Ensure the element is visible
                Thread.sleep(500); // Optional: small delay for rendering

                String name = course.getText();

                if (name.contains(textName)) { // Match the text (use `.equals` for exact match)
                    value = true;

                    // Ensure the target element is scrolled into view and clickable
                    WebElement elementToClick = getElements.get(i);
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", elementToClick);
                    wait.until(ExpectedConditions.elementToBeClickable(elementToClick)).click();
                    break;
                }
            }

            if (value) {
                test.log(Status.PASS, "Element with text '" + textName + "' clicked successfully.");
            } else {
                test.log(Status.FAIL, "Element with text '" + textName + "' not found or not clickable.",
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }
        } catch (TimeoutException te) {
            test.log(Status.FAIL, "Timeout waiting for elements to be located. Locator: " + getName,
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, te.getMessage());
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to click on element. Exception occurred.",
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }


    public static void clickOnSpecificCheckBoxOnTable(By getName, By locator, String textName) {
        try {
            // Wait for the elements to be visible and fetch them
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120)); // Adjust the timeout as needed
            List<WebElement> getData = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getName));
            List<WebElement> getElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

            boolean value = false;
            for (int i = 0; i < getData.size(); i++) {
                WebElement course = getData.get(i);
                String name = course.getText();

                if (name.contains(textName)) {
                    value = true;

                    // Wait until the checkbox is clickable before clicking
                    wait.until(ExpectedConditions.elementToBeClickable(getElements.get(i + 1))).click();
                    break;
                }
            }

            // Log result based on whether the checkbox was clicked
            if (value) {
                test.log(Status.PASS, "Checkbox clicked for element with text: " + textName);
            } else {
                test.log(Status.FAIL, "Checkbox not clicked for element with text: " + textName, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }
        } catch (Exception e) {
            // Log failure and capture screenshot in case of exception
            test.log(Status.FAIL, "Failed to click on checkbox for element with text: " + textName, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }

    public static void staticWait(int seconds) throws InterruptedException {
        Thread.sleep(seconds);
    }

    public static String getSpecificElementText(By locator, String content) {

        String text = null;
        List<WebElement> elements = driver.findElements(locator);
        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            text = element.getText();
            if (text.contains(content)) {
                value = true;
                break;
            }
            return text;
        }
        if (value) {
            test.log(Status.PASS, "Text is present with the locator : " + locator);
        } else {
            test.log(Status.FAIL, "Text is not present with the locator : " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
        return text;
    }

    public static void escapeButton() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
    }

    public static void sendKeysJavaScript() {
        WebElement element = driver.findElement(By.xpath("//div[@class='relative']//input[@placeholder='Search']"));
        element.clear();
        Actions actions = new Actions(driver);
        actions.sendKeys(element, "sudhakaran.s@cloudnowtech.com").perform();
    }


    public static void ImplicitlyWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    public static void jClick(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Wait for element presence first (not visible yet)
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Optional: wait until visible if you need visibility
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            } catch (TimeoutException ignore) {
                // Ignore visibility wait; element is present anyway
            }

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

            // JS click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

            test.log(Status.PASS, "Successfully JS clicked on element: " + locator);

        } catch (TimeoutException te) {
            test.log(Status.FAIL, "Timeout: Element not found/clickable. Locator: " + locator,
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te;
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to click on element: " + locator + ". Error: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw new RuntimeException(e); // Rethrow to fail the test
        }
    }


    public static void EnterButton(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Added wait to ensure element is interactable
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator)); // Wait until element is clickable

            // Perform a click using Actions class
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().build().perform();

            // Log success
            test.log(Status.PASS, "Successfully clicked using EnterButton on the element with locator: " + locator);
        } catch (TimeoutException te) {
            // Handle timeout exception if element is not clickable within the timeout
            test.log(Status.FAIL, "Timeout: Element not clickable for EnterButton. Locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te; // Re-throw to fail the test
        } catch (Exception e) {
            // Handle other exceptions
            test.log(Status.FAIL, "Failed to click on the element with locator using EnterButton: " + locator + ". Error: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e);
        }
    }

    public static void AlertAccept() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public static void AlertDismiss() {
        Alert alertdismiss = driver.switchTo().alert();
        alertdismiss.dismiss();
    }

    public static void AlertSendKeys() {
        Alert alertSendkeys = driver.switchTo().alert();
        alertSendkeys.sendKeys("Value");
    }

    public static void Sendkeys(By locator, String Value) {
        findElement(locator).sendKeys(Value);

    }

    public static void selectDropdown(By locator, String visibleText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            WebElement dropdownElement = findElement(locator);
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByVisibleText(visibleText);
            test.log(Status.PASS, "Selected option " + visibleText + " from dropdown with locator: " + locator);
        } catch (Exception e) {
            test.log(Status.FAIL, e);
            test.log(Status.FAIL, "Failed to select option " + visibleText + " from dropdown with locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }

    public static void actionClick(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // Wait for element visibility
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator)); // Ensure element is clickable
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().build().perform(); // Perform action click
            test.log(Status.PASS, "Successfully performed action click on element with locator: " + locator);
        } catch (TimeoutException te) {
            // Handle timeout if element isn't clickable within the given time
            test.log(Status.FAIL, "Timeout: Element not clickable within specified time. Locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te; // Rethrow to fail the test
        } catch (Exception e) {
            // Handle other exceptions
            test.log(Status.FAIL, "Failed to perform action click on element with locator: " + locator + ". Error: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e); // Log exception details
        }
    }

    public static void findElementsAndClick(By locator, String requiredValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40)); // Wait for elements to be present
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

        // Find all elements matching the given locator
        List<WebElement> elements = driver.findElements(locator);

        if (elements.isEmpty()) {
            // If no elements found, log failure and return
            test.log(Status.FAIL, "No elements found with the given locator: " + locator.toString(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            return;
        }

        // Iterate through the elements to find a match
        boolean found = false;
        for (WebElement element : elements) {
            String elementText = element.getText();
            if (elementText.equalsIgnoreCase(requiredValue)) {
                element.click(); // Click the element
                test.log(Status.PASS, "Clicked on element with text: " + elementText);
                found = true;
                break; // Exit loop once clicked
            }
        }

        // Log failure if no matching element was found
        if (!found) {
            test.log(Status.FAIL, "No element found with text: " + requiredValue, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }

    public static void selectFromScrollableDropdown(By optionsLocator, String requiredValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        List<WebElement> options = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator)
        );

        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean found = false;

        for (WebElement option : options) {
            String text = option.getText().trim();

            if (text.equalsIgnoreCase(requiredValue)) {
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                test.log(Status.PASS, "Selected dropdown option: " + text);
                found = true;
                break;
            }
        }

        if (!found) {
            test.log(Status.FAIL, "Dropdown option not found: " + requiredValue,
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }


    public static void findElementsAndClickNew(By locator, String requiredValue) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

            // Only wait for presence
            List<WebElement> elements = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(locator)
            );

            if (elements.isEmpty()) {
                test.log(Status.FAIL, "No elements found with the given locator: " + locator,
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                return;
            }

            JavascriptExecutor js = (JavascriptExecutor) driver;

            boolean found = false;

            for (WebElement element : elements) {

                // Scroll + focus for headless
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                js.executeScript("arguments[0].focus();", element);

                String elementText = element.getText().trim();

                // Fallback if getText is empty in headless
                if (elementText.isEmpty()) {
                    elementText = element.getAttribute("textContent").trim();
                }

                if (elementText.equalsIgnoreCase(requiredValue.trim())) {
                    js.executeScript("arguments[0].click();", element);
                    test.log(Status.PASS, "Clicked on element with text: " + elementText);
                    found = true;
                    break;
                }
            }

            if (!found) {
                test.log(Status.FAIL, "No element found with text: " + requiredValue,
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }

        } catch (Exception e) {
            test.log(Status.FAIL, e,
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }


    public static void findElementsAndActionClick(By locator, String requiredValue) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40)); // Wait for elements to be present
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Find all elements matching the given locator
            List<WebElement> elements = driver.findElements(locator);

            if (elements.isEmpty()) {
                // If no elements found, log failure and return
                test.log(Status.FAIL, "No elements found with the given locator: " + locator.toString(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                return;
            }

            // Initialize Actions class
            Actions actions = new Actions(driver);

            // Iterate through the elements to find a match
            boolean found = false;
            for (WebElement element : elements) {
                // Retrieve text from element
                String elementText = element.getText().trim();

                if (elementText.equalsIgnoreCase(requiredValue)) {
                    // Scroll to the element to ensure visibility
                    actions.moveToElement(element).perform();

                    // Perform click using Actions
                    actions.click(element).perform();

                    test.log(Status.PASS, "Clicked on element with text: " + elementText);
                    found = true;
                    break; // Exit loop once clicked
                }
            }

            // Log failure if no matching element was found
            if (!found) {
                test.log(Status.FAIL, "No element found with text: " + requiredValue, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }
        } catch (Exception e) {
            // Handle exceptions and log failure
            test.log(Status.FAIL, "Exception occurred while trying to click the element.", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }


    public String getOTPFromDatabase(String userId) {
        String otp = null;
        String url = "jdbc:postgresql://35.200.133.20:5432/akku-test-v2-database?currentSchema=akku-test-v2-database";
        String username = "akkuadmin";
        String password = "Cloudnow@1212";
        String sql = "SELECT * FROM \"akku-test-v2-database\".user_otp_validation WHERE user_id = ? ORDER BY expiry_timestamp DESC LIMIT 1";

        // Load PostgreSQL JDBC driver
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
            return null; // Exit or handle error as appropriate
        }

        try (
                // Establishing connection
                Connection conn = DriverManager.getConnection(url, username, password);
                // Creating prepared statement with parameterized query
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            // Setting the parameter in the prepared statement
            stmt.setString(1, userId);

            // Executing query
            ResultSet rs = stmt.executeQuery();

            // Processing result set
            if (rs.next()) {
                // Retrieving OTP from the result set
                otp = rs.getString("otp");
                System.out.println("Latest OTP retrieved from database: " + otp);
            } else {
                System.out.println("No OTP found for user: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return otp;
    }


    public void validateText(By locator, String expectedText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            String actualText = element.getText().trim(); // Trim to remove leading/trailing whitespace

            // Check if actualText contains the expectedText
            if (actualText.contains(expectedText)) {
                test.log(Status.PASS, "Validation successful: Actual text '" + actualText + "' contains expected text '" + expectedText + "'");
            } else {
                test.log(Status.FAIL, "Validation failed: Expected text '" + expectedText + "' not found in actual text '" + actualText + "'", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                Assert.fail("Validation failed: Expected text '" + expectedText + "' not found in actual text '" + actualText + "'");
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred while validating text: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            Assert.fail("Exception occurred while validating text: " + e.getMessage(), e);
        }
    }

    public static void findElementAndEnterText(By headerLocator, By valueLocator, String requiredHeader, String requiredValue) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Wait for the header element to be present
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(headerLocator));
            // Wait for the value element to be present
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(valueLocator));

            // Find all elements matching the header locator
            List<WebElement> headerElements = driver.findElements(headerLocator);
            // Find all elements matching the value locator
            List<WebElement> valueElements = driver.findElements(valueLocator);

            // Check if header elements are empty
            if (headerElements.isEmpty()) {
                test.log(Status.FAIL, "No header elements found with the locator: " + headerLocator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                return;
            }

            // Check if value elements are empty
            if (valueElements.isEmpty()) {
                test.log(Status.FAIL, "No value elements found with the locator: " + valueLocator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                return;
            }

            // Iterate through each found header element
            boolean isHeaderFound = false;
            for (int i = 0; i < headerElements.size(); i++) {
                String headerText = headerElements.get(i).getText().trim();

                // Check if the header element matches the required text (case-insensitive)
                if (headerText.contains(requiredHeader)) {
                    valueElements.get(i).sendKeys(requiredValue);
                    test.log(Status.PASS, "Entered text '" + requiredValue + "' in the element with header text: '" + requiredHeader + "'");
                    isHeaderFound = true;
                    break; // Exit once the matching header is found
                }
            }

            // Log failure if no matching header was found
            if (!isHeaderFound) {
                test.log(Status.FAIL, "No matching header found for text: " + requiredHeader, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred while finding and entering text: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e); // Log the exception for further details
        }
    }

    public static void refreshPage() {
        try {
            driver.navigate().refresh(); // Refresh the page
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Wait for the page to be fully loaded
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState=='complete';"));

            test.log(Status.PASS, "Page refreshed successfully.");
        } catch (TimeoutException te) {
            // Handle timeout exceptions where the page didn't load in time
            test.log(Status.FAIL, "Page refresh timeout: Page did not load within the expected time.", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, te);
        } catch (Exception e) {
            // Handle any other exceptions
            test.log(Status.FAIL, "Failed to refresh the page: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e);
        }
    }


    public static void explicitWait(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            //element.click();
//             Log the successful click action
            test.log(Status.PASS, "Click action passed on element with locator: " + locator);
        } catch (Exception e) {
//             Log the failure if an exception occurs
            test.log(Status.FAIL, "Click action failed on element with locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
//             Log the exception details
            test.log(Status.FAIL, e);
        }
    }


    public static void findElementsAndClickwithPagination(By locator, String requiredValue, By nextPageButtonLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        boolean found = false;

        try {
            while (true) {
                // Wait until the element is present
                wait.until(ExpectedConditions.presenceOfElementLocated(locator));

                // Find all elements matching the given locator
                List<WebElement> elements = driver.findElements(locator);

                // Check if any elements were found
                if (!elements.isEmpty()) {
                    // Iterate through each found element
                    for (WebElement element : elements) {
                        String elementText = element.getText().trim();

                        // Check if the text of the element matches the required value (case insensitive)
                        if (elementText.equalsIgnoreCase(requiredValue)) {
                            element.click();
                            test.log(Status.PASS, "Clicked on the element with text: " + elementText);
                            found = true;
                            return;  // Exit the method after clicking the element
                        }
                    }
                }

                // If no matching element was found on this page, try the next page
                List<WebElement> nextPageButton = driver.findElements(nextPageButtonLocator);
                if (!nextPageButton.isEmpty()) {
                    nextPageButton.get(0).click();
                    wait.until(ExpectedConditions.stalenessOf(elements.get(0))); // Wait for the new page to load
                } else {
                    // If no next page button is found, exit the loop
                    break;
                }
            }

            // If no matching element was found after checking all pages
            if (!found) {
                test.log(Status.FAIL, "No element found with text: " + requiredValue, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }

        } catch (Exception e) {
            test.log(Status.FAIL, "An error occurred while finding elements and clicking: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }

    public static void findElementsAndClickwithPaginationnew(By locator, String requiredValue, By nextPageButtonLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        boolean found = false;

        try {
            while (true) {
                // Wait until the element is present
                wait.until(ExpectedConditions.presenceOfElementLocated(locator));

                // Find all elements matching the given locator
                List<WebElement> elements = driver.findElements(locator);

                // Check if any elements were found
                if (!elements.isEmpty()) {
                    // Iterate through each found element
                    for (WebElement element : elements) {
                        String elementText = element.getText();

                        // Check if the text of the element matches the required value (case insensitive)
                        if (elementText.equalsIgnoreCase(requiredValue)) {
                            element.click();
                            test.log(Status.PASS, "Clicked on the element with text: " + elementText);
                            return;
                        }
                    }
                }

                // If no matching element was found on this page, try the next page
                List<WebElement> nextPageButton = driver.findElements(nextPageButtonLocator);
                if (!nextPageButton.isEmpty()) {
                    nextPageButton.get(0).click();
                    wait.until(ExpectedConditions.stalenessOf(elements.get(0))); // Wait for the new page to load
                } else {
                    // If no next page button is found, exit the loop
                    break;
                }
            }

            // If no matching element was found after checking all pages
            if (!found) {
                test.log(Status.FAIL, "No element found with text: " + requiredValue, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }

        } catch (Exception e) {
            test.log(Status.INFO, "An error occurred: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }


    public static void selectUserFromPaginatedTable(
            By tableRowsLocator,
            By userTextLocatorInRow,
            By nextPageLocator,
            String requiredUser) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        boolean found = false;

        while (true) {
            // Wait for rows on current page
            List<WebElement> rows = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(tableRowsLocator)
            );

            for (WebElement row : rows) {
                WebElement userCell = row.findElement(userTextLocatorInRow);
                String userText = userCell.getText().trim();

                if (userText.equalsIgnoreCase(requiredUser)) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", userCell);
                    wait.until(ExpectedConditions.elementToBeClickable(userCell)).click();

                    test.log(Status.PASS, "Clicked user: " + userText);
                    found = true;
                    return;
                }
            }

            // If user not found, try next page
            WebElement nextBtn;
            try {
                nextBtn = driver.findElement(nextPageLocator);
            } catch (NoSuchElementException e) {
                break; // no pagination
            }

            if (!nextBtn.isEnabled()
                    || nextBtn.getAttribute("class").contains("disabled")) {
                break; // last page reached
            }

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", nextBtn);
            nextBtn.click();

            // Wait for page/table refresh
            wait.until(ExpectedConditions.stalenessOf(rows.get(0)));
        }

        // Final failure
        test.log(Status.FAIL,
                "User not found in table: " + requiredUser,
                MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
    }


    public static String getClipboardContent() throws UnsupportedFlavorException, IOException {
        // Check if running in headless mode
        if (GraphicsEnvironment.isHeadless()) {
            // If headless, return empty string as clipboard access is unavailable
            return "";
        }

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        DataFlavor flavor = DataFlavor.stringFlavor;

        if (clipboard.isDataFlavorAvailable(flavor)) {
            return (String) clipboard.getData(flavor);  // Retrieves the copied text as a String
        }
        return "";  // Return an empty string if no valid content is available
    }

    public static void clickOnButtonByName(By nameLocator, By buttonLocator, String textName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // Adjust the timeout as needed
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Wait for the elements to be present in the DOM
            List<WebElement> nameElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(nameLocator));
            List<WebElement> buttonElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(buttonLocator));

            if (nameElements.isEmpty() || buttonElements.isEmpty()) {
                test.log(Status.FAIL, "No elements found with the provided locators.");
                return;
            }

            boolean isClicked = false;

            for (int i = 0; i < nameElements.size(); i++) {
                WebElement nameElement = nameElements.get(i);
                String currentName = nameElement.getText();

                // Scroll to the name element to ensure it's in view
                js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", nameElement);
                Thread.sleep(500); // Optional: Allow time for animations

                if (currentName.contains(textName)) { // Match the desired text
                    WebElement buttonToClick = buttonElements.get(i);

                    // Scroll to the button to ensure it's clickable
                    js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", buttonToClick);
                    wait.until(ExpectedConditions.elementToBeClickable(buttonToClick)).click();

                    isClicked = true;
                    break;
                }
            }

            if (isClicked) {
                test.log(Status.PASS, "Button clicked successfully for the name: " + textName);
            } else {
                test.log(Status.FAIL, "Button could not be clicked for the name: " + textName,
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to click on button for name: " + textName,
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }

    public static void clickOnButtonByNameAndText(By nameLocator, By buttonLocator, String textName, String buttonText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // Adjust the timeout as needed
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Wait for the elements to be present in the DOM
            List<WebElement> nameElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(nameLocator));
            List<WebElement> buttonElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(buttonLocator));

            if (nameElements.isEmpty() || buttonElements.isEmpty()) {
                test.log(Status.FAIL, "No elements found with the provided locators.");
                return;
            }

            boolean isClicked = false;

            for (int i = 0; i < nameElements.size(); i++) {
                WebElement nameElement = nameElements.get(i);
                String currentName = nameElement.getText();

                // Scroll to the name element to ensure it's in view
                js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", nameElement);
                Thread.sleep(500); // Optional: Allow time for animations

                if (currentName.contains(textName)) { // Match the desired name text
                    WebElement buttonToClick = buttonElements.get(i);

                    // Scroll to the button to ensure it's clickable
                    js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", buttonToClick);

                    // Check if the button's text matches the provided buttonText
                    String currentButtonText = buttonToClick.getText();
                    if (currentButtonText.contains(buttonText)) {
                        wait.until(ExpectedConditions.elementToBeClickable(buttonToClick)).click();
                        isClicked = true;
                        break;
                    }
                }
            }

            if (isClicked) {
                test.log(Status.PASS, "Button clicked successfully for the name: " + textName + " and button text: " + buttonText);
            } else {
                test.log(Status.FAIL, "Button could not be clicked for the name: " + textName + " with button text: " + buttonText,
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to click on button for name: " + textName + " with button text: " + buttonText,
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }


    public static void pressEnter() {
        try {
            Robot robot = new Robot();

            // Simulate Enter key press
            robot.keyPress(KeyEvent.VK_ENTER);

            // Simulate Enter key release
            robot.keyRelease(KeyEvent.VK_ENTER);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error simulating Enter key press: " + e.getMessage());
        }
    }

    public static void mouseOverAction(By locator) {
        Actions action = new Actions(driver);
        WebElement element = findElement(locator);
        action.moveToElement(element).build().perform();
    }

    public static void tabAction() {
        try {
            Actions action = new Actions(driver);
            action.keyDown(Keys.CONTROL).sendKeys(Keys.TAB).build().perform();
            test.log(Status.PASS, "Tab Action");
        } catch (Exception e) {
//             Log the failure if an exception occurs
            test.log(Status.FAIL, "Click action failed on e ", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e);
        }
    }

    public static void enterAction() {

        try {
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ENTER);
        } catch (Exception e) {
//             Log the failure if an exception occurs
            test.log(Status.FAIL, "Click action failed on ele ", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e);
        }
    }

    public void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // 60 seconds for page load
        wait.until(driver -> {
            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        });
    }

    public static void findElementByIndexAndPasteText(By locator, int elementIndex, String textToEnter) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40)); // Wait for elements to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        // Find all elements matching the given locator
        List<WebElement> elements = driver.findElements(locator);

        if (elements.isEmpty()) {
            // If no elements found, log failure and return
            test.log(Status.FAIL, "No elements found with the given locator: " + locator.toString(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            return;
        }

        if (elementIndex < 0 || elementIndex >= elements.size()) {
            // If the index is out of bounds, log failure and return
            test.log(Status.FAIL, "Invalid index: " + elementIndex + ". Total elements found: " + elements.size(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            return;
        }

        try {
            WebElement element = elements.get(elementIndex); // Get the element at the specified index
            element.clear(); // Clear the existing text (if any)

            // Directly type the text into the element
            element.sendKeys(textToEnter);

            test.log(Status.PASS, "Successfully entered text '" + textToEnter + "' into element at index: " + elementIndex);
        } catch (Exception e) {
            // Log any exception encountered while trying to enter text into the element
            test.log(Status.FAIL, "Failed to enter text into element at index: " + elementIndex + ". Exception: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }
    }

    public static void findElementByIndexAndClick(By locator, int elementIndex) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90)); // Wait for elements to be present
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator)); // Wait for all elements to be present
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

            // Find all elements matching the given locator
            List<WebElement> elements = driver.findElements(locator);

            if (elements.isEmpty()) {
                // If no elements are found, log failure and return
                test.log(Status.FAIL, "No elements found with the given locator: " + locator.toString(),
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                return;
            }

            if (elementIndex < 0 || elementIndex >= elements.size()) {
                // If the index is out of bounds, log failure and return
                test.log(Status.FAIL, "Invalid index: " + elementIndex + ". Total elements found: " + elements.size(),
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                return;
            }

            WebElement element = elements.get(elementIndex); // Get the element at the specified index

            // Wait for the element to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            // Scroll to the element to ensure visibility
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();

            // Check if the element is still obscured and try to resolve that issue
            if (isElementObscured(element)) {
                // Optionally, handle overlay or modal closure before proceeding
                closeOverlayIfPresent(); // Assuming you have a function to handle modals or overlays
            }

            // Attempt to click the element directly or via JavaScript if it's still blocked
            try {
                element.click();
            } catch (ElementClickInterceptedException e) {
                // Handle intercepted click by using JavaScript to perform the click
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].click();", element);
            }

            test.log(Status.PASS, "Successfully clicked element at index: " + elementIndex);
        } catch (TimeoutException te) {
            test.log(Status.FAIL, "Timeout while waiting for elements with locator: " + locator.toString(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te;
        } catch (NoSuchElementException nse) {
            test.log(Status.FAIL, "Element not found for locator: " + locator.toString(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw nse;
        } catch (ElementClickInterceptedException e) {
            test.log(Status.FAIL, "Click was intercepted: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw e;
        } catch (Exception e) {
            // Handle other exceptions
            test.log(Status.FAIL, "Error while interacting with elements: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw e;
        }
    }

    // Helper method to check if an element is obscured by another element
    private static boolean isElementObscured(WebElement element) {
        try {
            return !element.isDisplayed() || !element.isEnabled();
        } catch (Exception e) {
            return true; // If any exception occurs while checking, assume the element is obscured
        }
    }

    // Optional method to close modal or overlay if it exists
    private static void closeOverlayIfPresent() {
        try {
            WebElement overlay = driver.findElement(By.cssSelector("selector-for-overlay"));
            if (overlay.isDisplayed()) {
                WebElement closeButton = overlay.findElement(By.cssSelector("button.close"));
                closeButton.click();
            }
        } catch (NoSuchElementException e) {
            // No overlay present, proceed
        }
    }


    public static void safeClick(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            // Wait for the loader or overlay to disappear if present
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class, 'loader')]")));

            // Wait for the element to be present in the DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));

            // Wait for the element to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            // Scroll the element into view if necessary
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

            // Wait for the element to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            // Perform the click action
            element.click();

            // Log the successful click action
            test.log(Status.PASS, "Click action passed on element with locator: " + locator);
        } catch (Exception e) {
            // Log the failure and capture a screenshot if an error occurs
            try {
                String screenshotPath = captureScreenshot();
                test.log(Status.FAIL, "Click action failed on element with locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } catch (Exception ex) {
                test.log(Status.FAIL, "Click action failed and screenshot could not be captured.");
            }
            test.log(Status.FAIL, e);
        }
    }

    public static void findElementsAndClickByVisibleText(By textLocator, String requiredValue, By toggleButtonLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            // Wait for the elements to be present on the page
            wait.until(ExpectedConditions.presenceOfElementLocated(textLocator));

            // Find all elements matching the text locator
            List<WebElement> textElements = driver.findElements(textLocator);

            // Iterate through the list of text elements
            for (int i = 0; i < textElements.size(); i++) {
                String elementText = textElements.get(i).getText();

                // Check if the text matches the required value (case-insensitive)
                if (elementText.equalsIgnoreCase(requiredValue)) {
                    // Use the index to identify the corresponding toggle button
                    List<WebElement> toggleButtons = driver.findElements(toggleButtonLocator);

                    // Ensure the toggle button at this index exists and click it
                    if (i < toggleButtons.size()) {
                        toggleButtons.get(i).click();
                        System.out.println("Clicked on the toggle button for: " + elementText);
                        return; // Exit after finding and clicking the required element
                    }
                }
            }

            // Log if no matching element was found
            System.out.println("No element found with text: " + requiredValue);

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public static void assertContainsText(By locator) {
        try {
            // Increase timeout to 120 seconds (optional, can be adjusted as needed)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));

            // Wait until at least one element is present and visible
            List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

            // Fetch text from all elements
            List<String> texts = new ArrayList<>();
            for (WebElement element : elements) {
                String text = element.getText().trim();
                if (!text.isEmpty()) {
                    texts.add(text);
                }
            }

            if (!texts.isEmpty()) {
                test.log(Status.PASS, "Assert Passed: Elements contain texts: " + texts);
            } else {
                test.log(Status.FAIL, "Assert Failed: No text found for locator: " + locator.toString(),
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                Assert.fail("Assertion failed: No text found for locator: " + locator.toString());
            }

        } catch (TimeoutException te) {
            test.log(Status.FAIL, "Timeout while waiting for elements: " + locator.toString(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te;
        } catch (NoSuchElementException nse) {
            test.log(Status.FAIL, "No elements found for locator: " + locator.toString(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw nse;
        } catch (Exception e) {
            test.log(Status.FAIL, "Exception occurred during assertion",
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
            throw e;
        }
    }

    public static void assertTextContainsInMultipleElements(By locator, String expectedText) {
        try {
            // Increase timeout to 70 seconds for flexibility
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));

            // Wait for all elements to be visible
            List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

            if (elements.isEmpty()) {
                test.log(Status.FAIL, "No elements found with the locator: " + locator.toString());
                throw new AssertionError("No elements found with the locator: " + locator.toString());
            }

            boolean isMatchFound = false;

            for (WebElement element : elements) {
                String actualText = element.getText();

                // Check if the actual text contains the expected text
                if (actualText.contains(expectedText)) {
                    test.log(Status.PASS, "Assert Passed: Actual: " + actualText + " ; Expected: " + expectedText);
                    isMatchFound = true;
                    break; // Exit loop as a match is found
                }
            }

            if (!isMatchFound) {
                // Log failure details with a screenshot
                test.log(Status.FAIL, "No matching text found. Expected text: " + expectedText + " ; Checked elements: " + elements.size(),
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                throw new AssertionError("No matching text found for Expected: " + expectedText);
            }
        } catch (TimeoutException te) {
            // Handle timeout exception when elements are not found within the wait time
            test.log(Status.FAIL, "Timeout while waiting for elements with locator: " + locator.toString(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te; // Rethrow to fail the test
        } catch (Exception e) {
            // Handle other exceptions and log details
            test.log(Status.FAIL, "Exception occurred during text validation", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
            throw e; // Rethrow to ensure test failure
        }
    }

    public static void pressEnterActions() {
        try {
            // Create an Actions object
            Actions actions = new Actions(driver);

            // Perform the Enter key press action
            actions.sendKeys(Keys.ENTER).perform();

            // Log success
            test.log(Status.PASS, "Successfully pressed the Enter key");
        } catch (Exception e) {
            // Log failure and capture screenshot if an error occurs
            test.log(Status.FAIL, "Failed to press the Enter key", MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }

    public static void findElementByIndexAndActionClick(By locator, int elementIndex) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90)); // Wait for elements to be present
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

            List<WebElement> elements = driver.findElements(locator);

            if (elements.isEmpty()) {
                test.log(Status.FAIL, "No elements found with the given locator: " + locator.toString(),
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                return;
            }

            if (elementIndex < 0 || elementIndex >= elements.size()) {
                test.log(Status.FAIL, "Invalid index: " + elementIndex + ". Total elements found: " + elements.size(),
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                return;
            }

            WebElement element = elements.get(elementIndex);

            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));

            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();

            if (isElementObscured(element)) {
                closeOverlayIfPresent();
            }

            try {
                // Perform click using Actions class
                actions.moveToElement(element).click().build().perform();
            } catch (ElementClickInterceptedException e) {
                test.log(Status.FAIL, "Click was intercepted (even using Actions): " + e.getMessage(),
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
                throw e;
            }

            test.log(Status.PASS, "Successfully clicked element at index: " + elementIndex + " using Actions click");
        } catch (TimeoutException te) {
            test.log(Status.FAIL, "Timeout while waiting for elements with locator: " + locator.toString(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw te;
        } catch (NoSuchElementException nse) {
            test.log(Status.FAIL, "Element not found for locator: " + locator.toString(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw nse;
        } catch (Exception e) {
            test.log(Status.FAIL, "Error while interacting with elements: " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            throw e;
        }
    }

    public static void jClickOnButtonByName(By nameLocator, By buttonLocator, String textName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Wait for the elements to be present in the DOM
            List<WebElement> nameElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(nameLocator));
            List<WebElement> buttonElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(buttonLocator));

            if (nameElements.isEmpty() || buttonElements.isEmpty()) {
                test.log(Status.FAIL, "No elements found with the provided locators.");
                return;
            }

            boolean isClicked = false;

            for (int i = 0; i < nameElements.size(); i++) {
                WebElement nameElement = nameElements.get(i);
                String currentName = nameElement.getText();

                // Scroll to the name element
                js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", nameElement);
                Thread.sleep(500); // Optional

                if (currentName.contains(textName)) {
                    WebElement buttonToClick = buttonElements.get(i);

                    // Scroll to the button
                    js.executeScript("arguments[0].scrollIntoView({block: 'nearest'});", buttonToClick);

                    // JavaScript click
                    js.executeScript("arguments[0].click();", buttonToClick);

                    isClicked = true;
                    break;
                }
            }

            if (isClicked) {
                test.log(Status.PASS, "Button clicked successfully for the name: " + textName);
            } else {
                test.log(Status.FAIL, "Button could not be clicked for the name: " + textName,
                        MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            }
        } catch (Exception e) {
            test.log(Status.FAIL, "Failed to click on button for name: " + textName,
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
            test.log(Status.FAIL, e.getMessage());
        }
    }

    public static void clearText(By locator) {
        try {
            // Increase timeout to 60 seconds and improve conditions
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            // Wait for the element to be present, visible, and clickable
            wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(locator), ExpectedConditions.visibilityOfElementLocated(locator), ExpectedConditions.elementToBeClickable(locator)));

            // Clear and enter text into the element
            findElement(locator).clear();

            // Log success
            test.log(Status.PASS, "Entered text '" + text + "' on element with locator: " + locator);
        } catch (Exception e) {
            // Log failure and capture a screenshot if an error occurs
            test.log(Status.FAIL, e);
            test.log(Status.FAIL, "Failed to enter text '" + text + "' on element with locator: " + locator, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshot()).build());
        }


    }

    public static boolean moveSlider(By sliderLocator, int xOffset) {
        try {
            WebElement slider = driver.findElement(sliderLocator);

            if (slider.isDisplayed() && slider.isEnabled()) {
                Actions action = new Actions(driver);
                action.clickAndHold(slider)
                        .moveByOffset(xOffset, 0)
                        .release()
                        .build()
                        .perform();
                return true;
            } else {
                System.out.println("Slider is not enabled or not visible: " + sliderLocator.toString());
                return false;
            }

        } catch (NoSuchElementException e) {
            System.out.println("Slider element not found: " + sliderLocator.toString());
            return false;
        } catch (Exception e) {
            System.out.println("Exception while moving slider: " + e.getMessage());
            return false;
        }
    }

    public void generateTenantSummaryHtml() throws Exception {

        new File("Output/Akku_2.0/Report/Images").mkdirs();
        new File("Output/Akku_2.0/Report/Extent").mkdirs();

        String outputPath = "Output/Akku_2.0/Report/Extent/Akku-Tenant-Quality-Health-Report.html";

        int total = TenantResult.results.size();
        long passed = TenantResult.results.values().stream().filter(v -> v).count();
        long failed = total - passed;

        long suiteDuration = TenantResult.suiteEndTime - TenantResult.suiteStartTime;
        String totalExecTime = formatDurationAdaptive(suiteDuration);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
        ZoneId ist = ZoneId.of("Asia/Kolkata");

        String startTime = Instant.ofEpochMilli(TenantResult.suiteStartTime).atZone(ist).format(fmt);
        String endTime = Instant.ofEpochMilli(TenantResult.suiteEndTime).atZone(ist).format(fmt);

        StringBuilder rows = new StringBuilder();
        for (Map.Entry<String, Boolean> entry : TenantResult.results.entrySet()) {
            String url = entry.getKey();
            Boolean status = entry.getValue();

            String tenantName = url.replace("https://", "").replace("http://", "").split("\\.")[0];
            tenantName = tenantName.substring(0, 1).toUpperCase() + tenantName.substring(1);

            String safeFile = url.replaceAll("[^a-zA-Z0-9]", "_") + ".html";

            long execMillis = TenantResult.executionTimes.getOrDefault(url, 0L);
            String execTime = formatDurationAdaptive(execMillis);

            String badge = status
                    ? "<span class='badge pass'>PASS</span>"
                    : "<span class='badge fail'>FAIL</span>";

            rows.append("<tr>")
                    .append("<td>").append(tenantName).append("</td>")
                    .append("<td>").append(badge).append("</td>")
                    .append("<td>").append(execTime).append("</td>")
                    .append("<td><a class='link' href='").append(safeFile)
                    .append("' target='_blank'>Open Report</a></td>")
                    .append("</tr>");
        }

        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                <meta charset="UTF-8">
                <title>Akku Cybersecurity Solutions – Tenant Quality Health Report</title>
                <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet">
                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                <style>
                html, body { margin:0; padding:0; height:100%; font-family:'Inter',system-ui; background:#0b0c1a; color:#e0e0e0; }
                body { display:flex; flex-direction:column; min-height:100vh; }

                .container { flex:1; display:flex; flex-direction:column; max-width:1400px; margin:auto; padding:35px 20px; }

                /* Header */
                .header { display:flex; flex-direction:column; align-items:center; justify-content:center; margin-bottom:50px; gap:6px; }
                .logo { width:200px; margin-bottom:10px; filter:drop-shadow(0 0 25px rgba(56,189,248,.5)); }
                .title { font-size:46px; font-weight:800; line-height:1.2; background:linear-gradient(90deg,#38bdf8,#22c55e); -webkit-background-clip:text; -webkit-text-fill-color:transparent; text-align:center; }
                .subtitle { font-size:20px; opacity:.85; text-align:center; letter-spacing:0.5px; }

                /* Cards */
                .cards { display:flex; flex-wrap:wrap; justify-content:space-between; gap:24px; margin-bottom:45px; }
                .card { flex:1 1 200px; max-width:280px; min-width:200px; background:rgba(255,255,255,.05); border:1px solid rgba(255,255,255,.12);
                        padding:28px 22px; border-radius:20px; backdrop-filter:blur(12px); box-shadow:0 0 28px rgba(56,189,248,.15);
                        display:flex; flex-direction:column; align-items:center; justify-content:center; text-align:center;
                        transition: transform 0.3s ease, box-shadow 0.3s ease; position:relative;
                }
                .card:hover { transform: translateY(-6px); box-shadow:0 0 38px rgba(56,189,248,.25); }

                .card h2 { margin:0; font-size:42px; font-weight:700; color:#ffffff; background: linear-gradient(90deg,#38bdf8,#22c55e); 
                            -webkit-background-clip:text; -webkit-text-fill-color:transparent; letter-spacing:0.5px; }
                .card p { margin-top:6px; font-size:14px; color:#b0b8c0; text-transform:uppercase; letter-spacing:0.04em; font-weight:600; }

                .card .time-info { font-size:13px; margin-top:10px; opacity:.85; line-height:1.4; color:#b0b8c0; font-weight:500; text-align:center; }

                .chartBox { height:140px; width:140px; margin-bottom:8px; }

                /* Table */
                .table-wrap { flex:1; background:rgba(255,255,255,.03); padding:28px; border-radius:18px; box-shadow:0 0 30px rgba(56,189,248,.12); 
                              overflow-y:auto; max-height:550px; overflow-x:auto; transition: transform 0.3s ease; margin-top:15px; }
                .table-wrap:hover { transform:scale(1.01); }

                table { width:100%; border-collapse:collapse; text-align:center; font-size:15px; }
                th, td { padding:14px 12px; }
                th { opacity:.85; border-bottom:1px solid rgba(255,255,255,.3); font-weight:600; text-transform:uppercase; letter-spacing:0.04em; }
                td { border-bottom:1px solid rgba(255,255,255,.12); }
                tr:nth-child(even) { background:rgba(255,255,255,.02); }
                tr:hover { background:rgba(255,255,255,.05); }

                /* Badges & Links */
                .badge { padding:6px 16px; border-radius:16px; font-weight:600; font-size:12px; text-transform:uppercase; }
                .pass { background:#22c55e33;color:#22c55e; }
                .fail { background:#ef444433;color:#ef4444; }
                .link { color:#38bdf8; text-decoration:none; font-weight:600; }
                .link:hover { text-decoration:underline; }

                footer { text-align:center; font-size:14px; opacity:.75; letter-spacing:.08em; padding:18px 0; background:#0b0c1a; }
                </style>
                </head>
                <body>

                <div class="container">
                    <div class="header">
                        <img src="../Images/akku-brand_logo.jpg" class="logo"/>
                        <div class="title">Akku Cybersecurity Solutions</div>
                        <div class="subtitle">Tenant Quality Health Dashboard</div>
                    </div>

                    <div class="cards">
                        <div class="card"><h2>{{TOTAL}}</h2><p>Total Tenants</p></div>
                        <div class="card"><h2>{{PASSED}}</h2><p>Passed</p></div>
                        <div class="card"><h2>{{FAILED}}</h2><p>Failed</p></div>
                        <div class="card">
                            <h2>{{EXEC_TIME}}</h2><p>Total Execution Time</p>
                            <div class="time-info">Start: {{START_TIME}}<br>End: {{END_TIME}}</div>
                        </div>
                        <div class="card"><div class="chartBox"><canvas id="chart"></canvas></div><p>Health Overview</p></div>
                    </div>

                    <div class="table-wrap">
                        <table>
                            <tr><th>Tenant Name</th><th>Status</th><th>Execution Time</th><th>Detailed Report</th></tr>
                            {{ROWS}}
                        </table>
                    </div>
                </div>

                <footer>© 2026 Akku Cybersecurity Solutions · All Rights Reserved</footer>

                <script>
                const ctx=document.getElementById('chart');
                new Chart(ctx,{
                    type:'doughnut',
                    data:{labels:['Passed','Failed'], datasets:[{data:[{{PASSED}},{{FAILED}}], backgroundColor:['#22c55e','#ef4444'], borderWidth:0}]},
                    options:{cutout:'72%', plugins:{legend:{display:false}}, responsive:true, maintainAspectRatio:false}
                });
                </script>

                </body>
                </html>
                """;

        html = html.replace("{{TOTAL}}", String.valueOf(total))
                .replace("{{PASSED}}", String.valueOf(passed))
                .replace("{{FAILED}}", String.valueOf(failed))
                .replace("{{EXEC_TIME}}", totalExecTime)
                .replace("{{START_TIME}}", startTime)
                .replace("{{END_TIME}}", endTime)
                .replace("{{ROWS}}", rows.toString());

        Files.write(Paths.get(outputPath), html.getBytes(StandardCharsets.UTF_8));
        System.out.println("Executive Dashboard Generated: " + outputPath);
    }

    // Adaptive duration formatter: Xs, Xm Ys, Xh Ym Zs
    private String formatDurationAdaptive(long millis) {
        if (millis < 0) millis = 0;

        long totalSeconds = millis / 1000;
        long h = totalSeconds / 3600;
        long m = (totalSeconds % 3600) / 60;
        long s = totalSeconds % 60;

        if (h > 0) return h + "h " + m + "m " + s + "s";
        if (m > 0) return m + "m " + s + "s";
        return s + "s";
    }

}

