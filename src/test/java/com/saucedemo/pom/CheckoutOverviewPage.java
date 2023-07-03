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
    protected WebDriver driver;
    private static final String CHECKOUT_OVERVIEW_PAGE_URL = "https://www.saucedemo.com/checkout-step-two.html";
    private static final String DIFFERENT_TEXT = "The text is different!";
    private static final String DIFFERENT_CSS_VALUE = "The CSS value is different!";
    private static final String CHECKOUT_OVERVIEW_PAGE = "Current page is Checkout Overview page.";
    private static final String CHECKOUT_OVERVIEW_PAGE_ERROR = "Checkout Overview page loading Failed.";
    private static final String FINISH_BUTTON_MISSING_MESSAGE = "'Finish' button is not displayed!";
    private static final String CANCEL_BUTTON_MISSING_MESSAGE = "'Cancel' button is not displayed!";
    private static final String FINISH_BUTTON_TEXT = "Finish";
    private static final String FINISH_BUTTON_FONT_COLOR = "#132322";
    private static final String FINISH_BUTTON_BACKGROUND_COLOR = "#3ddc91";
    private static final String CANCEL_BUTTON_TEXT = "Cancel";
    private static final String CANCEL_BUTTON_FONT_COLOR = "#132322";
    private static final String CANCEL_BUTTON_BORDER_COLOR = "#132322";
    private static final String CANCEL_BUTTON_BACKGROUND_COLOR = "#ffffff";
    private static final String CART_LIST_MISSING_MESSAGE = "Cart list is not displayed!";
    private static final String SUMMARY_INFO_MISSING_MESSAGE = "Summary Info Data is not displayed!";
    private static final String CART_HAVE_ITEMS_TEXT = "Cart contains items.";
    private static final String CART_IS_EMPTY_TEXT = "Cart is empty.";
    private static final String PRODUCTS_NAME_MISSING_MESSAGE = "Product name is not displayed!";
    private static final String PRODUCT_PRICE_MISSING_MESSAGE = "Product price is not displayed!";
    private static final String PRODUCTS_NAME_IS_DIFFERENT_MESSAGE = "Product name is different!";
    private static final String PRODUCT_PRICE_IS_DIFFERENT_MESSAGE = "Product price is different!";
    private static final String ITEM_TOTAL_IS_DIFFERENT_MESSAGE = "Item total price is different!";
    private static final String ORDER_TOTAL_IS_DIFFERENT_MESSAGE = "Order total price is different!";
    private static final String ITEM_TOTAL_MISSING_MESSAGE = "Item total price is not displayed!";
    private static final String ORDER_TOTAL_MISSING_MESSAGE = "Order total price is not displayed!";
    private final ArrayList<Double> pricesOnly = new ArrayList<>();
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

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /* method who validate elements on Checkout Overview page */
    public void checkoutOverviewPageSimpleValidator() {
        if (driver.getCurrentUrl().contains(CHECKOUT_OVERVIEW_PAGE_URL)) {
            System.out.println(CHECKOUT_OVERVIEW_PAGE);
            MyFileWriter.writeToLog(CHECKOUT_OVERVIEW_PAGE);

            Assert.assertTrue(cartList.isDisplayed(), CART_LIST_MISSING_MESSAGE);
            Assert.assertTrue(summaryInfo.isDisplayed(), SUMMARY_INFO_MISSING_MESSAGE);
            finishButtonValidator();
            cancelButtonValidator();

        } else {
            System.out.println(CHECKOUT_OVERVIEW_PAGE_ERROR);
            MyFileWriter.writeToLog(CHECKOUT_OVERVIEW_PAGE_ERROR);
        }
    }

    /* method who validate elements on Checkout Overview page */
    public void checkoutOverviewPageFullValidator() {

        checkoutOverviewPageSimpleValidator();

        if (cartItems.size() > 0) {
            System.out.println(CART_HAVE_ITEMS_TEXT);
            MyFileWriter.writeToLog(CART_HAVE_ITEMS_TEXT);
            cartItemsValidator();
            validateItemTotal();
            validateOrderTotal();

        } else {
            System.out.println(CART_IS_EMPTY_TEXT);
            MyFileWriter.writeToLog(CART_IS_EMPTY_TEXT);
        }

    }

    public void finishButtonValidator() {
        Assert.assertTrue(finishButton.isDisplayed(), FINISH_BUTTON_MISSING_MESSAGE);
        Assert.assertEquals(finishButton.getText(), FINISH_BUTTON_TEXT, DIFFERENT_TEXT);

        String fontColor = finishButton.getCssValue("color");
        String fontColorHex = Color.fromString(fontColor).asHex();
        Assert.assertEquals(fontColorHex, FINISH_BUTTON_FONT_COLOR, DIFFERENT_CSS_VALUE);

        String elementBgColor = finishButton.getCssValue("background-color");
        String elementBgColorHex = Color.fromString(elementBgColor).asHex();
        Assert.assertEquals(elementBgColorHex, FINISH_BUTTON_BACKGROUND_COLOR, DIFFERENT_CSS_VALUE);
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

    public void cartItemsValidator() {

        /* go through all products */
        for (WebElement item : cartItems) {

            /* get element - Name */
            WebElement cartItemName = item.findElement(By.cssSelector("div[class='inventory_item_name']"));
            Assert.assertTrue(cartItemName.isDisplayed(), PRODUCTS_NAME_MISSING_MESSAGE);
            String cartItemNameText = cartItemName.getText();

            /* get element - Price */
            WebElement cartItemPrice = item.findElement(By.cssSelector("div[class='inventory_item_price']"));
            Assert.assertTrue(cartItemPrice.isDisplayed(), PRODUCT_PRICE_MISSING_MESSAGE);

            for (Product product : Product.productList) {
                if (cartItemNameText.equals(product.getName())) {

                    Assert.assertEquals(cartItemName.getText(), product.getName(), PRODUCTS_NAME_IS_DIFFERENT_MESSAGE);
                    Assert.assertEquals(cartItemPrice.getText(), product.getPrice(), PRODUCT_PRICE_IS_DIFFERENT_MESSAGE);

                    double price = extractPrice(product.getPrice());
                    pricesOnly.add(price);

                }
            }
        }
    }

    /* click method for "Finish" button */
    public CheckoutSuccessPage clickFinishButton() {
        finishButton.click();
        return new CheckoutSuccessPage(driver);
    }

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

    //todo Задължително ли трябва да запазвам изчислените itemTotalSum в отделна променлива, и да ги подавам към validateOrderTotal() метода
    // след като двата метода се извикват един след друг в основен метод и за да стигне до последния трябва първо да изчисли и сравни сумата от продуктите
    // подадени от списъка
    public void validateItemTotal() {
        double itemTotalSum = 0;
        for (double itemPrice : pricesOnly) {
            itemTotalSum += itemPrice;
        }
        double roundedTotalSum = Math.round(itemTotalSum * 100) / 100D;

        Assert.assertTrue(itemTotal.isDisplayed(), ITEM_TOTAL_MISSING_MESSAGE);
        Assert.assertEquals(roundedTotalSum, extractPriceFromElement(itemTotal), ITEM_TOTAL_IS_DIFFERENT_MESSAGE);
    }

    public void validateOrderTotal() {
        double orderTotalSum = extractPriceFromElement(itemTotal) + extractPriceFromElement(tax);
        double roundedOrderTotalSum = Math.round(orderTotalSum * 100) / 100D;

        Assert.assertTrue(itemTotal.isDisplayed(), ORDER_TOTAL_MISSING_MESSAGE);
        Assert.assertEquals(roundedOrderTotalSum, extractPriceFromElement(orderTotal), ORDER_TOTAL_IS_DIFFERENT_MESSAGE);
    }
}
