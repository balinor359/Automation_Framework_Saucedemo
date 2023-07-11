package com.saucedemo.tests.checkout;

import com.saucedemo.pom.*;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_TC6_Test_checkout_user_information_fields_validation extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, after that validation method from the Homepage object.
     * Select product, enter in product page, and validate that product page items are visible, and the same as in homepage
     * Add 2 products to the cart and validate is the cart amount changed.
     * Navigate to cart page and validate the added product is with right name and price.
     * Navigate to User Info Page and validate all fields errors are correct.
     * Go to checkout overview and validate its elements are shown
     */

    @Test
    public void verifyCheckoutInfoFieldsValidation() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC6: Test checkout user information fields validation");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        homePage.addItemToTheCartAndSaveValues("sauce-labs-bolt-t-shirt");
        homePage.addItemToTheCartAndSaveValues("sauce-labs-onesie");

        Assert.assertEquals(homePage.getItemsInTheCart(), 2, HomePage.CART_BADGE_WRONG_AMOUNT);

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
