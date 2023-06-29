package com.saucedemo.tests.menu;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.pom.ProductPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC8_4_2_Test_menu_link_reset_app_state_product_page extends TestUtilities {
    @Test
    public void testMenuResetLinkFromProductPage() {
        MyFileWriter.writeToLog("TC8.4.1: Test menu link reset app state product page");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        ProductPage productPage = homePage.selectProduct("Sauce Labs Fleece Jacket");
        productPage.productPageValidator();

        homePage.addItemToTheCartSimpleClick("sauce-labs-fleece-jacket");

        Assert.assertEquals(homePage.getItemsInTheCart(),1, HomePage.CART_BADGE_WRONG_AMOUNT);

        homePage.menuLinksValidator();
        homePage.clickMenuResetButton();

        Assert.assertEquals(homePage.getItemsInTheCart(),0, HomePage.CART_BADGE_WRONG_AMOUNT);

        homePage.addToCartButtonValidator("sauce-labs-fleece-jacket");

    }
}
