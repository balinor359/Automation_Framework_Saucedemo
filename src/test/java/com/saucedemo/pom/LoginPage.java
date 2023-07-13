package com.saucedemo.pom;

import com.saucedemo.objects.TestUser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;

import java.time.Duration;

public class LoginPage extends TestUtilities {

    /* Declaring web-driver in protected variable */
    protected WebDriver driver;

    /* Declaring string variables for the current page */
    private static String loginErrorMessage = "";
    private static final String LOGIN_PAGE = "Current page is Login page.";
    private static final String LOGIN_FORM_MISSING_MESSAGE = "Login form is missing!";
    private static final String LOGIN_PAGE_URL = "https://www.saucedemo.com/";
    private static final String LOGIN_FAILED_MESSAGE = "Login Failed!";
    private static final String LOGIN_WRONG_USERNAME_MESSAGE = "Wrong username!";
    private static final String LOGIN_WRONG_PASSWORD_MESSAGE = "Wrong password!";
    private static final String LOGIN_SUCCESSFUL_MESSAGE = "Login is Successful!";
    private static final String LOGOUT_FAILED_MESSAGE = "Logout Failed!";
    private static final String LOGOUT_SUCCESSFUL_MESSAGE = "Logout is Successful!";
    private static final String WRONG_USER_AND_PASS_MESSAGE = "Epic sadface: Username and password do not match any user in this service";
    private static final String LOCKED_USER_MESSAGE = "Epic sadface: Sorry, this user has been locked out.";
    public static final String LARGE_PAGE_LOAD_TIME_MESSAGE = "Page load time is more than 2 seconds.";

    /* Declaring page elements */
    @FindBy(xpath = "//div[@id='login_button_container']")
    private WebElement loginForm;
    @FindBy(xpath = "//input[@id='user-name' and @placeholder='Username']")
    private WebElement userNameField;
    @FindBy(xpath = "//input[@id='password' and @placeholder='Password']")
    private WebElement userPasswordField;
    @FindBy(xpath = "//input[@id='login-button' and @type='submit']")
    private WebElement loginButton;
    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorText;

    /* This is constructor for login page using PageFactory for web-elements */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /* This method return errorMessage */
    public String returnErrorText() {
        return loginErrorMessage = errorText.getText();
    }

    /* Method who validates error message for wrong username and password */
    public void validateErrorMsgWrongUsernameAndPassword() {
        Assert.assertEquals(errorText.getText(), WRONG_USER_AND_PASS_MESSAGE, GenericMessages.DIFFERENT_MESSAGE);
    }

    /* Method who validates error message for locked user */
    public void validateErrorMsgInvalidUser() {
        Assert.assertEquals(errorText.getText(), LOCKED_USER_MESSAGE, GenericMessages.DIFFERENT_MESSAGE);
    }

    /* Method who validate login page elements are displayed */
    public void loginPageValidator() {
        Assert.assertTrue(loginForm.isDisplayed(), LOGIN_FORM_MISSING_MESSAGE);
        Assert.assertTrue(userNameField.isDisplayed(), String.format(GenericMessages.MISSING_ELEMENT_MESSAGE, userNameField));
        Assert.assertTrue(userPasswordField.isDisplayed(), String.format(GenericMessages.MISSING_ELEMENT_MESSAGE, userPasswordField));
        Assert.assertTrue(loginButton.isDisplayed(), String.format(GenericMessages.MISSING_ELEMENT_MESSAGE, loginButton));
    }

    /* Method who validate login page elements are displayed */
    public void successfulLogout() {
        /* Check if current page is domain( login page ) and print messages in console and log file */
        if (driver.getCurrentUrl().equals(LOGIN_PAGE_URL)) {
            System.out.println(LOGOUT_SUCCESSFUL_MESSAGE);
            MyFileWriter.writeToLog(LOGOUT_SUCCESSFUL_MESSAGE);
            System.out.println(LOGIN_PAGE);
            MyFileWriter.writeToLog(LOGIN_PAGE);
        } else {
            System.out.println(LOGOUT_FAILED_MESSAGE);
            MyFileWriter.writeToLog(LOGOUT_FAILED_MESSAGE);
            Assert.assertEquals(driver.getCurrentUrl(), LOGIN_PAGE_URL, LOGOUT_FAILED_MESSAGE);
        }
    }

