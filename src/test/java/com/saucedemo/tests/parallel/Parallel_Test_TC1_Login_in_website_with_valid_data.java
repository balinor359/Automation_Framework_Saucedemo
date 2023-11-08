package com.saucedemo.tests.parallel;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Parallel_Test_TC1_Login_in_website_with_valid_data {

    /* The test initializes the web driver, call login page validator, then use provided user data to log into the profile,
     * validate error messages, after that validation method from the Homepage object
     */

    private WebDriver driver;

    /* Method who get the website URL */
    private void loadUrl(String url) {
        driver.get(url);
    }

    @BeforeMethod
    public void setUp() {

        /* Print messages into console and log file */
        MyFileWriter.writeToLog("Test Start!");
        System.out.println("Test Start!");

        /* Set up the WebDriver instance (ChromeDriver) and load the website URL */
        driver = new ChromeDriver();
        String url = "https://www.saucedemo.com/";
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


    @Test
    public void loginWithValidDataInput() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("Parallel_TC1: Login in website with valid data");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.login("standard_user", "secret_sauce");
        homePage.homepageValidator();

    }

    @AfterMethod
    public void tearDown() {

        /* Close the driver*/
        if (driver != null) {
            driver.quit();
        }

        /* Print messages into console and log file */
        MyFileWriter.writeToLog("Test End!" + "\n_________________________________________________________________________________________");
        System.out.println("Test End!");
        System.out.println("_________________________________________________________________________________________");

    }
}
