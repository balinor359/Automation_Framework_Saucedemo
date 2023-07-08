package com.saucedemo.tests.common;

import com.saucedemo.pom.*;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test_TC15_Verify_that_page_loading_speed_is_under_2_sec_performance_glitch_user extends TestUtilities {

    @Test
    public void verifyPageLoadingSpeed() {
        MyFileWriter.writeToLog("TC15: Verify that page loading speed is under 2 sec (performance_glitch_user)");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(3));
        homePage.homepageValidator();

    }
}
