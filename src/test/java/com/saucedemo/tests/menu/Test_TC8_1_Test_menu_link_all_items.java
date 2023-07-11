package com.saucedemo.tests.menu;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.pom.ProductPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class Test_TC8_1_Test_menu_link_all_items extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, after that validation method from the Homepage object
     */

    @Test
    public void testMenuAllItemsLink() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC8.1: Test menu link All Items");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        ProductPage productPage = homePage.selectProduct("Sauce Labs Bike Light");
        productPage.productPageValidator();

        homePage.menuLinksValidator();
        homePage.clickMenuInventoryButton();

        homePage.homepageValidator();

    }

}
