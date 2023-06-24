package com.saucedemo.pom;

import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class ProductPage extends TestUtilities {
    protected WebDriver driver;
    private static final String PRODUCT_PAGE_URL = "https://www.saucedemo.com/inventory-item.html";
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
    @FindBy(xpath = "//button[contains(@id,'add-to-cart-sauce-labs-')]")
    private WebElement productAddToCartButton;
    @FindBy(xpath = "//button[contains(@id,'remove-sauce-labs-']")
    private WebElement productRemoveButton;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addItemToTheCart() {
        productAddToCartButton.click();
    }

    //todo Грешно ли е няколко проверки да са в 1 метод? Трябва ли да ги изведа всяка в отделен метод, след като имам 3 отделни TC?
    // Или този вариант е добре, а за отделните тест кейсове да изведа конкретни валидатори, защото няма да мине през всичките варианти?
    // Другия вариант е да се преправи метода със SoftAssert за да могат да минават тестовете и да показва Expected и Actual
    // Лоша практика ли е за 3 тест кейса да успея да ги направя с 1 автоматизиран тест( в случея със softAssert ) ?
    /* method who validate product page items */
    public void productPageValidator() {
        if (driver.getCurrentUrl().contains(PRODUCT_PAGE_URL)) {
            SoftAssert softAssert = new SoftAssert();
            System.out.println(PRODUCT_PAGE);
            MyFileWriter.writeToLog(PRODUCT_PAGE);

            Assert.assertTrue(productName.isDisplayed(), PRODUCTS_NAME_MISSING_MESSAGE);
//            Assert.assertEquals(HomePage.selectedProductName, productName.getText(), PRODUCTS_NAME_IS_DIFFERENT_MESSAGE);
            softAssert.assertEquals(productName.getText(), HomePage.selectedProductName, PRODUCTS_NAME_IS_DIFFERENT_MESSAGE);

            Assert.assertTrue(productPrice.isDisplayed(), PRODUCT_PRICE_MISSING_MESSAGE);
//            Assert.assertEquals(HomePage.selectedProductPrice, productPrice.getText(), PRODUCT_PRICE_IS_DIFFERENT_MESSAGE);
            softAssert.assertEquals(productPrice.getText(), HomePage.selectedProductPrice, PRODUCT_PRICE_IS_DIFFERENT_MESSAGE);

            Assert.assertTrue(productImage.isDisplayed(), PRODUCT_IMAGE_MISSING_MESSAGE);
//            Assert.assertEquals(HomePage.selectedProductImageSrc, productImage.getAttribute("src"), PRODUCT_IMAGE_IS_DIFFERENT_MESSAGE);
            softAssert.assertEquals(productImage.getAttribute("src"), HomePage.selectedProductImageSrc, PRODUCT_IMAGE_IS_DIFFERENT_MESSAGE);
            //todo Защо в TC10.3 Expected и Actual са обърнати на обратно?
//            softAssert.assertEquals("", "", "");
//            Assert.assertEquals("","","");
            waitClickable(driver, backToAllProductsButton, 2);
            Assert.assertTrue(backToAllProductsButton.isDisplayed(), BACK_TO_ALL_PRODUCTS_MISSING_MESSAGE);

            waitClickable(driver, productAddToCartButton, 2);
            Assert.assertTrue(productAddToCartButton.isDisplayed(), PRODUCT_ADD_TO_CART_BUTTON_MISSING_MESSAGE);

            softAssert.assertAll();
        } else {
            System.out.println(PRODUCT_PAGE_ERROR);
            MyFileWriter.writeToLog(PRODUCT_PAGE_ERROR);
        }
    }
}
