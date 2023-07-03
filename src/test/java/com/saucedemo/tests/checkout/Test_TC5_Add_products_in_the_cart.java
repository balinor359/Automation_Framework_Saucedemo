package com.saucedemo.tests.checkout;

import com.saucedemo.objects.Product;
import com.saucedemo.pom.CartPage;
import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Test_TC5_Add_products_in_the_cart extends TestUtilities {
    @Test
    public void verifyProductsAddedToTheCart() {
        MyFileWriter.writeToLog("TC5: Add products in the cart");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        homePage.addItemToTheCartAndSaveValues("sauce-labs-backpack");
        homePage.addItemToTheCartAndSaveValues("test.allthethings()-t-shirt-(red)");

        Assert.assertEquals(homePage.getItemsInTheCart(),2, HomePage.CART_BADGE_WRONG_AMOUNT);

        CartPage cartPage = homePage.clickOnCartButton();
        cartPage.cartPageValidator();

    }
}
