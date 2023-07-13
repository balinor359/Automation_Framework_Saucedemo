package com.saucedemo.pom;

import com.saucedemo.objects.Product;
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

public class CartPage extends TestUtilities {

    /* Declaring web-driver in protected variable */
    protected WebDriver driver;

    /* Declaring string variables for the current page */
    private static final String CART_PAGE_URL = "https://www.saucedemo.com/cart.html";
    private static final String CART_PAGE = "Current page is Cart page.";
    private static final String CART_PAGE_ERROR = "Cart page loading Failed.";
    private static final String CONTINUE_SHOPPING_MISSING_MESSAGE = "'Continue Shopping' button is not displayed!";
    private static final String CHECKOUT_BUTTON_MISSING_MESSAGE = "'Checkout' button is not displayed!";

    /* Declaring page elements */
    @FindBy(xpath = "//div[@class='cart_list']")
    private WebElement cartList;
    @FindBy(xpath = "//div[@class='cart_item']")
    private List<WebElement> cartItems;
    @FindBy(xpath = "//div[@class='inventory_item_name']")
    private WebElement cartItemName;
    @FindBy(xpath = "//div[@class='inventory_item_price']")
    private WebElement cartItemPrice;
    @FindBy(xpath = "//button[@id='checkout']")
    private WebElement checkoutButton;
    @FindBy(xpath = "//button[@id='continue-shopping']")
    private WebElement continueShoppingButton;

    /* This is constructor for cart page using PageFactory for web-elements */
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /* Method who validate Cart page */
    public void cartPageValidator() {
        /* Check if current page contains cart.html */
        if (driver.getCurrentUrl().contains(CART_PAGE_URL)) {
            System.out.println(CART_PAGE);
            MyFileWriter.writeToLog(CART_PAGE);
            /* Validate cartList is shown and call validation methods for "checkout" and "continue shopping" buttons */
            try {
                Assert.assertTrue(cartList.isDisplayed(), GenericMessages.CART_LIST_MISSING_MESSAGE);
                checkoutButtonValidator();
                continueShoppingButtonValidator();

            } catch (NoSuchElementException e) {
                System.out.println(String.format(GenericMessages.MISSING_ELEMENT_MESSAGE, cartList));
                MyFileWriter.writeToLog(String.format(GenericMessages.MISSING_ELEMENT_MESSAGE, cartList));

                Assert.fail(String.format(GenericMessages.MISSING_ELEMENT_MESSAGE, cartList));
            }

            /* If cart is full call cart items validator, validate item total price and also order total */
            if (cartItems.size() > 0) {
                System.out.println(GenericMessages.CART_HAVE_ITEMS_TEXT);
                MyFileWriter.writeToLog(GenericMessages.CART_HAVE_ITEMS_TEXT);
                cartItemsValidator();
            } else {
                System.out.println(GenericMessages.CART_IS_EMPTY_TEXT);
                MyFileWriter.writeToLog(GenericMessages.CART_IS_EMPTY_TEXT);
            }
        } else {
            System.out.println(CART_PAGE_ERROR);
            MyFileWriter.writeToLog(CART_PAGE_ERROR);
        }
    }

