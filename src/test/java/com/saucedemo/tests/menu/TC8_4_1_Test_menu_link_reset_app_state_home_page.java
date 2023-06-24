package com.saucedemo.tests.menu;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TC8_4_1_Test_menu_link_reset_app_state_home_page extends TestUtilities{
    @Test
    public void testMenuResetLink() {
        MyFileWriter.writeToLog("TC8.4.1: Test menu link reset app state home page");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        homePage.addItemToTheCart("bolt-t-shirt");
        homePage.addItemToTheCart("bike-light");

        Assert.assertEquals(homePage.getItemsInTheCart(),2, HomePage.CART_BADGE_WRONG_AMOUNT);

        homePage.menuLinksValidator();
        homePage.clickMenuResetButton();

        Assert.assertEquals(homePage.getItemsInTheCart(),0, HomePage.CART_BADGE_WRONG_AMOUNT);

        homePage.removeButtonValidator("bolt-t-shirt");
        homePage.removeButtonValidator("bike-light");


    }

}
