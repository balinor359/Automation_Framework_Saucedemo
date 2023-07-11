package com.saucedemo.tests.checkout;

import com.saucedemo.pom.*;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_TC7_Add_products_in_cart_and_complete_the_checkout_process extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, after that validation method from the Homepage object.
     * Select product, enter in product page, and validate that product page items are visible, and the same as in homepage
     * Add 2 products to the cart and validate is the cart amount changed.
     * Navigate to cart page and validate the added product is with right name and price.
     * Go to successful checkout page while checking all validators for every page, products, totals
     */

    @Test
    public void verifySuccessfulOrder() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC7: Add products in cart and complete the checkout process");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        homePage.addItemToTheCartAndSaveValues("sauce-labs-fleece-jacket");
        homePage.addItemToTheCartAndSaveValues("sauce-labs-backpack");

        Assert.assertEquals(homePage.getItemsInTheCart(), 2, HomePage.CART_BADGE_WRONG_AMOUNT);

        CartPage cartPage = homePage.clickOnCartButton();
        cartPage.cartPageValidator();
        CheckoutInfoPage checkoutInfoPage = cartPage.clickOnCheckoutButton();

        checkoutInfoPage.checkoutInfoPageValidator();
        checkoutInfoPage.insertFirstName("Ivan");
        checkoutInfoPage.insertLastName("Ivanov");
        checkoutInfoPage.insertPostCode("1000");

        CheckoutOverviewPage checkoutOverviewPage = checkoutInfoPage.clickContinueButton();
        checkoutOverviewPage.checkoutOverviewPageFullValidator();

        CheckoutSuccessPage checkoutSuccessPage = checkoutOverviewPage.clickFinishButton();

        checkoutSuccessPage.checkoutSuccessPageValidator();
        checkoutSuccessPage.clickBackHomeButton();

        homePage.homepageValidator();
    }
}
