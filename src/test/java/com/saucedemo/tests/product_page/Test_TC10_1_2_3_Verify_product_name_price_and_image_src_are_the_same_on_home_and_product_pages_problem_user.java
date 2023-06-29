package com.saucedemo.tests.product_page;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.pom.ProductPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class Test_TC10_1_2_3_Verify_product_name_price_and_image_src_are_the_same_on_home_and_product_pages_problem_user extends TestUtilities {

    @Test
    public void verifyProductNamePriceImageSrcProblemUser() {
        MyFileWriter.writeToLog("TC10.1/10.2/10.3: Verify product name, price and image src are the same on Home and Product pages (problem_user)");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(2));
        homePage.homepageValidator();

//        ProductPage productPage = homePage.selectProduct("Sauce Labs Fleece Jacket");
        ProductPage productPage = homePage.selectProduct("Sauce Labs Bolt T-Shirt");
        productPage.productPageValidator();
        homePage.menuLinksValidator();
        homePage.clickMenuInventoryButton();

        homePage.homepageValidator();

    }
}
