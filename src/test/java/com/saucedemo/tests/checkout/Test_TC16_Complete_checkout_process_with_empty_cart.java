package com.saucedemo.tests.checkout;

import com.saucedemo.pom.*;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_TC16_Complete_checkout_process_with_empty_cart extends TestUtilities {
    @Test
    public void verifySuccessfulOrderWithEmptyCart() {
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
