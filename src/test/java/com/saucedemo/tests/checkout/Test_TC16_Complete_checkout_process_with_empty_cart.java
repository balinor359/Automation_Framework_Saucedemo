package com.saucedemo.tests.checkout;

import com.saucedemo.pom.*;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class Test_TC16_Complete_checkout_process_with_empty_cart extends TestUtilities {

    /* This test is made, only to show that user can complete order without adding product to the cart.
     * In real environment this test should not exist. Website need code fix, to not allow that action!
     *
     * The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, after that validation method from the Homepage object.
     * Click on cart link in header
     * Go to successful checkout page while checking all validators for every page, products, totals
     */

    @Test
    public void verifySuccessfulOrderWithEmptyCart() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC6: Complete checkout process with empty cart");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

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
