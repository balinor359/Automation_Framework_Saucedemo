package com.saucedemo.tests.common;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class Test_TC14_Verify_products_sorting_work_correctly extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method with specific user, after that validation method from the Homepage object.
     * Call the method who check is products are sorted in right order from low to high prices and from high to low prices.
     */

    @Test
    public void verifyProductsSortingWorkCorrectly() {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC14: Verify products sorting work correctly");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();
        homePage.sortingValidator();

    }
}
