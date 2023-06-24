package com.saucedemo.tests.common;

import com.saucedemo.pom.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.asserts.SoftAssert;

public class TC11_Verify_that_footer_external_links_are_valid extends TestUtilities{

    @Test
    public void testFooterSocialLinks(){
        MyFileWriter.writeToLog("TC11: Verify that footer external links are valid");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        homePage.socialLinksValidator();


    }
//
}