    /* Login method who use username and password strings */
    public HomePage login(String username, String password) {

        /* Clear, click and input data into the field */
        userNameField.clear();
        userNameField.click();
        userNameField.sendKeys(username);

        /* Validate the inserted data is the same with provided */
        Assert.assertEquals(username, this.userNameField.getAttribute("value"), LOGIN_WRONG_USERNAME_MESSAGE);

        /* Clear, click and input data into the field */
        userPasswordField.clear();
        userPasswordField.click();
        userPasswordField.sendKeys(password);

        /* Validate the inserted data is the same with provided */
        Assert.assertEquals(password, this.userPasswordField.getAttribute("value"), LOGIN_WRONG_PASSWORD_MESSAGE);

        loginButton.click();

        /* Check if current page is domain( login page ) and print messages in console and log file */
        if (driver.getCurrentUrl().equals(LOGIN_PAGE_URL)) {
            System.out.println(LOGIN_FAILED_MESSAGE);
            System.out.println("Error text: " + returnErrorText());
            MyFileWriter.writeToLog(LOGIN_FAILED_MESSAGE + " - " + returnErrorText());

            Assert.assertEquals(driver.getCurrentUrl(), GenericMessages.HOME_PAGE_URL, LOGIN_FAILED_MESSAGE);
        } else {
            System.out.println(LOGIN_SUCCESSFUL_MESSAGE);
            MyFileWriter.writeToLog(LOGIN_SUCCESSFUL_MESSAGE);

            Assert.assertEquals(driver.getCurrentUrl(), GenericMessages.HOME_PAGE_URL, LOGIN_FAILED_MESSAGE);
        }
        /* Pass the driver to HomePage (POM) */
        return new HomePage(driver);
    }

    /* Login method who use created TestUser */
    public HomePage testUserLogin(TestUser testUser) {

        /* Clear, click and input data into the field */
        userNameField.clear();
        userNameField.click();
        userNameField.sendKeys(testUser.getUsername());
        /* Validate the inserted data is the same with provided */
        Assert.assertEquals(testUser.getUsername(), userNameField.getAttribute("value"), LOGIN_WRONG_USERNAME_MESSAGE);

        /* Clear, click and input data into the field */
        userPasswordField.clear();
        userPasswordField.click();
        userPasswordField.sendKeys(testUser.getPassword());
        /* Validate the inserted data is the same with provided */
        Assert.assertEquals(testUser.getPassword(), userPasswordField.getAttribute("value"), LOGIN_WRONG_PASSWORD_MESSAGE);

        /* Check if the page loading time is more than 2000 milliseconds */
        validatePageLoadTime();

        /* Check if current page is domain( login page ) and print messages in console and log file */
        if (driver.getCurrentUrl().equals(LOGIN_PAGE_URL)) {
            System.out.println(LOGIN_FAILED_MESSAGE);
            System.out.println("Error text: " + returnErrorText());
            MyFileWriter.writeToLog(LOGIN_FAILED_MESSAGE + " - " + returnErrorText());

            Assert.assertEquals(driver.getCurrentUrl(), GenericMessages.HOME_PAGE_URL, LOGIN_FAILED_MESSAGE);
        } else {
            System.out.println(LOGIN_SUCCESSFUL_MESSAGE);
            MyFileWriter.writeToLog(LOGIN_SUCCESSFUL_MESSAGE);

            Assert.assertEquals(driver.getCurrentUrl(), GenericMessages.HOME_PAGE_URL, LOGIN_FAILED_MESSAGE);
        }
        /* Pass the driver to HomePage (POM) */
        return new HomePage(driver);
    }

    /* Method who check is page load time is under 2 seconds */
    public void validatePageLoadTime() {
        /* Start timer before performing action that triggers the page load */
        long startTime = System.currentTimeMillis();

        /* Making FluentWait with timeout 2 seconds and polling every 100 milliseconds for measuring */
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(2))
                .pollingEvery(Duration.ofMillis(100));

        /* Click login button to start measuring the time */
        loginButton.click();

        /* Wait until the condition is met or timeout occurs */
        wait.until(driver -> {
            long endTime = System.currentTimeMillis();
            long loadTime = endTime - startTime;

            // Assertion to check if the load time is more than 2 seconds
            Assert.assertFalse(loadTime > 2000, LARGE_PAGE_LOAD_TIME_MESSAGE);

            return true;
        });

    }
}
