package com.saucedemo.pom;

import com.saucedemo.objects.Product;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class ProductPage extends TestUtilities {
    protected WebDriver driver;
    private static final String PRODUCT_PAGE_URL = "https://www.saucedemo.com/inventory-item.html";
    private static final String HOME_PAGE_URL = "https://www.saucedemo.com/inventory.html";
    private static final String PRODUCT_PAGE = "Current page is Product page.";
    private static final String PRODUCT_PAGE_ERROR = "Product page loading Failed.";
    private static final String PRODUCTS_NAME_MISSING_MESSAGE = "Product name is not displayed!";
    private static final String PRODUCT_PRICE_MISSING_MESSAGE = "Product price is not displayed!";
    private static final String PRODUCT_IMAGE_MISSING_MESSAGE = "Product image is not displayed!";
    private static final String PRODUCTS_NAME_IS_DIFFERENT_MESSAGE = "Product name is different!";
    private static final String PRODUCT_PRICE_IS_DIFFERENT_MESSAGE = "Product price is different!";
    private static final String PRODUCT_IMAGE_IS_DIFFERENT_MESSAGE = "Product image is different!";
    private static final String BACK_TO_ALL_PRODUCTS_MISSING_MESSAGE = "'Back to products' link is not displayed!";
    private static final String PRODUCT_ADD_TO_CART_BUTTON_MISSING_MESSAGE = "'Add to cart' button is not displayed!";
    public static final String LARGE_PAGE_LOAD_TIME_MESSAGE = "Page load time is more than 2 seconds.";

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
    SoftAssert softAssert = new SoftAssert();
    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //todo излишно
    public void addItemToTheCart() {
        productAddToCartButton.click();
    }
    public void clickBackToAllProductsButton() {
        backToAllProductsButton.click();
    }

    //todo Грешно ли е няколко проверки да са в 1 метод? Трябва ли да ги изведа всяка в отделен метод, след като имам 3 отделни TC?
    // Или този вариант е добре, а за отделните тест кейсове да изведа конкретни валидатори, защото няма да мине през всичките варианти?
    // Другия вариант е да се преправи метода със SoftAssert за да могат да минават тестовете и да показва Expected и Actual
    // Лоша практика ли е за 3 тест кейса да успея да ги направя с 1 автоматизиран тест( в случея със softAssert ) TC10.3 ?
    // Остава само 1 тест кейс !!!!!!!!!!!!!!! обединен за 3-те теста TC10.1.2.3

    /* method who validate product page items */
    public void productPageValidator() {
        if (driver.getCurrentUrl().contains(PRODUCT_PAGE_URL)) {

            System.out.println(PRODUCT_PAGE);
            MyFileWriter.writeToLog(PRODUCT_PAGE);

            waitClickable(driver, backToAllProductsButton, 2);
            Assert.assertTrue(backToAllProductsButton.isDisplayed(), BACK_TO_ALL_PRODUCTS_MISSING_MESSAGE);

            waitClickable(driver, productAddToCartButton, 2);
            Assert.assertTrue(productAddToCartButton.isDisplayed(), PRODUCT_ADD_TO_CART_BUTTON_MISSING_MESSAGE);

//            System.out.println(Product.productList.get(0));
            validateProductName();
            validateProductPrice();
            validateProductImageSrc();

            softAssert.assertAll();
        } else {
            System.out.println(PRODUCT_PAGE_ERROR);
            MyFileWriter.writeToLog(PRODUCT_PAGE_ERROR);
        }
    }
    public void validateProductName() {
        Assert.assertTrue(productName.isDisplayed(), PRODUCTS_NAME_MISSING_MESSAGE);
//            Assert.assertEquals(HomePage.selectedProductName, productName.getText(), PRODUCTS_NAME_IS_DIFFERENT_MESSAGE);
        softAssert.assertEquals(productName.getText(),Product.productList.get(0).getName(), PRODUCTS_NAME_IS_DIFFERENT_MESSAGE);

    }
    public void validateProductPrice() {
        Assert.assertTrue(productPrice.isDisplayed(), PRODUCT_PRICE_MISSING_MESSAGE);
//            Assert.assertEquals(HomePage.selectedProductPrice, productPrice.getText(), PRODUCT_PRICE_IS_DIFFERENT_MESSAGE);
        softAssert.assertEquals(productPrice.getText(), Product.productList.get(0).getPrice(), PRODUCT_PRICE_IS_DIFFERENT_MESSAGE);
    }
    public void validateProductImageSrc() {
        Assert.assertTrue(productImage.isDisplayed(), PRODUCT_IMAGE_MISSING_MESSAGE);
//            Assert.assertEquals(HomePage.selectedProductImageSrc, productImage.getAttribute("src"), PRODUCT_IMAGE_IS_DIFFERENT_MESSAGE);
        softAssert.assertEquals(productImage.getAttribute("src"),Product.productList.get(0).getImageSrc(), PRODUCT_IMAGE_IS_DIFFERENT_MESSAGE);
    }
//    public void verifyPageLoadSpeed() {
//
//        // Get the current time before the page load
//        long startTime = System.currentTimeMillis();
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.urlToBe(HOME_PAGE_URL));
//        wait.until(ExpectedConditions.visibilityOf(productsList));
//
//        // Get the current time after the page load
//        long endTime = System.currentTimeMillis();
//
//        // Calculate the page load time in milliseconds
//        long loadTime = endTime - startTime;
//        System.out.println("startTime " + startTime);
//        System.out.println("endTime " + endTime);
//        System.out.println("loadTime " + loadTime);
//
//        // Assert that the page load time is under 2 seconds
//        Assert.assertTrue(loadTime < 2, LARGE_PAGE_LOAD_TIME_MESSAGE);
//
//    }
}
