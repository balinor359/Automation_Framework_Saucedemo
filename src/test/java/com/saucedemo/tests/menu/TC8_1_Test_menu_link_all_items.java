package com.saucedemo.tests.menu;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.pom.ProductPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class TC8_1_Test_menu_link_all_items extends TestUtilities {
    @Test
    public void testMenuAllItemsLink() {
        MyFileWriter.writeToLog("TC8.1: Test menu link All Items");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();
        homePage.selectProduct("Sauce Labs Bike Light");

        ProductPage productPage = homePage.openProductPage();
        productPage.productPageValidator();
        homePage.menuLinksValidator();
        homePage.clickMenuInventoryButton();

        simpleWait(2000);           //todo Ако я няма тази пауза излиза error-a
                                             //org.openqa.selenium.remote.http.WebSocket$Listener onError WARNING: Connection reset
        homePage.homepageValidator();

    }

}
