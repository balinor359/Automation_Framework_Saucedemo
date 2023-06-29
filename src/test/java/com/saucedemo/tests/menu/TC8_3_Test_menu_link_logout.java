package com.saucedemo.tests.menu;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class TC8_3_Test_menu_link_logout extends TestUtilities{
    @Test
    public void testMenuLogoutLink() {
        MyFileWriter.writeToLog("TC8.3: Test menu link logout");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        homePage.menuLinksValidator();
        homePage.clickMenuLogoutButton();

        loginPage.successfulLogout();
        loginPage.loginPageValidator();

    }

}
