package com.saucedemo.tests.menu;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class Test_TC8_3_Test_menu_link_logout extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, after that validation method from the Homepage object.
     * Check if menu links are visible and click on "Logout" link, and check did user logout successful.
     */

    @Test
    public void testMenuLogoutLink() {
        /* Print info data to log file */
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