    /* Method who validates Checkout button has correct text / font-color / Bg color */
    public void checkoutButtonValidator() {
        Assert.assertTrue(checkoutButton.isDisplayed(), CHECKOUT_BUTTON_MISSING_MESSAGE);
        Assert.assertEquals(checkoutButton.getText(), GenericMessages.CHECKOUT_BUTTON_TEXT, GenericMessages.DIFFERENT_TEXT);

        String fontColor = checkoutButton.getCssValue("color");
        String fontColorHex = Color.fromString(fontColor).asHex();
        Assert.assertEquals(fontColorHex, GenericMessages.CHECKOUT_BUTTON_FONT_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

        String elementBgColor = checkoutButton.getCssValue("background-color");
        String elementBgColorHex = Color.fromString(elementBgColor).asHex();
        Assert.assertEquals(elementBgColorHex, GenericMessages.CHECKOUT_BUTTON_BACKGROUND_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);
    }

    /* Method who validates continue shopping button has correct text / font-color / Bg color / border color */
    public void continueShoppingButtonValidator() {
        Assert.assertTrue(continueShoppingButton.isDisplayed(), CONTINUE_SHOPPING_MISSING_MESSAGE);
        Assert.assertEquals(continueShoppingButton.getText(), GenericMessages.CONTINUE_SHOPPING_TEXT, GenericMessages.DIFFERENT_TEXT);

        String fontColor = continueShoppingButton.getCssValue("color");
        String fontColorHex = Color.fromString(fontColor).asHex();
        Assert.assertEquals(fontColorHex, GenericMessages.CONTINUE_SHOPPING_BUTTON_FONT_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

        String elementBgColor = continueShoppingButton.getCssValue("background-color");
        String elementBgColorHex = Color.fromString(elementBgColor).asHex();
        Assert.assertEquals(elementBgColorHex, GenericMessages.CONTINUE_SHOPPING_BUTTON_BACKGROUND_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

        String elementBorderColor = continueShoppingButton.getCssValue("border-color");
        String elementBorderColorHex = Color.fromString(elementBorderColor).asHex();
        Assert.assertEquals(elementBorderColorHex, GenericMessages.CONTINUE_SHOPPING_BUTTON_BORDER_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);
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

            /* Take element - Remove Button */
            WebElement cartItemRemoveBtn = item.findElement(By.cssSelector("button.cart_button"));
            removeButtonValidator(cartItemRemoveBtn);

            /* Go through all products in productList */
            for (Product product : Product.productList) {

                /* If element from cart match with element from productList compare their name and price */
                if (cartItemNameText.equals(product.getName())) {

                    Assert.assertEquals(cartItemName.getText(), product.getName(), GenericMessages.PRODUCTS_NAME_IS_DIFFERENT_MESSAGE);
                    Assert.assertEquals(cartItemPrice.getText(), product.getPrice(), GenericMessages.PRODUCT_PRICE_IS_DIFFERENT_MESSAGE);

                }
            }
        }
    }

    /* Method who validates remove button has correct text / font-color / border color */
    public void removeButtonValidator(WebElement cartItemRemoveBtn) {
        try {
            Assert.assertTrue(cartItemRemoveBtn.isDisplayed(), GenericMessages.REMOVE_BUTTON_MISSING_MESSAGE);
            Assert.assertEquals(cartItemRemoveBtn.getText(), GenericMessages.REMOVE_BUTTON_TEXT, GenericMessages.DIFFERENT_TEXT);

            String fontColor = cartItemRemoveBtn.getCssValue("color");
            String fontColorHex = Color.fromString(fontColor).asHex();
            Assert.assertEquals(fontColorHex, GenericMessages.REMOVE_BUTTON_FONT_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

            String elementBorderColor = cartItemRemoveBtn.getCssValue("border-color");
            String elementBorderColorHex = Color.fromString(elementBorderColor).asHex();
            Assert.assertEquals(elementBorderColorHex, GenericMessages.REMOVE_BUTTON_BORDER_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

        } catch (NoSuchElementException e) {
            System.out.println(GenericMessages.CODE_ERROR_REMOVE_BUTTON);
            MyFileWriter.writeToLog(GenericMessages.CODE_ERROR_REMOVE_BUTTON);

            Assert.fail(GenericMessages.CODE_ERROR_REMOVE_BUTTON);
        }
    }

    /* Click method for "Checkout" button */
    public CheckoutInfoPage clickOnCheckoutButton() {
        checkoutButton.click();
        /* Pass the driver to CheckoutInfoPage (POM) */
        return new CheckoutInfoPage(driver);
    }
}
