package com.saucedemo.tests.menu;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class TC8_1_Test_menu_link_all_items extends TestUtilities {
    @Test
    public void clickMenuAllItemsLink() {
        MyFileWriter.writeToLog("TC8.1: Test menu link All Items");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();
        homePage.menuLinksValidator();


    }

}
