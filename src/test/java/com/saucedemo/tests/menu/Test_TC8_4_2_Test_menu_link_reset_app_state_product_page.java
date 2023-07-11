package com.saucedemo.tests.menu;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.pom.ProductPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_TC8_4_2_Test_menu_link_reset_app_state_product_page extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, after that validation method from the Homepage object.
     * Select product, enter in product page, and validate that product page items are visible, and the same as in homepage
     * Add product to the cart from product page, and check is the cart amount changed.
     * Check if menu links are visible, then click on "Reset App State" button.
     * Check is the cart empty, and add to cart button is in right state.
     */

    @Test
    public void testMenuResetLinkFromProductPage() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC8.4.2: Test menu link reset app state product page");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        ProductPage productPage = homePage.selectProduct("Sauce Labs Fleece Jacket");
        productPage.productPageValidator();

        homePage.addItemToTheCartSimpleClick("sauce-labs-fleece-jacket");

        Assert.assertEquals(homePage.getItemsInTheCart(), 1, HomePage.CART_BADGE_WRONG_AMOUNT);

        homePage.menuLinksValidator();
        homePage.clickMenuResetButton();

        Assert.assertEquals(homePage.getItemsInTheCart(), 0, HomePage.CART_BADGE_WRONG_AMOUNT);

        homePage.addToCartButtonValidator("sauce-labs-fleece-jacket");

    }
}
