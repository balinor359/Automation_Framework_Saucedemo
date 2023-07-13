package com.saucedemo.pom;

import com.saucedemo.objects.Product;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckoutOverviewPage extends TestUtilities {

    /* Declaring web-driver in protected variable */
    protected WebDriver driver;

    /* Declaring string variables for the current page */
    private static final String CHECKOUT_OVERVIEW_PAGE_URL = "https://www.saucedemo.com/checkout-step-two.html";
    private static final String CHECKOUT_OVERVIEW_PAGE = "Current page is Checkout Overview page.";
    private static final String CHECKOUT_OVERVIEW_PAGE_ERROR = "Checkout Overview page loading Failed.";
    private static final String FINISH_BUTTON_MISSING_MESSAGE = "'Finish' button is not displayed!";
    private static final String SUMMARY_INFO_MISSING_MESSAGE = "Summary Info Data is not displayed!";
    private static final String ITEM_TOTAL_IS_DIFFERENT_MESSAGE = "Item total price is different!";
    private static final String ORDER_TOTAL_IS_DIFFERENT_MESSAGE = "Order total price is different!";
    private static final String ITEM_TOTAL_MISSING_MESSAGE = "Item total price is not displayed!";
    private static final String ORDER_TOTAL_MISSING_MESSAGE = "Order total price is not displayed!";
    private final ArrayList<Double> pricesOnly = new ArrayList<>();

    /* Declaring page elements */
    @FindBy(xpath = "//div[@class='checkout_info']")
    private WebElement userInfoForm;
    @FindBy(xpath = "//button[@id='finish']")
    private WebElement finishButton;
    @FindBy(xpath = "//button[@id='cancel']")
    private WebElement cancelButton;
    @FindBy(xpath = "//div[@class='cart_list']")
    private WebElement cartList;
    @FindBy(xpath = "//div[@class='summary_info']")
    private WebElement summaryInfo;
    @FindBy(xpath = "//div[@class='cart_item']")
    private List<WebElement> cartItems;
    @FindBy(xpath = "//div[@class='inventory_item_name']")
    private WebElement cartItemName;
    @FindBy(xpath = "//div[@class='inventory_item_price']")
    private WebElement cartItemPrice;
    @FindBy(xpath = "//div[@class='summary_subtotal_label']")
    private WebElement itemTotal;
    @FindBy(xpath = "//div[@class='summary_tax_label']")
    private WebElement tax;
    @FindBy(xpath = "//div[contains(@class,'summary_total_label')]")
    private WebElement orderTotal;

    /* This is constructor for checkout overview page using PageFactory for web-elements */
    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /* Method who validate elements on Checkout Overview page - Simple - cartList / summaryInfo / finish button / cancel button */
    public void checkoutOverviewPageSimpleValidator() {
        /* Check if current page contains checkout-step-two.html */
        if (driver.getCurrentUrl().contains(CHECKOUT_OVERVIEW_PAGE_URL)) {
            System.out.println(CHECKOUT_OVERVIEW_PAGE);
            MyFileWriter.writeToLog(CHECKOUT_OVERVIEW_PAGE);

            /* Validate the cartList and summaryInfo are visible */
            Assert.assertTrue(cartList.isDisplayed(), GenericMessages.CART_LIST_MISSING_MESSAGE);
            Assert.assertTrue(summaryInfo.isDisplayed(), SUMMARY_INFO_MISSING_MESSAGE);
            /* Validate the "Finish" button */
            finishButtonValidator();
            /* Validate the "Cancel" button */
            cancelButtonValidator();

        } else {
            System.out.println(CHECKOUT_OVERVIEW_PAGE_ERROR);
            MyFileWriter.writeToLog(CHECKOUT_OVERVIEW_PAGE_ERROR);
        }
    }

    /* Method who validate elements on Checkout Overview page - Full - all items from Simple method plus cartItems / ItemTotal / orderTotal */
    public void checkoutOverviewPageFullValidator() {

        /* Call the simple validator who check cartList and summaryInfo visibility also finish and cancel buttons validators */
        checkoutOverviewPageSimpleValidator();

        /* If cart is full call cart items validator, validate item total price and also order total */
        if (cartItems.size() > 0) {
            System.out.println(GenericMessages.CART_HAVE_ITEMS_TEXT);
            MyFileWriter.writeToLog(GenericMessages.CART_HAVE_ITEMS_TEXT);

            /* Validate the Cart Items */
            cartItemsValidator();
            /* Validate the Item Total */
            validateItemTotal();
            /* Validate the Order Total */
            validateOrderTotal();

        } else {
            System.out.println(GenericMessages.CART_IS_EMPTY_TEXT);
            MyFileWriter.writeToLog(GenericMessages.CART_IS_EMPTY_TEXT);
        }

    }

    /* Method who validates finish button has correct text / font-color / Bg color */
    public void finishButtonValidator() {
        Assert.assertTrue(finishButton.isDisplayed(), FINISH_BUTTON_MISSING_MESSAGE);
        Assert.assertEquals(finishButton.getText(), GenericMessages.FINISH_BUTTON_TEXT, GenericMessages.DIFFERENT_TEXT);

        String fontColor = finishButton.getCssValue("color");
        String fontColorHex = Color.fromString(fontColor).asHex();
        Assert.assertEquals(fontColorHex, GenericMessages.FINISH_BUTTON_FONT_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

        String elementBgColor = finishButton.getCssValue("background-color");
        String elementBgColorHex = Color.fromString(elementBgColor).asHex();
        Assert.assertEquals(elementBgColorHex, GenericMessages.FINISH_BUTTON_BACKGROUND_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);
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

    /* Method who goes through all cart items and compare their name and price with items saved in productList */
    public void cartItemsValidator() {

        /* Go through all products saved in cart */
        for (WebElement item : cartItems) {

            /* Take element - Name */
            WebElement cartItemName = item.findElement(By.cssSelector("div[class='inventory_item_name']"));
            Assert.assertTrue(cartItemName.isDisplayed(), GenericMessages.PRODUCTS_NAME_MISSING_MESSAGE);
            String cartItemNameText = cartItemName.getText();

            /* Take element - Price */
            WebElement cartItemPrice = item.findElement(By.cssSelector("div[class='inventory_item_price']"));
            Assert.assertTrue(cartItemPrice.isDisplayed(), GenericMessages.PRODUCT_PRICE_MISSING_MESSAGE);

            /* Go through all products in productList */
            for (Product product : Product.productList) {

                /* If element from cart match with element from productList compare their name and price */
                if (cartItemNameText.equals(product.getName())) {

                    Assert.assertEquals(cartItemName.getText(), product.getName(), GenericMessages.PRODUCTS_NAME_IS_DIFFERENT_MESSAGE);
                    Assert.assertEquals(cartItemPrice.getText(), product.getPrice(), GenericMessages.PRODUCT_PRICE_IS_DIFFERENT_MESSAGE);

                    /* Save product price as double in pricesOnly array */
                    double price = extractPrice(product.getPrice());
                    pricesOnly.add(price);

                }
            }
        }
    }

    /* Click method for "Finish" button */
    public CheckoutSuccessPage clickFinishButton() {
        finishButton.click();
        /* Pass the driver to CheckoutSuccessPage (POM) */
        return new CheckoutSuccessPage(driver);
    }

    /* This method extract only prices from string without any other text */
    public static double extractPrice(String priceString) {

        /* Matches one or more digits */
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(priceString);

        if (matcher.find()) {
            String extractedPrice = matcher.group();
            return Double.parseDouble(extractedPrice);
        }

        return 0;
    }

    /* This method extract only prices from WebElement without any other text */
    public static double extractPriceFromElement(WebElement element) {

        String elementText = element.getText();

        /* Matches one or more digits */
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(elementText);

        if (matcher.find()) {
            String extractedPrice = matcher.group();
            return Double.parseDouble(extractedPrice);
        }

        return 0;
    }

    /* Method who validate Item Total is equal the sum of all products in the cart */
    public void validateItemTotal() {
        double itemTotalSum = 0;
        /* Go through all item in pricesOnly array and sum them in itemTotalSum variable */
        for (double itemPrice : pricesOnly) {
            itemTotalSum += itemPrice;

        }
        double roundedTotalSum = Math.round(itemTotalSum * 100) / 100D;

        Assert.assertTrue(itemTotal.isDisplayed(), ITEM_TOTAL_MISSING_MESSAGE);
        Assert.assertEquals(roundedTotalSum, extractPriceFromElement(itemTotal), ITEM_TOTAL_IS_DIFFERENT_MESSAGE);
    }

    /* Method who validate Order Total is equal the sum of Item Total plus tax */
    public void validateOrderTotal() {
        double orderTotalSum = extractPriceFromElement(itemTotal) + extractPriceFromElement(tax);
        double roundedOrderTotalSum = Math.round(orderTotalSum * 100) / 100D;

        Assert.assertTrue(itemTotal.isDisplayed(), ORDER_TOTAL_MISSING_MESSAGE);
        Assert.assertEquals(roundedOrderTotalSum, extractPriceFromElement(orderTotal), ORDER_TOTAL_IS_DIFFERENT_MESSAGE);
    }
}
