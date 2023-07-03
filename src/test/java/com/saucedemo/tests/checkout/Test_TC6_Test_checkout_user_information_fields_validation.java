package com.saucedemo.tests.checkout;

import com.saucedemo.pom.*;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_TC6_Test_checkout_user_information_fields_validation  extends TestUtilities {
    @Test
    public void verifyCheckoutInfoFieldsValidation() {
        MyFileWriter.writeToLog("TC6: Test checkout user information fields validation");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        homePage.addItemToTheCartAndSaveValues("sauce-labs-bolt-t-shirt");
        homePage.addItemToTheCartAndSaveValues("sauce-labs-onesie");

        Assert.assertEquals(homePage.getItemsInTheCart(),2, HomePage.CART_BADGE_WRONG_AMOUNT);

        CartPage cartPage = homePage.clickOnCartButton();
        cartPage.cartPageValidator();
        CheckoutInfoPage checkoutInfoPage = cartPage.clickOnCheckoutButton();

        checkoutInfoPage.checkoutInfoPageValidator();
        checkoutInfoPage.clickContinueButton();
        checkoutInfoPage.validateErrorMsgFirstNameRequired();
        checkoutInfoPage.insertFirstName("Ivan");

        checkoutInfoPage.clickContinueButton();
        checkoutInfoPage.validateErrorMsgLastNameRequired();
        checkoutInfoPage.insertLastName("Ivanov");

        checkoutInfoPage.clickContinueButton();
        checkoutInfoPage.validateErrorMsgPostCodeRequired();
        checkoutInfoPage.insertPostCode("1000");

        CheckoutOverviewPage checkoutOverviewPage = checkoutInfoPage.clickContinueButton();
        checkoutOverviewPage.checkoutOverviewPageSimpleValidator();
    }
}
