package com.saucedemo.pom;

import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


public class CheckoutInfoPage extends TestUtilities {

    /* Declaring web-driver in protected variable */
    protected WebDriver driver;

    /* Declaring string variables for the current page */
    private static String errorMessage = "";
    private static final String CHECKOUT_INFO_PAGE_URL = "https://www.saucedemo.com/checkout-step-one.html";
    private static final String CHECKOUT_INFO_PAGE = "Current page is Checkout User Information page.";
    private static final String CHECKOUT_INFO_PAGE_ERROR = "Checkout User Information page loading Failed.";
    private static final String USER_INFO_FORM_MISSING_MESSAGE = "User Information Form is missing!";
    private static final String FIRST_NAME_MISSING_MESSAGE = "Error: First Name is required";
    private static final String LAST_NAME_MISSING_MESSAGE = "Error: Last Name is required";
    private static final String POSTAL_CODE_MISSING_MESSAGE = "Error: Postal Code is required";
    private static final String WRONG_INSERTED_DATA = "The inserted data is different!";

    /* Declaring page elements */
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

    /* This is constructor for checkout user info page using PageFactory for web-elements */
    public CheckoutInfoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /* This method return errorMessage and print it in the console */
    public String returnErrorText() {
        errorMessage = errorText.getText();
        System.out.println("Error text: " + errorMessage);
        return errorMessage;
    }

    /* Method who validate Checkout User Information page */
    public void checkoutInfoPageValidator() {
        /* Check if current page contains checkout-step-one.html */
        if (driver.getCurrentUrl().contains(CHECKOUT_INFO_PAGE_URL)) {
            System.out.println(CHECKOUT_INFO_PAGE);
            MyFileWriter.writeToLog(CHECKOUT_INFO_PAGE);

            /* Validate the form is displayed */
            Assert.assertTrue(userInfoForm.isDisplayed(), USER_INFO_FORM_MISSING_MESSAGE);
            /* Validate the "Continue" button */
            continueButtonValidator();
            /* Validate the "Cancel" button */
            cancelButtonValidator();

            /* Validate the form fields are displayed */
            Assert.assertTrue(userFirstName.isDisplayed(), String.format(GenericMessages.MISSING_ELEMENT_MESSAGE, userFirstName));
            Assert.assertTrue(userLastName.isDisplayed(), String.format(GenericMessages.MISSING_ELEMENT_MESSAGE, userLastName));
            Assert.assertTrue(userZipPostCode.isDisplayed(), String.format(GenericMessages.MISSING_ELEMENT_MESSAGE, userZipPostCode));

        } else {
            System.out.println(CHECKOUT_INFO_PAGE_ERROR);
            MyFileWriter.writeToLog(CHECKOUT_INFO_PAGE_ERROR);
        }
    }

    /* Method who validates error message for First Name field */
    public void validateErrorMsgFirstNameRequired() {
        Assert.assertEquals(returnErrorText(), FIRST_NAME_MISSING_MESSAGE, GenericMessages.DIFFERENT_MESSAGE);
    }

    /* Method who validates error message for Last Name field */
    public void validateErrorMsgLastNameRequired() {
        Assert.assertEquals(returnErrorText(), LAST_NAME_MISSING_MESSAGE, GenericMessages.DIFFERENT_MESSAGE);
    }

    /* Method who validates error message for Post Code field */
    public void validateErrorMsgPostCodeRequired() {
        Assert.assertEquals(returnErrorText(), POSTAL_CODE_MISSING_MESSAGE, GenericMessages.DIFFERENT_MESSAGE);
    }

    /* Method who clear,click, input data and check inputted data for First Name field */
    public void insertFirstName(String firstName) {
        userFirstName.clear();
        userFirstName.click();
        userFirstName.sendKeys(firstName);
        /* Validate the inserted data is the same with provided */
        Assert.assertEquals(userFirstName.getAttribute("value"), firstName, WRONG_INSERTED_DATA);
    }

    /* Method who clear,click, input data and check inputted data for Last Name field */
    public void insertLastName(String lastName) {
        userLastName.clear();
        userLastName.click();
        userLastName.sendKeys(lastName);
        /* Validate the inserted data is the same with provided */
        Assert.assertEquals(userLastName.getAttribute("value"), lastName, WRONG_INSERTED_DATA);
    }

    /* Method who clear,click, input data and check inputted data for Post Code field */
    public void insertPostCode(String postCode) {
        userZipPostCode.clear();
        userZipPostCode.click();
        userZipPostCode.sendKeys(postCode);
        /* Validate the inserted data is the same with provided */
        Assert.assertEquals(userZipPostCode.getAttribute("value"), postCode, WRONG_INSERTED_DATA);
    }

    /* Method who validates continue button has correct text / font-color / Bg color */
    public void continueButtonValidator() {
        Assert.assertTrue(continueButton.isDisplayed(), GenericMessages.CONTINUE_BUTTON_MISSING_MESSAGE);
        Assert.assertEquals(continueButton.getAttribute("value"), GenericMessages.CONTINUE_BUTTON_TEXT, GenericMessages.DIFFERENT_TEXT);

        String fontColor = continueButton.getCssValue("color");
        String fontColorHex = Color.fromString(fontColor).asHex();
        Assert.assertEquals(fontColorHex, GenericMessages.CONTINUE_BUTTON_FONT_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

        String elementBgColor = continueButton.getCssValue("background-color");
        String elementBgColorHex = Color.fromString(elementBgColor).asHex();
        Assert.assertEquals(elementBgColorHex, GenericMessages.CONTINUE_BUTTON_BACKGROUND_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);
    }

    /* Method who validates cancel button has correct text / font-color / Bg color / border color */
    public void cancelButtonValidator() {
        Assert.assertTrue(cancelButton.isDisplayed(), GenericMessages.CANCEL_BUTTON_MISSING_MESSAGE);
        Assert.assertEquals(cancelButton.getText(), GenericMessages.CANCEL_BUTTON_TEXT, GenericMessages.DIFFERENT_TEXT);

        String fontColor = cancelButton.getCssValue("color");
        String fontColorHex = Color.fromString(fontColor).asHex();
        Assert.assertEquals(fontColorHex, GenericMessages.CANCEL_BUTTON_FONT_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

        String elementBgColor = cancelButton.getCssValue("background-color");
        String elementBgColorHex = Color.fromString(elementBgColor).asHex();
        Assert.assertEquals(elementBgColorHex, GenericMessages.CANCEL_BUTTON_BACKGROUND_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

        String elementBorderColor = cancelButton.getCssValue("border-color");
        String elementBorderColorHex = Color.fromString(elementBorderColor).asHex();
        Assert.assertEquals(elementBorderColorHex, GenericMessages.CANCEL_BUTTON_BORDER_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);
    }

    /* Click method for "Continue" button */
    public CheckoutOverviewPage clickContinueButton() {
        continueButton.click();
        /* Pass the driver to CheckoutOverviewPage (POM) */
        return new CheckoutOverviewPage(driver);
    }
}
