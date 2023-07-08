package com.saucedemo.tests.common;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.pom.ProductPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class Test_TC14_Verify_products_sorting_work_correctly extends TestUtilities {
    @Test
    public void verifyProductsSortingWorkCorrectly() {
        MyFileWriter.writeToLog("TC14: Verify products sorting work correctly");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();


    }
}
