package com.saucedemo.pom;

import com.saucedemo.objects.Product;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CartPage extends TestUtilities {
    protected WebDriver driver;
    private static final String CART_PAGE_URL = "https://www.saucedemo.com/cart.html";
    private static final String REMOVE_FROM_CART_LOCATOR = "//button[@id='remove-sauce-labs-%s']";
    private static final String CART_PAGE = "Current page is Cart page.";
    private static final String CART_PAGE_ERROR = "Cart page loading Failed.";
    private static final String PRODUCTS_NAME_MISSING_MESSAGE = "Product name is not displayed!";
    private static final String PRODUCT_PRICE_MISSING_MESSAGE = "Product price is not displayed!";
//    private static final String PRODUCT_IMAGE_MISSING_MESSAGE = "Product image is not displayed!";
    private static final String PRODUCTS_NAME_IS_DIFFERENT_MESSAGE = "Product name is different!";
    private static final String PRODUCT_PRICE_IS_DIFFERENT_MESSAGE = "Product price is different!";
//    private static final String PRODUCT_IMAGE_IS_DIFFERENT_MESSAGE = "Product image is different!";
    private static final String BACK_TO_ALL_PRODUCTS_MISSING_MESSAGE = "'Back to products' link is not displayed!";
    private static final String PRODUCT_ADD_TO_CART_BUTTON_MISSING_MESSAGE = "'Add to cart' button is not displayed!";

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    /* method who validate product page items */
    public void cartPageValidator() {
        if (driver.getCurrentUrl().contains(CART_PAGE_URL)) {
            System.out.println(CART_PAGE);
            MyFileWriter.writeToLog(CART_PAGE);

//            Assert.assertTrue(productName.isDisplayed(), PRODUCTS_NAME_MISSING_MESSAGE);
//            Assert.assertEquals(HomePage.selectedProductName, productName.getText(), PRODUCTS_NAME_IS_DIFFERENT_MESSAGE);
//
//            Assert.assertTrue(productPrice.isDisplayed(), PRODUCT_PRICE_MISSING_MESSAGE);
//            Assert.assertEquals(HomePage.selectedProductPrice, productPrice.getText(), PRODUCT_PRICE_IS_DIFFERENT_MESSAGE);
//
//            Assert.assertTrue(productImage.isDisplayed(), PRODUCT_IMAGE_MISSING_MESSAGE);
//            Assert.assertEquals(HomePage.selectedProductImageSrc, productImage.getAttribute("src"), PRODUCT_IMAGE_IS_DIFFERENT_MESSAGE);
//
//            waitClickable(driver, backToAllProductsButton, 2);
//            Assert.assertTrue(backToAllProductsButton.isDisplayed(), BACK_TO_ALL_PRODUCTS_MISSING_MESSAGE);
//
//            waitClickable(driver, productAddToCartButton, 2);
//            Assert.assertTrue(productAddToCartButton.isDisplayed(), PRODUCT_ADD_TO_CART_BUTTON_MISSING_MESSAGE);

            for (Product product : Product.productList) {
                System.out.println("Name: " + product.getName());
                System.out.println("Image source: " + product.getImageSrc());
                System.out.println("Price: $" + product.getPrice());
                System.out.println();

                //todo Asserts sa tuk !!!!!!!!!
            }

        } else {
            System.out.println(CART_PAGE_ERROR);
            MyFileWriter.writeToLog(CART_PAGE_ERROR);
        }
    }
}
