package com.saucedemo.utilities;

import com.saucedemo.objects.Product;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class TestUtilities {
    /* Create variables for web-driver / url / browser / implicitlyWait */
    public static WebDriver driver;
    public static String usedBrowser = "";
    private String url;
    private String browser;
    private int implicitlyWait;


    @BeforeMethod
    public void setUp() {
        /* Print messages into console and log file */
        MyFileWriter.writeToLog("Test Start!");
        System.out.println("Test Start!");

        /* Create userList */
        UserBuilder.createUserList();

        /* Create the driver, depending on chosen browser in config.properties */
        setupBrowserDriver();
    }

    @AfterMethod
    public void tearDown() {
        /* Clear saved list with products after each test */
        clearProductList();

        /* Clear saved list with users after each test */
        clearUserList();

        /* Close the driver*/
        if (driver != null) {
            driver.quit();
        }

        /* Print messages into console and log file */
        MyFileWriter.writeToLog("Test End!" + "\n_________________________________________________________________________________________");
        System.out.println("Test End!");
        System.out.println("_________________________________________________________________________________________");

    }

    /* This is driver creating method who use provided data from config.properties*/
    public void setupBrowserDriver() {
        try (FileInputStream configFile = new FileInputStream("src\\test\\resources\\config.properties")) {
            Properties config = new Properties();
            config.load(configFile);
            url = config.getProperty("urlAddress");
            browser = config.getProperty("browser");
            implicitlyWait = Integer.parseInt(config.getProperty("implicitlyWait"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            MyFileWriter.writeToLog("Cannot read configs.");
            System.out.println("Cannot read configs.");
        }
        /* This switch takes provided browser and call specific create method for specific browser */
        switch (browser) {
            case "chrome":
                createChromeDriver(url, implicitlyWait);
                break;
            case "firefox":
                createFirefoxDriver(url, implicitlyWait);
                break;
            case "edge":
                createEdgeDriver(url, implicitlyWait);
                break;
            default:
                MyFileWriter.writeToLog("Unsupported browser type");
                throw new IllegalStateException("Unsupported browser type");
        }

    }

    /* Method who get the website URL */
    private void loadUrl(String url) {
        driver.get(url);
    }

    /* Create Chrome Driver */
    private void createChromeDriver(String url, int implicitlyWait) {
        /* Set property from local web-driver folder  */
        System.setProperty("webdriver.chrome.driver", "saucedemo-webdrivers\\chromedriver\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        /* Create new driver */
        usedBrowser = "chrome";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
        loadUrl(url);

        /* Print driver info into console */
        System.out.println("INFO START________________________");
        System.out.println("Current Thread ID: " + Thread.currentThread().getId());

        Capabilities capabilities = ((ChromeDriver) driver).getCapabilities();
        String browserVersion = capabilities.getCapability("browserVersion").toString();
        String webDriverVersion = capabilities.getCapability("chrome").toString();

        System.out.println("Used WebDriver: Chrome");
        System.out.println("Browser Version: " + browserVersion);
        System.out.println("WebDriver Version: " + webDriverVersion);
        System.out.println("INFO END__________________________\n");
    }

    /* Create Firefox Driver */
    private void createFirefoxDriver(String url, int implicitlyWait) {
        /* Set property from local web-driver folder  */
        System.setProperty("webdriver.gecko.driver", "saucedemo-webdrivers\\geckodriver\\geckodriver.exe");
        WebDriverManager.firefoxdriver().setup();
        /* Create new driver */
        usedBrowser = "firefox";
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
        loadUrl(url);

        /* Print driver info into console */
        System.out.println("INFO START________________________");
        System.out.println("Current Thread ID: " + Thread.currentThread().getId());

        Capabilities capabilities = ((FirefoxDriver) driver).getCapabilities();
        String browserVersion = capabilities.getCapability("browserVersion").toString();
        String webDriverVersion = capabilities.getCapability("moz:geckodriverVersion").toString();

        System.out.println("Used WebDriver: Firefox");
        System.out.println("Browser Version: " + browserVersion);
        System.out.println("WebDriver Version: " + webDriverVersion);
        System.out.println("INFO END__________________________\n");
    }

    /* Create Edge Driver */
    private void createEdgeDriver(String url, int implicitlyWait) {
        /* Set property from local web-driver folder  */
        System.setProperty("webdriver.edge.driver", "saucedemo-webdrivers\\edgedriver\\msedgedriver.exe");
        WebDriverManager.edgedriver().setup();
        /* Create new driver */
        usedBrowser = "edge";
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
        loadUrl(url);

        /* Print driver info into console */
        System.out.println("INFO START________________________");
        System.out.println("Current Thread ID: " + Thread.currentThread().getId());

        Capabilities capabilities = ((EdgeDriver) driver).getCapabilities();
        String browserVersion = capabilities.getCapability("browserVersion").toString();
//        String webDriverVersion = ((EdgeDriver) driver).getCapabilities().toString();
        String webDriverVersion = capabilities.getCapability("msedge").toString();


        System.out.println("Used WebDriver: Edge");
        System.out.println("Browser Version: " + browserVersion);
        System.out.println("WebDriver Version: " + webDriverVersion);
        System.out.println("INFO END__________________________\n");

    }

    //Explicit wait for element to be clickable
    public static WebElement waitClickable(WebDriver driver, WebElement webElement, int sec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        return element;
    }

    //Explicit wait for element to be visible
    public static WebElement waitVisible(WebDriver driver, WebElement webElement, int sec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
        WebElement element = wait.until(ExpectedConditions.visibilityOf(webElement));
        return element;

    }

    /* Simple Thread wait when can`t make web-driver to wait specific page load*/
    public static void simpleWait(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /* This method clear products list data */
    public void clearProductList() {
        Product.productList.clear();
    }
    /* This method clear user list data */
    public void clearUserList() {
        UserBuilder.fullUsersList.clear();
    }

}
