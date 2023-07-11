package com.saucedemo.tests.common;

import com.saucedemo.pom.*;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class Test_TC15_Verify_that_page_loading_speed_is_under_2_sec_performance_glitch_user extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, after that validation method from the Homepage object.
     * Method who check page loading time is called into testUserLogin method.
     */

    @Test
    public void verifyPageLoadingSpeed() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC15: Verify that page loading speed is under 2 sec (performance_glitch_user)");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(3));
        homePage.homepageValidator();

    }
}
