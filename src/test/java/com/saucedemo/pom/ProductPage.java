package com.saucedemo.pom;

import com.saucedemo.objects.Product;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;


public class ProductPage extends TestUtilities {

    /* Declaring web-driver in protected variable */
    protected WebDriver driver;

    /* Declaring string variables for the current page */
    private static final String PRODUCT_PAGE_URL = "https://www.saucedemo.com/inventory-item.html";
    private static final String PRODUCT_PAGE = "Current page is Product page.";
    private static final String PRODUCT_PAGE_ERROR = "Product page loading Failed.";
    private static final String PRODUCT_IMAGE_MISSING_MESSAGE = "Product image is not displayed!";
    private static final String PRODUCT_IMAGE_IS_DIFFERENT_MESSAGE = "Product image is different!";
    private static final String BACK_TO_ALL_PRODUCTS_MISSING_MESSAGE = "'Back to products' link is not displayed!";

    /* Declaring page elements */
    @FindBy(xpath = "//a[@id='inventory_sidebar_link']")
    private WebElement menuInventoryButton;
    @FindBy(xpath = "//button[@id='back-to-products']")
    private WebElement backToAllProductsButton;
    @FindBy(xpath = "//div[contains(@class,'inventory_details_name')]")
    private WebElement productName;
    @FindBy(xpath = "//div[@class='inventory_details_price']")
    private WebElement productPrice;
    @FindBy(xpath = "//img[@class='inventory_details_img']")
    private WebElement productImage;
    @FindBy(xpath = "//button[contains(@id,'add-to-cart-')]")
    private WebElement productAddToCartButton;
    @FindBy(xpath = "//button[contains(@id,'remove-']")
    private WebElement productRemoveButton;
    @FindBy(xpath = "//div[@class='inventory_list']")
    private WebElement productsList;

    /* Declare soft Assert  - used for problem user, to get all errors at the end of the test */
    SoftAssert softAssert = new SoftAssert();

    /* This is constructor for product page using PageFactory for web-elements */
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /* Click method for "Cart" button in header */
    public void addItemToTheCart() {
        productAddToCartButton.click();
    }

    /* Click method for "Back to products" button */
    public void clickBackToAllProductsButton() {
        backToAllProductsButton.click();
    }

    /* Method who validate product page items */
    public void productPageValidator() {
        /* Check if current page is one of product pages and print messages in console and log file */
        if (driver.getCurrentUrl().contains(PRODUCT_PAGE_URL)) {

            System.out.println(PRODUCT_PAGE);
            MyFileWriter.writeToLog(PRODUCT_PAGE);

            /* Explicit wait elements to be clickable, then check if they are displayed on the page */
            waitClickable(driver, backToAllProductsButton, 2);
            Assert.assertTrue(backToAllProductsButton.isDisplayed(), BACK_TO_ALL_PRODUCTS_MISSING_MESSAGE);

            waitClickable(driver, productAddToCartButton, 2);
            Assert.assertTrue(productAddToCartButton.isDisplayed(), GenericMessages.ADD_TO_CART_MISSING_MESSAGE);

            /* Validate the product name */
            validateProductName();
            /* Validate the product price */
            validateProductPrice();
            /* Validate the product image src */
            validateProductImageSrc();

            /* Calling all soft Asserts */
            softAssert.assertAll();
        } else {
            System.out.println(PRODUCT_PAGE_ERROR);
            MyFileWriter.writeToLog(PRODUCT_PAGE_ERROR);
        }
    }

    /* Method who validate the product name */
    public void validateProductName() {
        Assert.assertTrue(productName.isDisplayed(), GenericMessages.PRODUCTS_NAME_MISSING_MESSAGE);
        /* Its use soft assert, to get all errors at the end of the test */
        softAssert.assertEquals(productName.getText(), Product.productList.get(0).getName(), GenericMessages.PRODUCTS_NAME_IS_DIFFERENT_MESSAGE);
    }

    /* Method who validate the product price */
    public void validateProductPrice() {
        Assert.assertTrue(productPrice.isDisplayed(), GenericMessages.PRODUCT_PRICE_MISSING_MESSAGE);
        /* Its use soft assert, to get all errors at the end of the test */
        softAssert.assertEquals(productPrice.getText(), Product.productList.get(0).getPrice(), GenericMessages.PRODUCT_PRICE_IS_DIFFERENT_MESSAGE);
    }

    /* Method who validate the product image src */
    public void validateProductImageSrc() {
        Assert.assertTrue(productImage.isDisplayed(), PRODUCT_IMAGE_MISSING_MESSAGE);
        /* Its use soft assert, to get all errors at the end of the test */
        softAssert.assertEquals(productImage.getAttribute("src"), Product.productList.get(0).getImageSrc(), PRODUCT_IMAGE_IS_DIFFERENT_MESSAGE);
    }

}
