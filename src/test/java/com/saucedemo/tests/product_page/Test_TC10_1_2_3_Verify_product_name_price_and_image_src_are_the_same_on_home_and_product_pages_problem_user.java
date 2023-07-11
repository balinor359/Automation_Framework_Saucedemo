package com.saucedemo.tests.product_page;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.pom.ProductPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class Test_TC10_1_2_3_Verify_product_name_price_and_image_src_are_the_same_on_home_and_product_pages_problem_user extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, after that validation method from the Homepage object.
     * Select product, enter in product page, and validate that product page items are visible, and the same as in homepage
     * Check if menu links are visible, then click on "All Items" button.
     * Validate user is navigated to homepage.
     */

    @Test
    public void verifyProductNamePriceImageSrcProblemUser() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC10.1/10.2/10.3: Verify product name, price and image src are the same on Home and Product pages (problem_user)");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(2));
        homePage.homepageValidator();

        ProductPage productPage = homePage.selectProduct("Sauce Labs Bolt T-Shirt");
        productPage.productPageValidator();

        homePage.menuLinksValidator();
        homePage.clickMenuInventoryButton();

        homePage.homepageValidator();

    }
}
