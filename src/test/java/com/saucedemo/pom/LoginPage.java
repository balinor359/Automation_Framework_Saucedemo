package com.saucedemo.pom;

import com.saucedemo.objects.TestUser;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;

public class LoginPage extends TestUtilities{
    protected WebDriver driver;
    private static String loginErrorMessage = "";
    private static final String LOGGIN_FORM_MISSING_MESSAGE = "Login form is missing!";
    private static final String MISSING_ELEMENT_MESSAGE = "Element ( %s ) is missing!";
    private static String missingElementMessage = java.lang.String.format(MISSING_ELEMENT_MESSAGE, "");
    private static final String LOGGIN_PAGE_URL = "https://www.saucedemo.com/";
    private static final String LOGGIN_FAILED_MESSAGE = "Login Failed!";
    private static final String LOGGIN_WRONG_USERNAME_MESSAGE = "Wrong username!";
    private static final String LOGGIN_WRONG_PASSWORD_MESSAGE = "Wrong password!";
    private static final String LOGGIN_SUCCESSFUL_MESSAGE = "Login is Successful!";
    private static final String LOGOUT_FAILED_MESSAGE = "Logout Failed!";
    private static final String LOGOUT_SUCCESSFUL_MESSAGE = "Logout is Successful!";
    private static final String DIFFERENT_MESSAGE = "The message is different!";
    private static final String WRONG_USER_AND_PASS_MESSAGE = "Epic sadface: Username and password do not match any user in this service";
    private static final String INVALID_USER_MESSAGE = "Epic sadface: Sorry, this user has been locked out.";

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

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String returnErrorText() {
        return loginErrorMessage = errorText.getText();
    }
    public void checkErrorMsgWrongUsernameAndPassword(){
        Assert.assertEquals(errorText.getText(),WRONG_USER_AND_PASS_MESSAGE,DIFFERENT_MESSAGE);
    }
    public void checkErrorMsgInvalidUser(){
        Assert.assertEquals(errorText.getText(),INVALID_USER_MESSAGE,DIFFERENT_MESSAGE);
    }
    public void loginPageValidator(){
        Assert.assertTrue(loginForm.isDisplayed(), LOGGIN_FORM_MISSING_MESSAGE);
        Assert.assertTrue(userNameField.isDisplayed(), String.format(MISSING_ELEMENT_MESSAGE, userNameField));
        Assert.assertTrue(userPasswordField.isDisplayed(), String.format(MISSING_ELEMENT_MESSAGE, userPasswordField));
        Assert.assertTrue(loginButton.isDisplayed(), String.format(MISSING_ELEMENT_MESSAGE, loginButton));
    }
    public void successfulLogout(){
        if (driver.getCurrentUrl().equals(LOGGIN_PAGE_URL)) {
            System.out.println(LOGOUT_SUCCESSFUL_MESSAGE);
            MyFileWriter.writeToLog(LOGOUT_SUCCESSFUL_MESSAGE);
        } else {
            System.out.println(LOGOUT_FAILED_MESSAGE);
            MyFileWriter.writeToLog(LOGOUT_FAILED_MESSAGE);
        }
    }
    public HomePage login(String username, String password) {

        userNameField.clear();
        userNameField.click();
        userNameField.sendKeys(username);
        Assert.assertEquals(username, this.userNameField.getAttribute("value"), LOGGIN_WRONG_USERNAME_MESSAGE);

        userPasswordField.clear();
        userPasswordField.click();
        userPasswordField.sendKeys(password);
        Assert.assertEquals(password, this.userPasswordField.getAttribute("value"), LOGGIN_WRONG_PASSWORD_MESSAGE);

        loginButton.click();

        if (driver.getCurrentUrl().equals(LOGGIN_PAGE_URL)) {
            System.out.println(LOGGIN_FAILED_MESSAGE);
            returnErrorText();
            System.out.println("Error text: " + returnErrorText());
            MyFileWriter.writeToLog(LOGGIN_FAILED_MESSAGE + " - " + returnErrorText() );
        } else {
            System.out.println(LOGGIN_SUCCESSFUL_MESSAGE);
            MyFileWriter.writeToLog(LOGGIN_SUCCESSFUL_MESSAGE);
        }

        return new HomePage(driver);
    }

    public HomePage testUserLogin(TestUser testUser) {

        userNameField.clear();
        userNameField.click();
        userNameField.sendKeys(testUser.getUsername());
        Assert.assertEquals(testUser.getUsername(), userNameField.getAttribute("value"), LOGGIN_WRONG_USERNAME_MESSAGE);

        userPasswordField.clear();
        userPasswordField.click();
        userPasswordField.sendKeys(testUser.getPassword());
        Assert.assertEquals(testUser.getPassword(), userPasswordField.getAttribute("value"), LOGGIN_WRONG_PASSWORD_MESSAGE);

        loginButton.click();

        if (driver.getCurrentUrl().equals(LOGGIN_PAGE_URL)) {
            System.out.println(LOGGIN_FAILED_MESSAGE);
            returnErrorText();
            System.out.println("Error text: " + returnErrorText());
            MyFileWriter.writeToLog(LOGGIN_FAILED_MESSAGE + " - " + returnErrorText());
        } else {
            System.out.println(LOGGIN_SUCCESSFUL_MESSAGE);
            MyFileWriter.writeToLog(LOGGIN_SUCCESSFUL_MESSAGE);
        }
        return new HomePage(driver);
    }

//    public boolean tryToLogin(TestUser testUser){
//        userNameField.clear();
//        userNameField.click();
//        userNameField.sendKeys(testUser.getUsername());
//
//        userPasswordField.clear();
//        userPasswordField.click();
//        userPasswordField.sendKeys(testUser.getPassword());
//        try {
//            WebElement productsMenu = driver.findElement(By.id("react-burger-menu-btn"));
//        } catch (NoSuchElementException e){
//            return false;
//        }
//        return true;
//    }

}
