package com.saucedemo.utilities;

import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.UserBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestUtilities {
    public static WebDriver driver;
    private String url;
    private String browser;
    private int implicitlyWait;

    @BeforeMethod
    public void setUp() {
        MyFileWriter.writeToLog("Test Start!");
        System.out.println("Test Start!");
        setupBrowserDriver();
        UserBuilder.createUserList();

        System.out.println("Current Thread ID: " + Thread.currentThread().getId());
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
        driver.quit();
        MyFileWriter.writeToLog("Test End!" + "\n_________________________________________________________________________________________");
        System.out.println("Test End!");
        System.out.println("_________________________________________________________________________________________");
    }

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

    private void loadUrl(String url) {
        driver.get(url);
    }

    private void createChromeDriver(String url, int implicitlyWait) {
        System.setProperty("webdriver.chrome.driver", "saucedemo-webdrivers\\chromedriver\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
        loadUrl(url);
    }

    private void createFirefoxDriver(String url, int implicitlyWait) {
        System.setProperty("webdriver.gecko.driver", "saucedemo-webdrivers\\geckodriver\\geckodriver.exe");
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
        loadUrl(url);
    }

    private void createEdgeDriver(String url, int implicitlyWait) {
        System.setProperty("webdriver.edge.driver", "saucedemo-webdrivers\\edgedriver\\msedgedriver.exe");
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
        loadUrl(url);
    }


    //Explicit wait
    public static WebElement waitClickable(WebDriver driver, WebElement webElement, int sec){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(sec));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        return element;
    }
    public static WebElement waitVisible(WebDriver driver, WebElement webElement, int sec){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
        WebElement element = wait.until(ExpectedConditions.visibilityOf(webElement));
        return element;

    }
//    public static void waitPageToLoad(WebDriver driver,int sec){
//        driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
////        try {
////            driver.manage().wait(sec);
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
////        }
//    }
    public static void simpleWait(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    public static WebDriverWait waitSimpleTest(WebDriver driver, int sec){
//        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(sec));
//        return wait;
//    }
}
