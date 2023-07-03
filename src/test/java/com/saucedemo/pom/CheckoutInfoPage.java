package com.saucedemo.pom;

import com.saucedemo.objects.TestUser;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class CheckoutInfoPage extends TestUtilities {
    protected WebDriver driver;
    private static String errorMessage = "";
    private static final String CHECKOUT_INFO_PAGE_URL = "https://www.saucedemo.com/checkout-step-one.html";
    private static final String DIFFERENT_TEXT = "The text is different!";
    private static final String DIFFERENT_CSS_VALUE = "The CSS value is different!";
    private static final String MISSING_ELEMENT_MESSAGE = "Element ( %s ) is missing!";
    private static final String CHECKOUT_INFO_PAGE = "Current page is Checkout User Information page.";
    private static final String CHECKOUT_INFO_PAGE_ERROR = "Checkout User Information page loading Failed.";
    private static final String USER_INFO_FORM_MISSING_MESSAGE = "User Information Form is missing!";
    private static final String CONTINUE_BUTTON_MISSING_MESSAGE = "'Continue' button is not displayed!";
    private static final String CANCEL_BUTTON_MISSING_MESSAGE = "'Cancel' button is not displayed!";
    private static final String CONTINUE_BUTTON_TEXT = "Continue";
    private static final String CONTINUE_BUTTON_FONT_COLOR = "#132322";
    private static final String CONTINUE_BUTTON_BACKGROUND_COLOR = "#3ddc91";
    private static final String CANCEL_BUTTON_TEXT = "Cancel";
    private static final String CANCEL_BUTTON_FONT_COLOR = "#132322";
    private static final String CANCEL_BUTTON_BORDER_COLOR = "#132322";
    private static final String CANCEL_BUTTON_BACKGROUND_COLOR = "#ffffff";
    private static final String FIRST_NAME_MISSING_MESSAGE = "Error: First Name is required";
    private static final String LAST_NAME_MISSING_MESSAGE = "Error: Last Name is required";
    private static final String POSTAL_CODE_MISSING_MESSAGE = "Error: Postal Code is required";
    private static final String DIFFERENT_MESSAGE = "The message is different!";
    private static final String WRONG_INSERTED_DATA = "The inserted data is different!";


    @FindBy(xpath = "//div[@class='checkout_info']")
    private WebElement userInfoForm;
    @FindBy(xpath = "//input[@id='first-name' and @placeholder='First Name']")
    private WebElement userFirstName;
    @FindBy(xpath = "//input[@id='last-name' and @placeholder='Last Name']")
    private WebElement userLastName;
    @FindBy(xpath = "//input[@id='postal-code' and @placeholder='Zip/Postal Code']")
    private WebElement userZipPostCode;
    @FindBy(xpath = "//h3[@data-test='error']")
    private WebElement errorText;
    @FindBy(xpath = "//input[@id='continue']")
    private WebElement continueButton;
    @FindBy(xpath = "//button[@id='cancel']")
    private WebElement cancelButton;

    public CheckoutInfoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String returnErrorText() {
        errorMessage = errorText.getText();
        System.out.println("Error text: " + errorMessage);
        return errorMessage;
    }

    /* method who validate Checkout User Information page */
    public void checkoutInfoPageValidator() {
        if (driver.getCurrentUrl().contains(CHECKOUT_INFO_PAGE_URL)) {
            System.out.println(CHECKOUT_INFO_PAGE);
            MyFileWriter.writeToLog(CHECKOUT_INFO_PAGE);

            Assert.assertTrue(userInfoForm.isDisplayed(), USER_INFO_FORM_MISSING_MESSAGE);
            continueButtonValidator();
            cancelButtonValidator();

            Assert.assertTrue(userFirstName.isDisplayed(), String.format(MISSING_ELEMENT_MESSAGE, userFirstName));
            Assert.assertTrue(userLastName.isDisplayed(), String.format(MISSING_ELEMENT_MESSAGE, userLastName));
            Assert.assertTrue(userZipPostCode.isDisplayed(), String.format(MISSING_ELEMENT_MESSAGE, userZipPostCode));

        } else {
            System.out.println(CHECKOUT_INFO_PAGE_ERROR);
            MyFileWriter.writeToLog(CHECKOUT_INFO_PAGE_ERROR);
        }
    }
    public void validateErrorMsgFirstNameRequired(){
        Assert.assertEquals(returnErrorText(), FIRST_NAME_MISSING_MESSAGE, DIFFERENT_MESSAGE);
    }
    public void validateErrorMsgLastNameRequired(){
        Assert.assertEquals(returnErrorText(), LAST_NAME_MISSING_MESSAGE, DIFFERENT_MESSAGE);
    }
    public void validateErrorMsgPostCodeRequired(){
        Assert.assertEquals(returnErrorText(), POSTAL_CODE_MISSING_MESSAGE, DIFFERENT_MESSAGE);
    }
    public void insertFirstName(String firstName) {
        userFirstName.clear();
        userFirstName.click();
        userFirstName.sendKeys(firstName);
        /* Validate the inserted data is the same with provided */
        Assert.assertEquals(userFirstName.getAttribute("value"), firstName, WRONG_INSERTED_DATA);
    }
    public void insertLastName(String lastName) {
        userLastName.clear();
        userLastName.click();
        userLastName.sendKeys(lastName);
        /* Validate the inserted data is the same with provided */
        Assert.assertEquals(userLastName.getAttribute("value"), lastName, WRONG_INSERTED_DATA);
    }
    public void insertPostCode(String postCode) {
        userZipPostCode.clear();
        userZipPostCode.click();
        userZipPostCode.sendKeys(postCode);
        /* Validate the inserted data is the same with provided */
        Assert.assertEquals(userZipPostCode.getAttribute("value"), postCode, WRONG_INSERTED_DATA);
    }
    public void continueButtonValidator() {
        Assert.assertTrue(continueButton.isDisplayed(), CONTINUE_BUTTON_MISSING_MESSAGE);
        Assert.assertEquals(continueButton.getAttribute("value"), CONTINUE_BUTTON_TEXT, DIFFERENT_TEXT);

        String fontColor = continueButton.getCssValue("color");
        String fontColorHex = Color.fromString(fontColor).asHex();
        Assert.assertEquals(fontColorHex, CONTINUE_BUTTON_FONT_COLOR, DIFFERENT_CSS_VALUE);

        String elementBgColor = continueButton.getCssValue("background-color");
        String elementBgColorHex = Color.fromString(elementBgColor).asHex();
        Assert.assertEquals(elementBgColorHex, CONTINUE_BUTTON_BACKGROUND_COLOR, DIFFERENT_CSS_VALUE);
    }

    public void cancelButtonValidator() {
        Assert.assertTrue(cancelButton.isDisplayed(), CANCEL_BUTTON_MISSING_MESSAGE);
        Assert.assertEquals(cancelButton.getText(), CANCEL_BUTTON_TEXT, DIFFERENT_TEXT);

        String fontColor = cancelButton.getCssValue("color");
        String fontColorHex = Color.fromString(fontColor).asHex();
        Assert.assertEquals(fontColorHex, CANCEL_BUTTON_FONT_COLOR, DIFFERENT_CSS_VALUE);

        String elementBgColor = cancelButton.getCssValue("background-color");
        String elementBgColorHex = Color.fromString(elementBgColor).asHex();
        Assert.assertEquals(elementBgColorHex, CANCEL_BUTTON_BACKGROUND_COLOR, DIFFERENT_CSS_VALUE);

        String elementBorderColor = cancelButton.getCssValue("border-color");
        String elementBorderColorHex = Color.fromString(elementBorderColor).asHex();
        Assert.assertEquals(elementBorderColorHex, CANCEL_BUTTON_BORDER_COLOR, DIFFERENT_CSS_VALUE);
    }
    /* click method for "Continue" button */
    public CheckoutOverviewPage clickContinueButton() {
        continueButton.click();
        return new CheckoutOverviewPage(driver);
    }
}
